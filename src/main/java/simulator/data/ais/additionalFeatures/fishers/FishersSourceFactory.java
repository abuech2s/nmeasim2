package simulator.data.ais.additionalFeatures.fishers;

import java.util.ArrayList;
import java.util.List;

import simulator.model.ISourcesFactory;
import simulator.model.source.ISource;

public class FishersSourceFactory implements ISourcesFactory {

	@Override
	public List<ISource> getSources() {
		List<ISource> list = new ArrayList<>();
		list.add(new F1Source());
		list.add(new F2Source());
		list.add(new F3Source());
		list.add(new F4Source());
		list.add(new F5Source());
		list.add(new F6Source());
		list.add(new F7Source());
		list.add(new F8Source());
		list.add(new F9Source());
		list.add(new F10Source());
		return list;
	}
	
}
