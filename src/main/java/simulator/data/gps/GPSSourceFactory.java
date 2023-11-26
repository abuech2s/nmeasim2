package simulator.data.gps;

import simulator.model.ISourceFactory;
import simulator.model.source.ISource;

public class GPSSourceFactory implements ISourceFactory {

	@Override
	public ISource getSource() {
		return new GPSSource();
	}
	
	public static GPSSourceFactory get() {
		return new GPSSourceFactory();
	}
	
}
