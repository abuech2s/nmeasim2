package simulator.data.adsb;

import simulator.model.ISourceFactory;
import simulator.model.source.ISource;

public class ADSBSourceFactory implements ISourceFactory {

	@Override
	public ISource getSource() {
		return new ADSBSource();
	}
	
	public static ADSBSourceFactory get() {
		return new ADSBSourceFactory();
	}
	
}
