package simulator.ais.kml;

import javax.xml.bind.annotation.XmlElement;

import lombok.Getter;

public class Document {
	
	@XmlElement(name = "Folder")
	@Getter private Folder folder;
	
}
