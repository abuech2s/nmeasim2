package simulator.data.ais;

import java.util.concurrent.ThreadLocalRandom;

import lombok.AllArgsConstructor;
import lombok.Getter;
import simulator.config.Constants;
import simulator.util.datagenerators.CallSignGenerator;
import simulator.util.datagenerators.IMOGenerator;
import simulator.util.datagenerators.VesselnameGenerator;

@Getter
@AllArgsConstructor
public class Vessel implements IVessel {
	
	private int imo;
	private String callsign;
	private String vesselName;
	private int draught;
	private int bow;
	private int stern;
	private int port;
	private int starboard;
	private int shipType;
	
	public Vessel() {
		imo = IMOGenerator.next();
		callsign = CallSignGenerator.next();
		vesselName = VesselnameGenerator.next();
		draught = ThreadLocalRandom.current().nextInt(Constants.vesselDraughtMin, Constants.vesselDraughtMax);
		bow = ThreadLocalRandom.current().nextInt(Constants.vesselBowMin, Constants.vesselBowMax);
		stern = ThreadLocalRandom.current().nextInt(Constants.vesselSternMin, Constants.vesselSternMax);
		port = ThreadLocalRandom.current().nextInt(Constants.vesselPortMin, Constants.vesselPortMax);
		starboard = ThreadLocalRandom.current().nextInt(Constants.vesselStarboardMin, Constants.vesselStarboardMax);
		shipType = ThreadLocalRandom.current().nextInt(Constants.vesselShiptypeMin, Constants.vesselShiptypeMax);
	}
	
}
