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
public class General {

	@XmlElement(name = "ActivateDataLogging")
	@Getter private boolean activateDataLogging;
	
	@XmlElement(name = "ConfigReloadTime", defaultValue = "15")
	@Getter private int configReloadTime;
}
