package simulator.data.radar;

import simulator.model.ISourceFactory;
import simulator.model.source.ISource;

public class RadarSourceFactory implements ISourceFactory {

	@Override
	public ISource getSource() {
		return new RadarSource();
	}
	
	public static RadarSourceFactory get() {
		return new RadarSourceFactory();
	}

}
