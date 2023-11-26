package simulator.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GC implements Runnable {

	private static final Logger log = LoggerFactory.getLogger(GC.class);
	
	@Override
	public void run() {
		while(true) {
			try {
				Thread.sleep(60_000L);
			} catch (InterruptedException e) {
				log.warn("Exception: ", e);
			}
			System.gc();
			log.info("Garbage Collected.");
		}
	}

}
