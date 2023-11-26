package simulator.model.tracks;

import java.util.Objects;

import lombok.Getter;

public class RadarContact {

	@Getter private int radarTrackId;
	@Getter private long lastDetection;
	
	public RadarContact(int radarTrackId) {
		this.radarTrackId = radarTrackId;
		lastDetection = System.currentTimeMillis();
	}
	
	public void updateTimeStamp() {
		this.lastDetection = System.currentTimeMillis();
	}
	
	@Override
	public boolean equals(Object o) {
		if (!(o instanceof RadarContact)) return false;
		RadarContact r = (RadarContact)o;
		
		if (r.getRadarTrackId() == this.radarTrackId) return true;
		
		return false;
	}
	
	@Override
	public int hashCode() {
	    return Objects.hash(this.radarTrackId);
	}
	
}
