package simulator.util.datagenerators;

import lombok.Setter;
import simulator.config.Constants;

public class HexIdentGenerator {
	
	@Setter private static int initTrackId;
	private static int counter = Constants.airplaneHexIdentMin - 1;
	private static int maxCount = Constants.airplaneHexIdentMax;

	public synchronized static String next() {
		counter++;
		int hexIdent = (counter + initTrackId) % maxCount;
		if (counter > maxCount) counter = -initTrackId;
		return "HEX" + String.format("%03d", hexIdent);
	}
	
}
