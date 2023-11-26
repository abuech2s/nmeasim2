package simulator.ais;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import simulator.data.ais.geo.Cities;
import simulator.data.ais.geo.Point;

public class LoadingAisDataTest {
	
	private static double EPS = 0.0001;
	
	@Test
	public void LoadAisDataTestWithAHarbour() throws Exception {
		Point point = Cities.getHarbour("MALMOE");
		
		assertEquals("094_MALMOE", point.getGeoCoordinate().getPointName());
		assertEquals(12.96814847182144, point.getGeoCoordinate().getLatitude(), EPS);
		assertEquals(55.61342327972536, point.getGeoCoordinate().getLongitude(), EPS);
		assertEquals(1, point.getNeighbours().size());
		assertEquals("095", point.getNeighbours().get(0));
		assertEquals(100, Cities.getSize());
	}
	
	@Test
	public void LoadAisDataTestWithANonHarbour() throws Exception {
		Point point = Cities.getPoint("095");
		
		assertEquals(12.64194302721624, point.getGeoCoordinate().getLatitude(), EPS);
		assertEquals(55.49393542788729, point.getGeoCoordinate().getLongitude(), EPS);
		assertEquals(3, point.getNeighbours().size());
		assertEquals("094_MALMOE", point.getNeighbours().get(0));
		assertEquals("097", point.getNeighbours().get(1));
		assertEquals("096", point.getNeighbours().get(2));
		assertEquals(100, Cities.getSize());
	}
	
	@Test
	public void LoadAisDataTestWithACityAsPoint() throws Exception {
		Point point = Cities.getPoint("094_MALMOE");
		
		assertEquals(12.96814847182144, point.getGeoCoordinate().getLatitude(), EPS);
		assertEquals(55.61342327972536, point.getGeoCoordinate().getLongitude(), EPS);
		assertEquals(1, point.getNeighbours().size());
		assertEquals("095", point.getNeighbours().get(0));
		assertEquals(100, Cities.getSize());
	}
}
