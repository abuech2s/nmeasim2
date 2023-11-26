package simulator.util.datagenerators;

import simulator.config.Constants;

public class IMOGenerator {
	
	private static int imoCurrent = Constants.vesselImoMin - 1;
	
	public synchronized static int next() {

		imoCurrent++;
		if (imoCurrent > Constants.vesselImoMax) {
			imoCurrent = Constants.vesselImoMin;
		}
		
		return imoCurrent;
	}

}
