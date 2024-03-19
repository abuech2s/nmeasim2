package simulator.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.NoSuchFileException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashSet;
import java.util.Set;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import simulator.config.model.AppConfig;
import simulator.config.model.Config;
import simulator.config.model.Mode;
import simulator.config.model.Type;
import simulator.model.tracks.TrackAdministration;
import simulator.util.logging.Logging;
import simulator.Main;

public class Configuration implements Runnable {
	
	private static final Logger log = LoggerFactory.getLogger(Configuration.class);
	
	private static AppConfig appConfig = null;
	private MessageDigest md5Digest = null;
	private File file = null;
	private JAXBContext jaxbContext = null;
	private Unmarshaller jaxbUnmarshaller = null;
	private String lastChecksum = "";

	private void init() throws JAXBException, NoSuchAlgorithmException, NoSuchFileException {
		if (md5Digest == null) {
			md5Digest = MessageDigest.getInstance("MD5");
			file = new File(Constants.CONFIG_FILENAME);
			
			if (!file.exists()) {
				throw new NoSuchFileException("File " + Constants.CONFIG_FILENAME + " does not exist.");
			}
			
			jaxbContext = JAXBContext.newInstance(AppConfig.class);
			jaxbUnmarshaller = jaxbContext.createUnmarshaller();
		}
		
		try {
			String currentChecksum = getFileChecksum(md5Digest, file);
			if (lastChecksum.isEmpty() || !currentChecksum.equals(lastChecksum)) {
				appConfig = (AppConfig) jaxbUnmarshaller.unmarshal(file);
		        log.info("(Re)load config file with mode = {}", appConfig.getMode());
		        
		        if (appConfig.getMode() == Mode.Generator) {
			        validateConfig(appConfig);
			        TrackAdministration.reInit(appConfig);
			        Logging.reinit(appConfig);
			        lastChecksum = currentChecksum;
		        } else {
		        	log.error("Was not able to start simulator with mode = " + appConfig.getMode() + ". XML-Tag <Mode> is missing in config file.");
		        	System.exit(-1);
		        	//TODO: Mode = Mode.FILE
		        }
			}
		} catch (IOException e) {
			log.warn("Could not calculate hash from config file.", e);
		}
	}

	@Override
	public void run() {
		while (true) {
			try {
				init();
			} catch (NoSuchFileException e) {
				log.warn("File not found: {}", e);
				break;
			} catch (Exception e) {
				log.warn("Exception while loading config file {}", e);
			}
			try { Thread.sleep(appConfig.getGeneral().getConfigReloadTime() * 1_000L ); } catch (Exception e) {}
		}
	}
	
	private static String getFileChecksum(MessageDigest digest, File file) throws IOException {
	    FileInputStream fis = new FileInputStream(file);
	     
	    byte[] byteArray = new byte[1024];
	    int bytesCount = 0; 
	  
	    while ((bytesCount = fis.read(byteArray)) != -1) {
	        digest.update(byteArray, 0, bytesCount);
	    };

	    fis.close();

	    byte[] bytes = digest.digest();

	    StringBuilder sb = new StringBuilder();
	    for (int i = 0; i < bytes.length; i++) {
	        sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
	    }

	   return sb.toString();
	}
	
	private boolean validateConfig(AppConfig appconfig) {
		//Check for additionally needed GPS support:
		for (Config config : appconfig.getDataStreams().getConfigs()) {
			if (config.isActive() && (config.getType() == Type.RADAR || config.getType() == Type.WEATHER)) {
				appconfig.getDataStreams().getConfig(Type.GPS).setActive(true);
			}
		}
		
		//Check if ports exist multiple times:
		Set<Integer> ports = new HashSet<>();
		for (Config c : appconfig.getDataStreams().getConfigs()) {
			ports.add(c.getPort());
		}
		if (ports.size() != appconfig.getDataStreams().getConfigs().size()) Main.exit("At least two configs have the same port.");

		return true;
	}
	
}
