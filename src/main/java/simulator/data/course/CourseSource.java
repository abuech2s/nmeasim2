package simulator.data.course;

import java.util.concurrent.ThreadLocalRandom;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import lombok.Getter;
import simulator.config.Constants;
import simulator.model.GeoCoordinate;
import simulator.model.source.AbstractSource;
import simulator.model.tracks.TrackAdministration;
import simulator.util.GeoOps;

public class CourseSource extends AbstractSource {

	private static final Logger log = LoggerFactory.getLogger(CourseSource.class);
	
	@Getter private GeoCoordinate currentPositionBefore; 
	@Getter private GeoCoordinate currentPosition; 
	
	private int messagePublishTimeInterval; // in [s]
	
	@Override
	public void run() {
		init();
		while (!kill) {
			GeoCoordinate currentPositionNow = TrackAdministration.getTrack(Constants.TOKEN_GPS).getSources().get(0).getCurrentPosition();
			
			if (currentPositionNow == null) continue;
			
			if (currentPosition == null || !currentPosition.equals(currentPositionNow)) {
				currentPositionBefore = currentPosition;
				currentPosition = currentPositionNow;
			}
			
			if (currentPositionBefore == null) {
				continue;
			}
			
			double bearing = GeoOps.getBearing(currentPositionBefore.getLatitude(), currentPositionBefore.getLongitude(), currentPosition.getLatitude(), currentPosition.getLongitude());
			
			String msgGphdt = CourseMessages.MSG_GPHDT;
			msgGphdt = msgGphdt.replace("${course}", String.format("%.3f", bearing).replaceAll(",", "."));
			
			String msgHehdt = CourseMessages.MSG_HEHDT;
			msgHehdt = msgHehdt.replace("${course}", String.format("%.3f", bearing).replaceAll(",", "."));
			
			
			msgGphdt = "$" + msgGphdt + "*" + GeoOps.calcCheckSum(msgGphdt);
			msgHehdt = "$" + msgHehdt + "*" + GeoOps.calcCheckSum(msgHehdt);
			
			getTrack().publish(msgGphdt);
			getTrack().publish(msgHehdt);
			
			try {
				Thread.sleep(messagePublishTimeInterval * 1_000);
			} catch (InterruptedException e) {
				log.debug("Exception at Thread {} ", e);
			}
			
		}
	}
	
	@Override
	public void init() {
		messagePublishTimeInterval = ThreadLocalRandom.current().nextInt(Constants.courseMessagePublishTimeIntervalMin, Constants.courseMessagePublishTimeIntervalMax);
		log.info("course device created.");
	}
	
	@Override
	public boolean containsName(String id) {
		return true;
	}
	
}
