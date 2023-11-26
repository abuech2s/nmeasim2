package simulator.config.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@XmlAccessorType (XmlAccessType.FIELD)
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AdditionalFeatures {

	@XmlElement(name = "AirplanesOverBalticSea")
	@Getter boolean airplanesOverBalticSea;
	
	@XmlElement(name = "PatrolVessels")
	@Getter boolean patrolVessels;
	
	@XmlElement(name = "FishingVessels")
	@Getter boolean fishingVessels;
	
	@XmlElement(name = "FerryVessels")
	@Getter boolean ferryVessels;
	
}
