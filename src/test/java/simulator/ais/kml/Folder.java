package simulator.ais.kml;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;

public class Folder {
	
	private List<Placemark> placemarks;
	
	@XmlElement(name = "Placemark")
	public List<Placemark> getPlacemarks() {
		return placemarks;
	}

	public void setPlacemarks(List<Placemark> placemarks) {
		this.placemarks = placemarks;
	}
}
