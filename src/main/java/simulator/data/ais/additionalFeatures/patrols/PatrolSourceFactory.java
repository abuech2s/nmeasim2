package simulator.data.ais.additionalFeatures.patrols;

import java.util.ArrayList;
import java.util.List;

import simulator.model.ISourcesFactory;
import simulator.model.source.ISource;

public class PatrolSourceFactory implements ISourcesFactory {

	@Override
	public List<ISource> getSources() {
		List<ISource> list = new ArrayList<>();
		list.add(new P1SourceA());
		list.add(new P1SourceB());
		list.add(new P2SourceA());
		list.add(new P2SourceB());
		list.add(new P3Source());
		list.add(new P4Source());
		list.add(new P5Source());
		return list;
	}
	
}
