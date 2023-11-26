package simulator.data.ais.additionalFeatures.patrols;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import simulator.model.GeoCoordinate;

public class PatrolLines {
	
	public static List<GeoCoordinate> getP1Route() {
		List<GeoCoordinate> p1 = new ArrayList<>();
		p1.add(new GeoCoordinate(55.348855,14.164991));
		p1.add(new GeoCoordinate(55.125138,14.644462));
		return p1;
	}
	
	public static List<GeoCoordinate> getP2Route() {
		List<GeoCoordinate> p2 = new ArrayList<>();
		p2.add(new GeoCoordinate(55.046925,14.760550));
		p2.add(new GeoCoordinate(54.636729,14.581488));
		p2.add(new GeoCoordinate(54.247000,13.827964));
		return p2;
	}
	
	public static List<GeoCoordinate> getP3Route() {
		List<GeoCoordinate> p3 = new ArrayList<>();
		p3.add(new GeoCoordinate(55.462549,12.831971));
		p3.add(new GeoCoordinate(55.542035,12.612569));
		return p3;
	}
	
	public static List<GeoCoordinate> reverse(List<GeoCoordinate> list) {
		List<GeoCoordinate> copy = list.subList(0, list.size());
		Collections.reverse(copy);
		return copy;
	}
	
	public static List<GeoCoordinate> getP4Route() {
		List<GeoCoordinate> p4 = new ArrayList<>();
		p4.add(new GeoCoordinate(54.402387,14.193286));
		p4.add(new GeoCoordinate(54.459807,14.284911));
		return p4;
	}
	
	public static List<GeoCoordinate> getP5Route() {
		List<GeoCoordinate> p5 = new ArrayList<>();
		p5.add(new GeoCoordinate(54.795842,14.665608));
		p5.add(new GeoCoordinate(54.731523,14.638731));
		return p5;
	}

}
