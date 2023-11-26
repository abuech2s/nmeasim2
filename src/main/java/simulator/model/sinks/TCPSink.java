package simulator.model.sinks;

import java.io.OutputStreamWriter;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import simulator.config.Constants;

public class TCPSink extends AbstractSink {
	
	private static final Logger log = LoggerFactory.getLogger(TCPSink.class);

	private ServerSocket serverSocket = null;
	private Socket socket = null;
	private OutputStreamWriter writer = null;
	
	public TCPSink(String identifier, int port, int queueSize) {
		super(identifier, port, queueSize);
	}

	@Override
	public void run() {
		close();
		while (!kill) {
			try {
				serverSocket = new ServerSocket(port);
				log.info("Opens listener for {} ({})", getIdentifier(), port);
				isReady = false;
				queue.clear();
				socket = serverSocket.accept();
				log.info("{} sink accepted.", getIdentifier());
				isReady = true;
				writer = new OutputStreamWriter(socket.getOutputStream());

				while (!kill) {
					if (!queue.isEmpty()) {
						String message = queue.poll();
						if (message == null) continue;
						if (activateLogging) log.info(message);
						writer.write(message + Constants.RCLF);
						writer.flush();
						log.debug("Send via TCP " + getIdentifier() + " : 1");
						if (queue.size() > queueSize) {
							queue.clear();
						}
					} else {
						Thread.sleep(500);
					}
				}

			} catch (BindException e) {
				// In case of a blocking port
				log.warn("Exception: ", e);
				try { Thread.sleep(5000); } catch (InterruptedException e1) {}
			} catch (InterruptedException e) {
				// Do nothing
			} catch (SocketException e) {
				// This here is an exit for killing the current thread at being at a blocking function
				log.debug("Exception: ", e);
			} catch (Exception e1) {
				log.warn("Exception: ", e1);
			}
			close();
		}
	}
	
	@Override
	protected void close() {
		try {
			if (null != socket) socket.close();
			if (null != serverSocket) serverSocket.close();
		} catch (Exception e1) {
			log.warn("Exception: ", e1);
		}
	}

	@Override
	public String getSinkType() {
		return Constants.TOKEN_TCP;
	}
	
	@Override
	public String getTarget() {
		return String.valueOf(this.port);
	}
}
