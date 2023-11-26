package simulator.util.logging;

import simulator.config.model.AppConfig;
import simulator.model.sinks.AbstractSink;

public class Logging {

	public static void reinit(AppConfig appConfig) {
		if (appConfig.getGeneral().isActivateDataLogging()) {
			AbstractSink.activateLogging = true;
		} else {
			AbstractSink.activateLogging = false;
		}
	}
	
}

