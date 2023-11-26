package simulator.data.ais;

import simulator.util.datagenerators.CallSignGenerator;
import simulator.util.datagenerators.IMOGenerator;

public class VesselFactory {

	public static IVessel createRandom() {
		return new Vessel();
	}
	
	public static IVessel createRussianVessel(String vesselName) {
		return new Vessel(IMOGenerator.next(), CallSignGenerator.next(), vesselName, 25, 5, 6, 10, 12, 35);
	}
	
	public static IVessel createFisherVessel(String vesselName) {
		return new Vessel(IMOGenerator.next(), CallSignGenerator.next(), vesselName, 4, 3, 3, 5, 7, 30);
	}
	
}
