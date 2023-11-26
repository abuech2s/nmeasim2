package simulator.model.source;

import java.util.Objects;

import lombok.Getter;
import simulator.model.tracks.ITrack;

public abstract class AbstractSource implements ISource {
	
	@Getter protected ITrack track;
	@Getter protected boolean kill = false;
	protected Thread thread = null;
	
	public AbstractSource() {
		thread = new Thread(this);
	}
	
	@Override
	public void start() {
		thread.start();
	}
	

	@Override
	public void stop() {
		thread.interrupt();
	}
	
	@Override
	public ISource setTrack(ITrack track) {
		this.track = track;
		return this;
	}
	
	@Override
	public boolean equals(Object o) {
		if (!(o instanceof AbstractSource)) return false;
		AbstractSource as = (AbstractSource)o;
		
		if (as.thread.getId() == thread.getId()) return true;
		
		return false;
	}
	
	@Override
	public int hashCode() {
	    return Objects.hash(this.thread.getId());
	}
	
}
