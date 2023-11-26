package simulator.model.tracks;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import simulator.config.model.AppConfig;

public class TrackAdministration {

	private static final Logger log = LoggerFactory.getLogger(TrackAdministration.class);
	
	private static List<ITrack> tracks = null;
	
	public synchronized static void reInit(AppConfig appConfig) {
		if (tracks == null) tracks = new ArrayList<>();
		
		stopTracks();
		
		tracks = LoaderFromSimulator.loadFromSimulator(appConfig);

		printTracks();
		startTracks();
	}
	
	private static void stopTracks() {
		for (ITrack track: tracks) {
			track.kill();
		}
		tracks.clear();
	}
	
	private static void startTracks() {
		for (ITrack track: tracks) {
			track.start();
		}
	}
	
	private static void printTracks() {
		log.info("Created Tracks:");
		for (ITrack track : tracks) {
			log.info("   {}", track.toString());
		}
	}
	
	public static List<ITrack> getTracks() {
		return tracks;
	}
	
	public static ITrack getTrack(String token) {
		for (ITrack track : tracks) {
			if (track.getToken().equals(token)) {
				return track;
			}
		}
		return null;
	}
	
}
