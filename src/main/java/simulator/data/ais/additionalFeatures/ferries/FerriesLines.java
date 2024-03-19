package simulator.data.ais.additionalFeatures.ferries;

import java.util.ArrayList;
import java.util.List;
import simulator.model.GeoCoordinate;

public class FerriesLines {
	
	public static List<GeoCoordinate> getFerry1Route() {
		List<GeoCoordinate> list = new ArrayList<>();
		list.add(new GeoCoordinate(54.65419214233147, 11.34827155038206));
		list.add(new GeoCoordinate(54.50328332460039, 11.228849553716762));
		list.add(new GeoCoordinate(54.65419214233147, 11.34827155038206));
		return list;
	}
	
	public static List<GeoCoordinate> getFerry2Route() {
		List<GeoCoordinate> list = new ArrayList<>();
		list.add(new GeoCoordinate(54.17461539458806, 12.08292202620231));
		list.add(new GeoCoordinate(54.573186427427025, 11.92542722478814));
		list.add(new GeoCoordinate(54.17461539458806, 12.08292202620231));
		return list;
	}
	
	public static List<GeoCoordinate> getFerry3Route() {
		List<GeoCoordinate> list = new ArrayList<>();
		list.add(new GeoCoordinate(53.922469225199585, 14.304226307205873));
		list.add(new GeoCoordinate(55.36352920768457, 13.160802447113497));
		list.add(new GeoCoordinate(53.922469225199585, 14.304226307205873));
		return list;
	}
	
}