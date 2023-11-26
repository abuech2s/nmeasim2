package simulator.util.datagenerators;

import simulator.config.Constants;

public class RadarTrackIdGenerator {
	
	private static int radarTrackId = Constants.radarTrackIdMin - 1;
	
	public synchronized static int next() {

		radarTrackId++;
		if (radarTrackId > Constants.radarTrackIdMax) {
			radarTrackId = Constants.radarTrackIdMin;
		}
		
		return radarTrackId;
	}

}
