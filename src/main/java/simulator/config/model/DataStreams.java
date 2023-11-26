package simulator.config.model;

import java.util.List;

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
public class DataStreams {

	@XmlElement(name = "Config")
	@Getter private List<Config> configs = null; 
	
	public Config getConfig(Type type) {
		for (Config config : configs) {
			if (config.getType() == type) return config;
		}
		return null;
	}
	
}
