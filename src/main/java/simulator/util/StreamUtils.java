package simulator.util;

import java.io.InputStream;

public class StreamUtils {

	public static InputStream getFileFromResources(String fileName) {
		ClassLoader classLoader = StreamUtils.class.getClassLoader();
		InputStream stream = classLoader.getResourceAsStream(fileName);
		if (stream == null) {
			throw new IllegalArgumentException("file: " + fileName + " not found!");
		} else {
			return stream;
		}
	}
	
}
