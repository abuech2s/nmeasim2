package simulator.model.sinks;

import java.net.BindException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import simulator.config.Constants;

public class UDPSink extends AbstractSink {
	
	private static final Logger log = LoggerFactory.getLogger(UDPSink.class);

	private DatagramSocket datagramSocket = null;
	private InetAddress address = null;
	
	private String targetIp;
	private int queueSize;

	public UDPSink(String identifier, String targetIp, int port, int queueSize) {
		super(identifier, port, queueSize);
		this.targetIp = targetIp;
	}

	@Override
	public void run() {
		close();
		while (!kill) {
			try {
				datagramSocket = new DatagramSocket();
				address = InetAddress.getByName(targetIp);
				log.info("Create udp sink for {} ({}:{})", getIdentifier(), targetIp, port);
				isReady = true;

				while (!kill) {
					if (!queue.isEmpty()) {
						String message = queue.poll();
						if (message == null) continue;
						message += Constants.RCLF;
					
						byte[] bytes = message.getBytes();
						DatagramPacket packet = new DatagramPacket(bytes, bytes.length, address, port);
						if (activateLogging) log.info(message);
						datagramSocket.send(packet);
						log.debug("Send via UDP " + getIdentifier() + " : 1");
						if (queue.size() > queueSize) queue.clear();
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
				//This here is an exit for killing the current thread at being at a blocking function
				log.debug("Exception: ", e);
			} catch (Exception e) {
				log.warn("Exception: ", e);
			}
			close();
		}
	}
	
	@Override
	protected void close() {
		try {
			if (null != datagramSocket) datagramSocket.close();
		} catch (Exception e1) {
			log.warn("Exception: {}", e1);
		}
	}
	
	@Override
	public String getSinkType() {
		return Constants.TOKEN_UDP;
	}
	
	@Override
	public String getTarget() {
		return this.targetIp + ":" + String.valueOf(this.port);
	}
}
