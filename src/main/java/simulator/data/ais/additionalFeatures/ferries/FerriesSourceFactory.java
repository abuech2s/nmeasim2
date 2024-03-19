package simulator.data.ais.additionalFeatures.ferries;

import java.util.ArrayList;
import java.util.List;

import simulator.model.ISourcesFactory;
import simulator.model.source.ISource;

public class FerriesSourceFactory implements ISourcesFactory {

	@Override
	public List<ISource> getSources() {
		List<ISource> list = new ArrayList<>();
		list.add(new Fe1Source());
		list.add(new Fe2Source());
		list.add(new Fe3Source());
		return list;
	}
	
}
