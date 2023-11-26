package simulator.data.radar;

import java.text.DecimalFormat;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import lombok.Getter;
import simulator.config.Constants;
import simulator.data.ais.AISAbstractSource;
import simulator.model.GeoCoordinate;
import simulator.model.source.AbstractSource;
import simulator.model.source.ISource;
import simulator.model.tracks.RadarContact;
import simulator.model.tracks.TrackAdministration;
import simulator.util.GeoOps;
import simulator.util.datagenerators.RadarTrackIdGenerator;

public class RadarSource extends AbstractSource {
	
	private static final Logger log = LoggerFactory.getLogger(RadarSource.class);
	
	@Getter private GeoCoordinate currentPosition; 
	
	private int messagePublishTimeInterval; // in [s]
	
	private Map<String, RadarContact> contacts = new HashMap<>();
	
	private DateTimeFormatter dateTimeFormatterRadar = DateTimeFormatter.ofPattern("HHmmss.SS").withZone(ZoneId.of("UTC"));
	private DecimalFormat df2 = new DecimalFormat("00.0");
	private DecimalFormat df3 = new DecimalFormat("000.0");
	
	@Override
	public void run() {
		init();
		while (!kill) {
			
			List<ISource> aisContacts = TrackAdministration.getTrack(Constants.TOKEN_AIS).getSources();
			currentPosition = TrackAdministration.getTrack(Constants.TOKEN_GPS).getSources().get(0).getCurrentPosition();
			
			for (ISource source : aisContacts) {
				String mmsi = ((AISAbstractSource)source).getMmsi();
				detectExistingRadarContact(mmsi, source.getCurrentPosition());
			}
			
			cleanContacts();
			
			try {
				Thread.sleep(messagePublishTimeInterval * 1_000);
			} catch (InterruptedException e) {
				log.debug("Exception at Thread {} ", e);
			}
			
		}
	}
	
	private void detectExistingRadarContact(String token, GeoCoordinate position) {
		if (position == null || currentPosition == null) return;
		double dist = GeoOps.getDistance(position.getLatitude(), position.getLongitude(), currentPosition.getLatitude(), currentPosition.getLongitude());
		if (dist <= Constants.MAX_RADAR_RADIUS) {
			int radarTrackId;
			if (contacts.get(token) == null) {
				radarTrackId = RadarTrackIdGenerator.next();
				contacts.put(token, new RadarContact(radarTrackId));
			} else {
				radarTrackId = contacts.get(token).getRadarTrackId();
				contacts.put(token, new RadarContact(radarTrackId));
			}
			
			double bearing = GeoOps.getBearing(currentPosition.getLatitude(), currentPosition.getLongitude(), position.getLatitude(), position.getLongitude());
			String msgRattm = buildMessage(radarTrackId, dist, bearing);
			getTrack().publish(msgRattm);
		}
	}
	
	private String buildMessage(int radarTrackId, double distance, double bearing) {
		String msgRattm = RadarMessage.MSG_TTM;
		
		distance = distance * Constants.fromMtoNM; // [m] in [NM]
		
		msgRattm = msgRattm.replace("${trackid}", String.valueOf(radarTrackId));
		msgRattm = msgRattm.replace("${dist}", df2.format(distance).replace(",", "."));
		msgRattm = msgRattm.replace("${bearing}", df3.format(bearing).replace(",", "."));
		msgRattm = msgRattm.replace("${time}", dateTimeFormatterRadar.format(new Date().toInstant()));
		msgRattm = msgRattm.replace("${name}", "TRK" + radarTrackId);
		msgRattm = msgRattm.replace("${course}", "0.0");
		msgRattm = msgRattm.replace("${speed}", "0.0");
		//TODO
		//msgRattm = msgRattm.replace("${course}", df3.format(course).replace(",", "."));
		//msgRattm = msgRattm.replace("${speed}", String.format("%.3f", speed * Constants.fromMstoKn).replaceAll(",", "."));
		
		msgRattm = "$" + msgRattm + "*" + GeoOps.calcCheckSum(msgRattm);
		return msgRattm;
	}
	
	private void cleanContacts() {
		long currentTime = System.currentTimeMillis();
		Iterator<Map.Entry<String, RadarContact>> iter = contacts.entrySet().iterator();
		while (iter.hasNext()) {
		    Map.Entry<String, RadarContact> entry = iter.next();
		    if (entry.getValue().getLastDetection() < currentTime - 60_000) {
		    	log.info("Removed radar contact for: " + entry.getKey());
		        iter.remove();
		    }
		}
	}
	
	@Override
	public void init() {
		messagePublishTimeInterval = ThreadLocalRandom.current().nextInt(Constants.radarMessagePublishTimeIntervalMin, Constants.radarMessagePublishTimeIntervalMax);
		log.info("radar device created.");
	}

}
