package simulator.config.model;

import javax.xml.bind.annotation.XmlRootElement;

import lombok.Getter;
import lombok.ToString;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlRootElement(name = "AppConfig")
@XmlAccessorType (XmlAccessType.FIELD)
@ToString
public class AppConfig {
	
	@XmlElement(name = "Mode")
	@Getter Mode mode;
	
	@XmlElement(name = "General")
	@Getter General general;

	@XmlElement(name = "DataStreams")
	@Getter DataStreams dataStreams;
	
	@XmlElement(name = "AdditionalFeatures")
	@Getter AdditionalFeatures additionalFeatures;
	
}
