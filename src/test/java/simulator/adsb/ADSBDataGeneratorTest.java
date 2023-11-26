package simulator.adsb;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;


import org.junit.jupiter.api.Test;

import simulator.util.datagenerators.CallSignGenerator;
import simulator.util.datagenerators.HexIdentGenerator;

public class ADSBDataGeneratorTest {

	@Test
	public void generateCallSignTest() {
		String callsign = CallSignGenerator.next();
		assertEquals(7, callsign.length());
	}
	
	@Test
	public void generateHexIdentTest() {
		String hexIdent = HexIdentGenerator.next();
		assertTrue(hexIdent.startsWith("HEX"));
		assertEquals(6, hexIdent.length());
		
		String hexIdent2 = HexIdentGenerator.next();
		assertFalse(hexIdent.equals(hexIdent2));
	}
	
}
