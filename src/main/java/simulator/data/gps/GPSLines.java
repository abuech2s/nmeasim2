package simulator.data.gps;

import java.util.ArrayList;
import java.util.List;
import simulator.model.GeoCoordinate;

public class GPSLines {
	
	public static List<GeoCoordinate> getGPSRoute() {
		
		List<GeoCoordinate> balticSea = new ArrayList<>();
		balticSea.add(new GeoCoordinate(54.6746753,10.0616863));
		balticSea.add(new GeoCoordinate(54.5123633,10.1550701));
		balticSea.add(new GeoCoordinate(54.3910041,10.8087566));
		balticSea.add(new GeoCoordinate(54.6429004,10.9680584));
		balticSea.add(new GeoCoordinate(54.2676809,12.0529583));
		balticSea.add(new GeoCoordinate(54.8568991,13.0774334));
		balticSea.add(new GeoCoordinate(54.8005945,14.10937));
		balticSea.add(new GeoCoordinate(55.2352963,12.8020389));
		balticSea.add(new GeoCoordinate(54.7531695,12.6207644));
		balticSea.add(new GeoCoordinate(54.4492912,11.7198855));
		balticSea.add(new GeoCoordinate(54.6960674,11.1595828));
		balticSea.add(new GeoCoordinate(54.6746753,10.0616863));
		return balticSea;
		
	}

}
