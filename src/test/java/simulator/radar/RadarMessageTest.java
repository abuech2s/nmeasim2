package simulator.radar;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import org.junit.jupiter.api.Test;

import simulator.data.radar.RadarSource;

public class RadarMessageTest {
	
	@Test
	@SuppressWarnings("rawtypes")
	public void BuildMessageTest() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		
		String result[] = new String[]{"$RATTM", "4", "00.1", "034.5", "T", "0.0", "0.0", "T", "0.0", "0.0", "N", "TRK4", "T", "", "205604.48", "A", "*5C"};
		
		RadarSource rs = new RadarSource();
		Class[] cArg = new Class[]{ int.class, double.class, double.class };
		Method method = rs.getClass().getDeclaredMethod("buildMessage", cArg);
		method.setAccessible(true);
		String calculatedResult = (String)method.invoke(rs, 4, 123.4, 34.5);
		
		String elem[] = calculatedResult.split(",");
		assertEquals(17, elem.length);
		
		assertEquals(elem[0], result[0]);
		assertEquals(elem[1], result[1]);
		assertEquals(elem[2], result[2]);
		assertEquals(elem[3], result[3]);
		assertEquals(elem[4], result[4]);
		assertEquals(elem[5], result[5]);
		assertEquals(elem[6], result[6]);
		assertEquals(elem[7], result[7]);
		assertEquals(elem[8], result[8]);
		assertEquals(elem[9], result[9]);
		assertEquals(elem[10], result[10]);
		assertEquals(elem[11], result[11]);
		assertEquals(elem[12], result[12]);
		assertEquals(elem[13], result[13]);
		assertEquals(9, elem[14].length());
		assertEquals(elem[15], result[15]);
		assertEquals(3, elem[16].length());

	}
}
