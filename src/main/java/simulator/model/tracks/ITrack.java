package simulator.model.tracks;

import java.util.List;

import simulator.model.source.ISource;

public interface ITrack {
	public void start();
	public void kill();
	public void publish(String message);
	public void publish(List<String> messages);
	public String getToken();
	public List<ISource> getSources();
}
