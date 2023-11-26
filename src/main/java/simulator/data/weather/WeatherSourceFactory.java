package simulator.data.weather;

import simulator.model.ISourceFactory;
import simulator.model.source.ISource;

public class WeatherSourceFactory implements ISourceFactory {

	@Override
	public ISource getSource() {
		return new WeatherSource();
	}
	
	public static WeatherSourceFactory get() {
		return new WeatherSourceFactory();
	} 

}
