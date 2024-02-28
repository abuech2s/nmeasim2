package simulator.data.ais;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import simulator.config.Constants;
import simulator.data.ais.geo.Cities;
import simulator.data.ais.geo.Point;
import simulator.model.GeoCoordinate;
import simulator.util.GeoOps;
import simulator.util.datagenerators.MMSIGenerator;

public class AISSource extends AISAbstractSource {
	
	private static final Logger log = LoggerFactory.getLogger(AISSource.class);
	
	private List<Point> waypoints;
	
	public AISSource() {
		super();
	}
	
	@Override
	public void run() {
		init();
		while (!isKill()) {
			
			navStatus++;
			navStatus = navStatus % 16;
			
			posFixType++;
			posFixType = posFixType % 16;
			
			int course = (int)GeoOps.getBearing(currentPosition.getLatitude(), currentPosition.getLongitude(), waypoints.get(currentWayPointIndex+1).getGeoCoordinate().getLatitude(), waypoints.get(currentWayPointIndex+1).getGeoCoordinate().getLongitude());
			int trueHeading = (int)course; // TODO
			
			String msgType1 = AISEncoder.getBinaryStringMsg1(Integer.parseInt(mmsi), currentPosition.getLatitude(), currentPosition.getLongitude(), speed, course, trueHeading, navStatus);
			List<String> msgs1 = AISEncoder.getFinalAISMessages(msgType1);
			
			String binMsg5 = AISEncoder.getBinaryStringMsg5(Integer.parseInt(mmsi), vessel, eta, waypoints.get(waypoints.size()-1).getGeoCoordinate().getCityName(), posFixType);
			List<String> msgs5 = AISEncoder.getFinalAISMessages(binMsg5);

			getTrack().publish(msgs1);
			getTrack().publish(msgs5);
			
			try {
				Thread.sleep(messagePublishTimeInterval * 1_000);
			} catch (InterruptedException e) {
				log.debug("Exception at Thread {} ", e);
			}
			
			sentPoints++;
			if (sentPoints >= nrOfPoints) {
				if (currentWayPointIndex < waypoints.size()-2) {
					currentWayPointIndex++;
					updateWayPoint();
					log.debug("Change waypoint for {} to {} with {} points", mmsi, waypoints.get(currentWayPointIndex).getGeoCoordinate().getPointName(), nrOfPoints);
				} else {
					try {
						log.info("Track for mmsi={} terminated. Restart in {} minutes.", mmsi, Constants.DEFAULT_TRACK_SLEEP_TIME / 60_000);
						Thread.sleep(Constants.DEFAULT_TRACK_SLEEP_TIME);
						init();
					} catch (Exception e) {}
				}
			}
			
			double newLat = currentPosition.getLatitude() + factorLat * stepLat;
			double newLon = currentPosition.getLongitude() + factorLon * stepLon;
			currentPosition = new GeoCoordinate(newLat, newLon);

		}
		log.info("Thread killed for " + mmsi);	
	}

	@Override
	public void init() {
		mmsi = MMSIGenerator.next();
		vessel = VesselFactory.createRandom();
		
		speed = ThreadLocalRandom.current().nextInt(Constants.vesselSpeedMin, Constants.vesselSpeedMax);
		messagePublishTimeInterval = ThreadLocalRandom.current().nextInt(Constants.vesselMessagePublishTimeIntervalMin, Constants.vesselMessagePublishTimeIntervalMax);
		
		Point startHarbour = Cities.getRandomHarbour();
		//Create target harbour and be sure, that start and target are not the same.
		Point targetHarbour;
		do {
			 targetHarbour = Cities.getRandomHarbour();
		} while (targetHarbour.getGeoCoordinate().getPointName().equals(startHarbour.getGeoCoordinate().getPointName()));
		waypoints = Cities.getRandomRoute(startHarbour, targetHarbour);
		
		for (Point p : waypoints) {
			log.debug(p.getGeoCoordinate().getPointName() + "(" + p.getGeoCoordinate().getLatitude() + "," + p.getGeoCoordinate().getLongitude() + ")");
		}
		
		currentWayPointIndex = 0;
		factorLat = 1.0;
		factorLon = 1.0;
		
		updateWayPoint();
		
		log.info("Ship created: {} from ({}) to ({}) dist={}m, time={}s, points={}", mmsi, startHarbour.getGeoCoordinate().getCityName(), targetHarbour.getGeoCoordinate().getCityName(), (int)totalDistance, (int)totalTime, nrOfPoints);
	}
	
	private void updateWayPoint() {
		double lat1 = waypoints.get(currentWayPointIndex).getGeoCoordinate().getLatitude();
		double lon1 = waypoints.get(currentWayPointIndex).getGeoCoordinate().getLongitude();
		double lat2 = waypoints.get(currentWayPointIndex+1).getGeoCoordinate().getLatitude();
		double lon2 = waypoints.get(currentWayPointIndex+1).getGeoCoordinate().getLongitude();
		
		currentPosition = waypoints.get(currentWayPointIndex).getGeoCoordinate();
		totalDistance = GeoOps.getDistance(lat1, lon1, lat2, lon2);
		totalTime = totalDistance / this.speed;
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
		
		eta = new ETA(waypoints.get(currentWayPointIndex).getGeoCoordinate(), waypoints.get(waypoints.size()-1).getGeoCoordinate(), speed);
	}
	
	@Override
	public boolean containsName(String id) {
		if (mmsi.equalsIgnoreCase(id)) return true;
		return false;
	}

}
