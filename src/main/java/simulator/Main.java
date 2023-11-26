package simulator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import simulator.config.Configuration;
import simulator.config.Constants;
import simulator.util.GC;
import simulator.util.Version;

public class Main {

	private static final Logger log = LoggerFactory.getLogger(Main.class);
	
	public static void main(String[] args) {	
		if (args.length > 0) {
			if (args[0].endsWith("-help")) help();
			exit("Unknown argument. Use argument -help for more information.");
		}
		
		initConfiguration();
		initGarbageCollection();
		
		log.info("Version: {} (Build: {})", Version.get("Version"), Version.get("Date"));
		log.info("Simulator started." + Constants.RCLF);
	}
	
	private static void initConfiguration() {
		Thread configurationThread = new Thread(new Configuration(), "Configuration");
		configurationThread.setDaemon(false);
		configurationThread.start();
	}
	
	private static void initGarbageCollection() {
		Thread gcThread = new Thread(new GC(), "GC");
		gcThread.setDaemon(false);
		gcThread.start();
	}
	
	public static void exit(String message) {
		log.error("ERROR: {}", message);
		System.exit(-1);
	}
	
	public static void help() {
		log.error("");
		log.error("Usage: java -jar *.jar");
		log.error("");
		log.error("config.xml must exist in the same directory.");
		System.exit(-1);
	}
	
}
