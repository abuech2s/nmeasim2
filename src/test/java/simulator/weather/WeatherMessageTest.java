package simulator.weather;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.junit.jupiter.api.Test;

import simulator.data.weather.WeatherSource;

public class WeatherMessageTest {

	@Test
	public void BuildMessageTest() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		
		String result[] = new String[]{"$WIMDA", "1.000", "I", "0.001", "B", "15.1", "C", "", "", "55.5", "60.3", "23", "C", "0.1", "T", "5.1", "M", "10.3", "N", "5.3", "M*6C"};
		
		WeatherSource rs = new WeatherSource();
		Method method = rs.getClass().getDeclaredMethod("buildMessage");
		method.setAccessible(true);
		String calculatedResult = (String)method.invoke(rs);
		
		String elem[] = calculatedResult.split(",");
		assertEquals(21, elem.length);
		
		for (int i = 0; i < 21; i++) {
			assertEquals(elem[i], result[i]);
		}
	
	}

}
