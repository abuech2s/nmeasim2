package simulator.data.gps;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import lombok.Getter;
import simulator.config.Constants;
import simulator.util.GeoOps;
import simulator.model.GeoCoordinate;
import simulator.model.source.AbstractSource;

public class GPSSource extends AbstractSource {
	
	private static final Logger log = LoggerFactory.getLogger(GPSSource.class);
	
	private List<GeoCoordinate> waypoints;
	private int currentWayPointIndex;
	private int messagePublishTimeInterval; // in [s]
	
	private double totalTime;
	private double totalDistance;
	private int nrOfPoints;

	private DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HHmmss").withZone(ZoneId.of("UTC"));
	private DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("ddMMyy").withZone(ZoneId.of("UTC"));
	
	@Getter private GeoCoordinate currentPosition;
	private int sentPoints;
	
	private double factorLat = 1.0;
	private double factorLon = 1.0;
	private double stepLat;
	private double stepLon;

	private double course;

	public GPSSource() {
		super();
	}

	@Override
	public void run() {
		init();
		while (!kill) {
			
			course = GeoOps.getBearing(currentPosition.getLatitude(), currentPosition.getLongitude(), waypoints.get(currentWayPointIndex+1).getLatitude(), waypoints.get(currentWayPointIndex+1).getLongitude());
				
			String msgGpgga = GPSMessages.MSG_GPGGA;
			msgGpgga = msgGpgga.replace("${time}", timeFormatter.format(new Date().toInstant()));
			msgGpgga = msgGpgga.replace("${lat}", GeoOps.GeoDecToDegMin(currentPosition.getLatitude(), 4, 4) );
			if (currentPosition.getLatitude() < 0) msgGpgga = msgGpgga.replace("${latNS}", "S");
			else msgGpgga = msgGpgga.replace("${latNS}", "N");
							
			msgGpgga = msgGpgga.replace("${lon}", GeoOps.GeoDecToDegMin(currentPosition.getLongitude(), 5, 4));
			if (currentPosition.getLongitude() < 0) msgGpgga = msgGpgga.replace("${lonWE}", "W");
			else msgGpgga = msgGpgga.replace("${lonWE}", "E");
			
			String msgGprmc = GPSMessages.MSG_GPRMC;
			msgGprmc = msgGprmc.replace("${lat}", GeoOps.GeoDecToDegMin(currentPosition.getLatitude(), 4, 2));
			if (currentPosition.getLatitude() < 0) msgGprmc = msgGprmc.replace("${latNS}", "S");
			else msgGprmc = msgGprmc.replace("${latNS}", "N");
			msgGprmc = msgGprmc.replace("${lon}", GeoOps.GeoDecToDegMin(currentPosition.getLongitude(), 5, 2));
			if (currentPosition.getLongitude() < 0) msgGprmc = msgGprmc.replace("${lonWE}", "W");
			else msgGprmc = msgGprmc.replace("${lonWE}", "E");
			msgGprmc = msgGprmc.replace("${time}", timeFormatter.format(new Date().toInstant()));
			msgGprmc = msgGprmc.replace("${date}", dateFormatter.format(new Date().toInstant()));
			
			msgGprmc = msgGprmc.replace("${speed}", String.format("%.3f", Constants.gpsSpeed * Constants.fromMstoKn).replaceAll(",", "."));
			
			String msgGphdt = GPSMessages.MSG_GPHDT;
			msgGphdt = msgGphdt.replace("${course}", String.format("%.1f", course).replaceAll(",", "."));
			
			msgGpgga = "$" + msgGpgga + "*" + GeoOps.calcCheckSum(msgGpgga);
			msgGprmc = "$" + msgGprmc + "*" + GeoOps.calcCheckSum(msgGprmc);
			msgGphdt = "$" + msgGphdt + "*" + GeoOps.calcCheckSum(msgGphdt);
			
			getTrack().publish(msgGpgga);
			getTrack().publish(msgGprmc);
			getTrack().publish(msgGphdt);
			
			try {
				Thread.sleep(messagePublishTimeInterval * 1_000);
			} catch (InterruptedException e) {
				log.debug("Exception at Thread {} ", e);
			}
			
			sentPoints++;
			if (sentPoints > nrOfPoints) {
				if (currentWayPointIndex < waypoints.size()-2) {
					currentWayPointIndex++;
					updateWayPoint();
					log.info("Change waypoint for own vehicle to {} with {} points", waypoints.get(currentWayPointIndex).getPointName(), nrOfPoints);
				} else {
					try {
						log.info("Track for own vehicle terminated. Restart in {} s.", Constants.gpsSleepTime);
						Thread.sleep(Constants.gpsSleepTime);
						init();
					} catch (Exception e) {}
				}
			}
			
			double newLat = currentPosition.getLatitude() + factorLat * stepLat;
			double newLon = currentPosition.getLongitude() + factorLon * stepLon;
			currentPosition = new GeoCoordinate(newLat, newLon);
		}
	}
	
	@Override
	public void init() {
		messagePublishTimeInterval = ThreadLocalRandom.current().nextInt(Constants.gpsMessagePublishTimeIntervalMin, Constants.gpsMessagePublishTimeIntervalMax);

		waypoints = GPSLines.getGPSRoute();
		
		currentWayPointIndex = 0;
		factorLat = 1.0;
		factorLon = 1.0;
		
		updateWayPoint();
		
		log.info("Own vehicle created: dist={}m, time={}s, points={}", (int)totalDistance, (int)totalTime, nrOfPoints);
	}
	
	private void updateWayPoint() {
		double lat1 = waypoints.get(currentWayPointIndex).getLatitude();
		double lon1 = waypoints.get(currentWayPointIndex).getLongitude();
		double lat2 = waypoints.get(currentWayPointIndex+1).getLatitude();
		double lon2 = waypoints.get(currentWayPointIndex+1).getLongitude();
		
		currentPosition = waypoints.get(currentWayPointIndex);
		totalDistance = GeoOps.getDistance(lat1, lon1, lat2, lon2);
		totalTime = totalDistance / Constants.gpsSpeed;
		nrOfPoints = (int) (totalTime / this.messagePublishTimeInterval);
		sentPoints = 0;
		
		stepLat = Math.abs((lat1 - lat2) / nrOfPoints);
		stepLon = Math.abs((lon1 - lon2) / nrOfPoints);
		
		factorLat = 1.0;
		factorLon = 1.0;
		if (lat1 > lat2)
			factorLat = -factorLat;
		if (lon1 > lon2)
			factorLon = -factorLon;
	}

}
