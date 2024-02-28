package simulator.adsb;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.junit.jupiter.api.Test;

import simulator.config.Constants;
import simulator.data.adsb.geo.Cities;
import simulator.model.GeoCoordinate;
import simulator.util.GeoOps;

public class FindClosestCitiesTest {
	
	@Test
	public void findClosestCitiesTest() throws NumberFormatException, IOException {
		
		// Load cities
		ClassLoader classLoader = Cities.class.getClassLoader();
		InputStream stream = classLoader.getResourceAsStream(Constants.FILE_ADSBCITIES);

		Map<String, GeoCoordinate> cities = new HashMap<>();
		BufferedReader br = new BufferedReader(new InputStreamReader(stream));
		String line;
		while ((line = br.readLine()) != null) {
			String[] elem = line.split(",");
			double lat = Double.parseDouble(elem[1]);
			double lon = Double.parseDouble(elem[2]);
			cities.put(elem[0], new GeoCoordinate(elem[0], lat, lon, true));
		}
		br.close();
		
		//Find closest cities
		double dist = Double.MAX_VALUE;
		String city1 = "";
		String city2 = "";
		
		for (Entry<String, GeoCoordinate> entry1 : cities.entrySet()) {
			for (Entry<String, GeoCoordinate> entry2 : cities.entrySet()) {
				double d = GeoOps.getDistance(entry1.getValue().getLatitude(), entry1.getValue().getLongitude(), entry2.getValue().getLatitude(), entry2.getValue().getLongitude());
				if (d != 0.0 && d < dist) {
					dist = d;
					city1 = entry1.getKey();
					city2 = entry2.getKey();
				}
			}
		}
		
		assertEquals(1616.4192060785758, dist);
		assertEquals("posadas", city1);
		assertEquals("encarnacion", city2);
	}
}
