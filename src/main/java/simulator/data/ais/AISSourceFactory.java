package simulator.data.ais;

import simulator.model.ISourceFactory;
import simulator.model.source.ISource;

public class AISSourceFactory implements ISourceFactory {

	@Override
	public ISource getSource() {
		return new AISSource();
	}
	
	public static AISSourceFactory get() {
		return new AISSourceFactory();
	}

}
