package simulator.model;

import java.util.Objects;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
public class GeoCoordinate {
	
	@Getter private double latitude = Double.NaN;
	@Getter private double longitude = Double.NaN;
	@Setter @Getter private String pointName = "";
	@Setter @Getter private String cityName = "";
	@Setter @Getter private boolean isACity = false;
	
	public GeoCoordinate(double latitude, double longitude) {
		setCoordinates(latitude, longitude);
	}
	
	public GeoCoordinate(String pointName, double latitude, double longitude) {
		setCoordinates(latitude, longitude);
		this.pointName = pointName;
	}
	
	public GeoCoordinate(String pointName, double latitude, double longitude, boolean isACity) {
		setCoordinates(latitude, longitude);
		this.pointName = pointName;
		this.isACity = isACity;
	}
	
	private void setCoordinates(double latitude, double longitude) {
		this.latitude = latitude;
		this.longitude = longitude;
	}
	
	@Override
	public boolean equals(Object o) {
		if (!(o instanceof GeoCoordinate)) return false;
		GeoCoordinate p = (GeoCoordinate)o;
		
		if (p.getLatitude() == this.getLatitude() && p.getLongitude() == this.getLongitude()) return true;
		
		return false;
	}
	
	@Override
	public int hashCode() {
	    return Objects.hash(this.latitude, this.longitude, this.pointName, this.cityName, this.isACity);
	}
	
}
