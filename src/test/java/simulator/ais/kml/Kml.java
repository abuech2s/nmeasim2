package simulator.ais.kml;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import lombok.Getter;

@XmlRootElement(name = "kml")
public class Kml {

	@XmlElement(name = "Document")
	@Getter private Document document;
	
}
