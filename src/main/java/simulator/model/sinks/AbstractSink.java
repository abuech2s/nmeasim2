package simulator.model.sinks;

import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

import lombok.Getter;

public abstract class AbstractSink implements ISink {
	
	public static boolean activateLogging = false;

	private Thread thread;
	
	protected boolean kill = false;
	@Getter protected String identifier = "";
	
	protected ConcurrentLinkedQueue<String> queue = null;
	protected boolean isReady = false;
	@Getter protected int port = 0;
	
	protected int queueSize;

	protected abstract void close();
	
	protected AbstractSink(String identifier, int port, int queueSize) {
		this.identifier = identifier.toLowerCase().trim();
		this.port = port;
		this.queueSize = 5 * (queueSize+1);
		queue = new ConcurrentLinkedQueue<>();
	}
	
	public void start() {
		if (!kill) {
			thread = new Thread(this, "Sink-"+getIdentifier());
			thread.setDaemon(false);
			thread.start();
		}
	}
	
	public void kill() {
		thread.interrupt();
		this.kill = true;
		
		//We use this trick here to kill the current thread while waiting for an accepting socket
		close();
	}
	
	@Override
	public void take(String message) {
		if (isReady) {
			queue.add(message);
		} else {
			queue.clear();
		}
	}
	
	@Override
	public void take(List<String> messages) {
		for (String msg : messages) {
			take(msg);
		}
	}
	
}
