package simulator.util.datagenerators;

import lombok.Setter;
import simulator.config.Constants;
import simulator.model.tracks.TrackAdministration;

public class HexIdentGenerator {
	
	@Setter private static int initTrackId;
	private static int counter = Constants.airplaneHexIdentMin - 1;
	private static int maxCount = Constants.airplaneHexIdentMax;

	public synchronized static String next() {
		int hexIdent;
		do {
			counter++;
			hexIdent = (counter + initTrackId) % maxCount;
			if (counter > maxCount) counter = -initTrackId;
		} while (TrackAdministration.alreadyExists(format(hexIdent)));
		return format(hexIdent);
	}
	
	private static String format(int hexIdent) {
		return "HEX" + String.format("%03d", hexIdent);
	}
}
