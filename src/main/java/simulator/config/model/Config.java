package simulator.config.model;

import javax.xml.bind.annotation.XmlAttribute;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import simulator.config.Constants;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType (XmlAccessType.FIELD)
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Config {
	
	@XmlAttribute (name = "type")
	@Getter private Type type;
	
	@XmlAttribute (name = "sink")
	@Getter private String sink;
	
	@XmlAttribute (name = "active")
	@Setter @Getter private boolean active = false;
	
	@XmlAttribute (name = "ip")
	@Getter private String ip;
	
	@XmlAttribute (name = "port")
	@Getter private int port;
	
	@XmlAttribute (name = "nroftrack")
	@Getter private int nroftrack = 1;
	
	@XmlAttribute (name = "radius")
	@Getter private int radius = 30000;
	
	@XmlAttribute (name = "sleeptime")
	private long sleeptime;
	
	@XmlAttribute (name = "initTrackId")
	@Getter private int initTrackId = 0;
	
	public long getSleepTime() {
		if (sleeptime == 0) return Constants.DEFAULT_TRACK_SLEEP_TIME;
		return sleeptime;
	}
}
