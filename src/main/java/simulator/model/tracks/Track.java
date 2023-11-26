package simulator.model.tracks;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import lombok.Getter;
import lombok.Setter;
import simulator.model.ISourceFactory;
import simulator.model.ISourcesFactory;
import simulator.model.sinks.ISink;
import simulator.model.source.ISource;

public class Track implements ITrack {
	
	private static final Logger log = LoggerFactory.getLogger(Track.class);
	
	@Getter @Setter private String token;
	
	@Getter private List<ISource> sources;
	
	protected ISink sink; 
	
	private ISourceFactory sourceFactory;
	
	public Track(String token, ISourceFactory sourceFactory, ISink sink) {
		this.token = token;
		this.sink = sink;
		this.sourceFactory = sourceFactory;
		this.sources = new ArrayList<>();
	}
	
	public void initSources(int nrSources) {
		for (int i = 0; i < nrSources; i++) {
			this.sources.add(this.sourceFactory.getSource().setTrack(this));
		}
	}
	
	public ITrack addAdditionalFeatureSource(ISourceFactory sourceFactory) {
		this.sources.add(sourceFactory.getSource().setTrack(this));
		return this;
	}
	
	public ITrack addAdditionalFeatureSources(ISourcesFactory sourceFactory) {
		List<ISource> sources = sourceFactory.getSources();
		for (ISource source : sources) {
			this.sources.add(source.setTrack(this));
		}
		return this;
	}
	
	public void publish(String message) {
		if (null != sink) {
			sink.take(message);
		}
	}
	
	public void publish(List<String> messages) {
		if (null != sink) sink.take(messages);
	}
	
	public void kill() {
		this.sink.kill();
	}
	
	@Override
	public void start() {
		log.info("Start {} with {} sources", getToken(), sources.size());
		this.sink.start();
		for (ISource source : sources) {
			source.start();
		}
	}
	
	@Override
	public String toString() {
		return "[" + token + " : " + sources.size() + "]";
	}

}
