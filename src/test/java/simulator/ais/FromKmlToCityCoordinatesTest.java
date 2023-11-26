package simulator.ais;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.InputStream;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamReader;
import javax.xml.transform.stream.StreamSource;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import simulator.ais.kml.Kml;
import simulator.data.ais.geo.Cities;

public class FromKmlToCityCoordinatesTest {
	
	
	private static final Logger log = LoggerFactory.getLogger(Cities.class);

	@Test
	public void ParseTest() {
		
		InputStream stream = getFileFromResources("ais/doc.kml");
		try {
			
			XMLInputFactory xif = XMLInputFactory.newFactory();
			xif.setProperty(XMLInputFactory.IS_NAMESPACE_AWARE, false); 
			StreamSource source = new StreamSource(stream);
			XMLStreamReader xsr = xif.createXMLStreamReader(source);
			JAXBContext jc = JAXBContext.newInstance(Kml.class);
			Unmarshaller unmarshaller = jc.createUnmarshaller();
			Kml kml = (Kml) unmarshaller.unmarshal(xsr);
			
			assertEquals(100, kml.getDocument().getFolder().getPlacemarks().size());
			
		} catch (Exception e) {
			log.warn("Exception while loading cities {}", e);
		}
		
	}
	
	private static InputStream getFileFromResources(String fileName) {
		ClassLoader classLoader = Cities.class.getClassLoader();
		InputStream stream = classLoader.getResourceAsStream(fileName);
		if (stream == null) {
			throw new IllegalArgumentException("file: " + fileName + " not found!");
		} else {
			return stream;
		}
	}
	
}
