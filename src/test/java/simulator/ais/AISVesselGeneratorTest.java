package simulator.ais;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import simulator.config.Constants;
import simulator.data.ais.IVessel;
import simulator.data.ais.VesselFactory;
import simulator.util.datagenerators.IMOGenerator;

public class AISVesselGeneratorTest {

	@Test
	public void createVesselTest() {
		IVessel vessel = VesselFactory.createRandom();
		assertTrue(vessel.getVesselName()!= null);
		assertTrue(!vessel.getVesselName().isEmpty());
		assertTrue(vessel.getCallsign()!= null);
		assertTrue(!vessel.getCallsign().isEmpty());
		assertTrue(vessel.getCallsign().length() == 7);
		assertTrue(vessel.getBow() <= Constants.vesselBowMax);
		assertTrue(vessel.getBow() >= Constants.vesselBowMin);
		assertTrue(vessel.getDraught() <= Constants.vesselDraughtMax);
		assertTrue(vessel.getDraught() >= Constants.vesselDraughtMin);
		assertTrue(vessel.getPort() <= Constants.vesselPortMax);
		assertTrue(vessel.getPort() >= Constants.vesselPortMin);
		assertTrue(vessel.getStarboard() <= Constants.vesselStarboardMax);
		assertTrue(vessel.getStarboard() >= Constants.vesselStarboardMin);
		assertTrue(vessel.getImo() >= Constants.vesselImoMin);
		assertTrue(vessel.getImo() <= Constants.vesselImoMax);
	}
	
	@Test
	public void createImoTest() {
		int imo = IMOGenerator.next();
		assertTrue(imo >= Constants.vesselImoMin);
		assertTrue(imo <= Constants.vesselImoMax);
		
		int imo2 = IMOGenerator.next();
		assertTrue(imo2 >= Constants.vesselImoMin);
		assertTrue(imo2 <= Constants.vesselImoMax);
		assertFalse(imo == imo2);
	}
	
}


