package simulator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.junit.jupiter.api.Test;

import simulator.config.model.AppConfig;
import simulator.util.StreamUtils;

public class LoadConfigTest {

	@Test
	public void ConfigTest() throws JAXBException {
		JAXBContext jaxbContext = JAXBContext.newInstance(AppConfig.class);
		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
		
		AppConfig appConfig = (AppConfig) jaxbUnmarshaller.unmarshal(StreamUtils.getFileFromResources("testConfig.xml"));
		
		assertEquals(6, appConfig.getDataStreams().getConfigs().size());
		assertTrue(appConfig.getGeneral().isActivateDataLogging());
		
		assertEquals(15,  appConfig.getGeneral().getConfigReloadTime());
		
		assertTrue(appConfig != null);
	}
	
}
