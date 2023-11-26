package simulator.ais.kml;

import javax.xml.bind.annotation.XmlElement;

import lombok.Getter;

public class Placemark {

	@XmlElement(name = "name")
	@Getter private String name;
	
	@XmlElement(name = "Point")
	@Getter private Point point;
	
}
