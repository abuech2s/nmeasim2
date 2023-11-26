package simulator.data.ais.geo;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import lombok.Getter;
import simulator.model.GeoCoordinate;

public class Point {

	@Getter private GeoCoordinate geoCoordinate;
	
	@Getter private List<String> neighbours;
	
	public Point(String pointName, String harbourName, double lat, double lon) {
		this.neighbours = new ArrayList<>();
		this.geoCoordinate = new GeoCoordinate(lat, lon);
		this.geoCoordinate.setPointName(pointName);
		if (harbourName != null) {
			this.geoCoordinate.setACity(true);
			this.geoCoordinate.setCityName(harbourName);
		}
	}
	
	public void addNeighbour(String neighbour) {
		this.neighbours.add(neighbour);
	}
	
	@Override
	public boolean equals(Object o) {
		if (!(o instanceof Point)) return false;
		Point p = (Point)o;
		if (p.getGeoCoordinate().equals(geoCoordinate)) return true;
		return false;
	}
	
	@Override
	public int hashCode() {
	    return Objects.hash(this.geoCoordinate, this.neighbours);
	}
}
