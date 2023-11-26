package simulator.ais.kml;

import javax.xml.bind.annotation.XmlElement;

import lombok.Getter;

public class Point {

	@XmlElement(name = "coordinates")
	@Getter private String coordinates;
	
	public double getLatitude() {
		if (coordinates == null || coordinates.isEmpty()) {
			return Double.NaN;
		} else {
			String[] elem = coordinates.split(",");
			return Double.parseDouble(elem[0]);
		}
	}
	
	public double getLongitude() {
		if (coordinates == null || coordinates.isEmpty()) {
			return Double.NaN;
		} else {
			String[] elem = coordinates.split(",");
			return Double.parseDouble(elem[1]);
		}
	}
	
}
