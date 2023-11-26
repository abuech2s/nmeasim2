package simulator.data.ais.additionalFeatures.fishers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import simulator.model.GeoCoordinate;

public class FishersLines {
	
	public static List<GeoCoordinate> getF1Route() {
		return createWayPointsA(53.957594, 10.881564);
	}
	
	public static List<GeoCoordinate> getF2Route() {
		return createWayPointsA(54.141970, 10.963420);
	}
	
	public static List<GeoCoordinate> getF3Route() {
		return createWayPointsA(53.993816, 10.795184);
	}
	
	public static List<GeoCoordinate> getF4Route() {
		return createWayPointsA(54.180187, 11.055161);
	}
	
	public static List<GeoCoordinate> getF5Route() {
		return createWayPointsA(54.013745, 11.178430);
	}
	
	public static List<GeoCoordinate> getF6Route() {
		return createWayPointsB(55.454391,12.210379);
	}
	
	public static List<GeoCoordinate> getF7Route() {
		return createWayPointsB(55.566394,12.294005);
	}
	
	public static List<GeoCoordinate> getF8Route() {
		return createWayPointsB(55.586009,12.910177);
	}
	
	public static List<GeoCoordinate> getF9Route() {
		return createWayPointsB(55.602757,12.462585);
	}
	
	public static List<GeoCoordinate> getF10Route() {
		return createWayPointsB(55.418791,12.829095);
	}

	private static List<GeoCoordinate> createWayPointsA(double lat, double lon) {
		List<GeoCoordinate> list = new ArrayList<>();
		list.add(new GeoCoordinate(lat, lon));
		list.addAll(getRandomA());
		list.add(new GeoCoordinate(lat, lon));
		return list;
	}
	
	private static List<GeoCoordinate> createWayPointsB(double lat, double lon) {
		List<GeoCoordinate> list = new ArrayList<>();
		list.add(new GeoCoordinate(lat, lon));
		list.addAll(getRandomB());
		list.add(new GeoCoordinate(lat, lon));
		return list;
	}
	
	private static List<GeoCoordinate> getRandomA() {
		List<GeoCoordinate> rnd = new ArrayList<>();
		rnd.add(new GeoCoordinate(54.066828, 11.063166));
		rnd.add(new GeoCoordinate(54.073989, 11.081354));
		rnd.add(new GeoCoordinate(54.072987, 11.121354));
		rnd.add(new GeoCoordinate(54.071655, 11.056833));
		rnd.add(new GeoCoordinate(54.065783, 11.111378));
		rnd.add(new GeoCoordinate(54.066176, 11.073071));
		rnd.add(new GeoCoordinate(54.068378, 11.130894));
		rnd.add(new GeoCoordinate(54.069705, 11.105157));
		Collections.shuffle(rnd);
		return rnd;
	}
	
	private static List<GeoCoordinate> getRandomB() {
		List<GeoCoordinate> rnd = new ArrayList<>();
		rnd.add(new GeoCoordinate(54.066828, 11.063166));
		rnd.add(new GeoCoordinate(54.073989, 11.081354));
		rnd.add(new GeoCoordinate(54.072987, 11.121354));
		rnd.add(new GeoCoordinate(54.071655, 11.056833));
		rnd.add(new GeoCoordinate(54.065783, 11.111378));
		rnd.add(new GeoCoordinate(54.066176, 11.073071));
		rnd.add(new GeoCoordinate(54.068378, 11.130894));
		rnd.add(new GeoCoordinate(54.069705, 11.105157));
		Collections.shuffle(rnd);
		return rnd;
	}
}