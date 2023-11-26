package simulator.util.datagenerators;

import lombok.Setter;
import simulator.config.Constants;

public class MMSIGenerator {
	
	@Setter private static int initTrackId;
	private static long counter = Constants.vesselMmsiMin - 1;
	private static long maxCount = Constants.vesselMmsiMax;
	
	public synchronized static String next() {
		counter++;
		long mmsi = (counter + initTrackId) % maxCount;
		if (counter > maxCount) counter = -initTrackId;
		return String.valueOf(mmsi);
	}

}
