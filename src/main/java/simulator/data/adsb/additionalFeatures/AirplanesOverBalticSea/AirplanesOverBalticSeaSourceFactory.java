package simulator.data.adsb.additionalFeatures.AirplanesOverBalticSea;

import simulator.model.ISourceFactory;
import simulator.model.source.ISource;

public class AirplanesOverBalticSeaSourceFactory implements ISourceFactory {

	@Override
	public ISource getSource() {
		return new AirplanesOverBalticSeaSource();
	}
	
	public static AirplanesOverBalticSeaSourceFactory get() {
		return new AirplanesOverBalticSeaSourceFactory();
	}
	
}
