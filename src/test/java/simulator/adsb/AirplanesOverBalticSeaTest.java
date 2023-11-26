package simulator.adsb;

import simulator.model.GeoCoordinate;
import simulator.util.GeoOps;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class AirplanesOverBalticSeaTest {

	@Test
	public void CircularTest() {
		
		double lat = 55.20;
		double lon = 10.50;
		
		GeoCoordinate startingPoint = new GeoCoordinate(lat, lon);
		double dist = 100000;
		
		for (int i = 0; i < 360; i++) {
			GeoCoordinate gc = GeoOps.calcPointFromAngleAndDistance(startingPoint, dist, i);
			assertTrue(gc.getLatitude() > 54.30 && gc.getLatitude() < 56.10);
			assertTrue(gc.getLongitude() > 8.79 && gc.getLongitude() < 12.10);
		}
		
	}

	
}
