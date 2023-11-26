package simulator.model;

import java.util.List;

import simulator.model.source.ISource;

public interface ISourcesFactory {
	public List<ISource> getSources();
}
