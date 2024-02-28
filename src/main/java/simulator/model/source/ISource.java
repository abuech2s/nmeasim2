package simulator.model.source;

import simulator.model.GeoCoordinate;
import simulator.model.tracks.ITrack;

public interface ISource extends Runnable {
	public void start();
	public void stop();
	public void init();
	public ISource setTrack(ITrack track);
	public GeoCoordinate getCurrentPosition();
	public boolean containsName(String id);
}
