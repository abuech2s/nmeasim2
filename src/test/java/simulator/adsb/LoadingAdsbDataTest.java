package simulator.adsb;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import simulator.data.adsb.geo.Cities;
import simulator.model.GeoCoordinate;

public class LoadingAdsbDataTest {
	
	private static double EPS = 0.0001;
	
	@Test
	public void LoadAdsbDataTest() throws Exception {
		GeoCoordinate geoCoordinate = Cities.get("tokyo");
		
		assertEquals("tokyo", geoCoordinate.getPointName());
		assertEquals(35.6850, geoCoordinate.getLatitude(), EPS);
		assertEquals(139.7514, geoCoordinate.getLongitude(), EPS);
		assertEquals(3181, Cities.getSize());
	}
}
