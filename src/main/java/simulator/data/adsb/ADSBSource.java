package simulator.data.adsb;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import lombok.Getter;
import simulator.config.Constants;
import simulator.data.adsb.geo.Cities;
import simulator.model.GeoCoordinate;
import simulator.model.source.AbstractSource;
import simulator.util.GeoOps;
import simulator.util.datagenerators.CallSignGenerator;
import simulator.util.datagenerators.HexIdentGenerator;

public class ADSBSource extends AbstractSource {
	
	private static final Logger log = LoggerFactory.getLogger(ADSBSource.class);
	
	private static DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd,HH:mm:ss.SSS").withZone(ZoneId.of("UTC"));
	
	private Entry<String, GeoCoordinate> city1;
	private Entry<String, GeoCoordinate> city2;

	private double totalTime;
	private double totalDistance;
	
	private int publishTime = 3; // in [s]
	private int nrOfPoints;
	
	private double factorLat = 1.0;
	private double factorLon = 1.0;
	private double stepLat;
	private double stepLon;

	@Getter private GeoCoordinate currentPosition;
	private int sentPoints;
	
	@Getter private String hexIdent;
	private String callsign;
	
	private static final double factor = 100_000.0;
	
	public ADSBSource() {
		super();
	}
	
	@Override
	public void run() {
		init();
		while (!isKill()) {
			
			double newLat = currentPosition.getLatitude() + factorLat * stepLat;
			double newLon = currentPosition.getLongitude() + factorLon * stepLon;
			
			double lat = ((int)(currentPosition.getLatitude() * factor)) / factor;
			double lon = ((int)(currentPosition.getLongitude() * factor)) / factor;
			
			getTrack().publish(ADSBMessages.createMsg1(hexIdent, callsign, dateTimeFormatter.format(new Date().toInstant())));
			getTrack().publish(ADSBMessages.createMsg3(hexIdent, lat, lon, dateTimeFormatter.format(new Date().toInstant())));
			
			double speed = (int)(Constants.airplaneSpeed * Constants.fromMstoKn);
			double track = GeoOps.getBearing(currentPosition.getLatitude(), currentPosition.getLongitude(), newLat, newLon);
			getTrack().publish(ADSBMessages.createMsg4(hexIdent, dateTimeFormatter.format(new Date().toInstant()), speed, track));

			currentPosition = new GeoCoordinate(newLat, newLon);
			sentPoints++;
			
			try {
				Thread.sleep(publishTime * 1_000L);
			} catch (InterruptedException e) {
				log.debug("Exception at Thread {} ", e);
			}
			
			if (sentPoints > nrOfPoints) {
				try {
					log.info("Track for hexident={} terminated. Restart in {} minutes.", hexIdent, Constants.DEFAULT_TRACK_SLEEP_TIME / 60_000);
					Thread.sleep(Constants.DEFAULT_TRACK_SLEEP_TIME);
					init();
				} catch (Exception e) {}
			}
		}
		log.info("Thread killed for " + hexIdent);
	}

	@Override
	public void init() {
		hexIdent = HexIdentGenerator.next();
		callsign = CallSignGenerator.next();
		
		city1 = Cities.getRandom();
		city2 = Cities.getRandom();
		while (city1.getValue().equals(city2.getValue())) {
			city2 = Cities.getRandom();
		}
		
		double lat1 = city1.getValue().getLatitude();
		double lon1 = city1.getValue().getLongitude();
		double lat2 = city2.getValue().getLatitude();
		double lon2 = city2.getValue().getLongitude();
		
		currentPosition = city1.getValue();
		totalDistance = GeoOps.getDistance(lat1, lon1, lat2, lon2);
		totalTime = totalDistance / Constants.airplaneSpeed;
		nrOfPoints = (int) (totalTime / this.publishTime);
		sentPoints = 0;
		
		stepLat = Math.abs((lat1 - lat2) / nrOfPoints);
		stepLon = Math.abs((lon1 - lon2) / nrOfPoints);
		
		if (lat1 > lat2)
			factorLat = -factorLat;
		if (lon1 > lon2)
			factorLon = -factorLon;
		
		log.info("Airplane created: {} from ({}) to ({}) dist={}m, time={}s, points={}", hexIdent, city1.getKey(), city2.getKey(), (int)totalDistance, (int)totalTime, nrOfPoints);
	}
	
	@Override
	public String toString() {
		return hexIdent;
	}
}
