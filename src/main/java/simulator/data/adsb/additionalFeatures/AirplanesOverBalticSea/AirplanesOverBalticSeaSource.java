package simulator.data.adsb.additionalFeatures.AirplanesOverBalticSea;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import lombok.Getter;
import simulator.config.Constants;
import simulator.data.adsb.ADSBMessages;
import simulator.model.GeoCoordinate;
import simulator.model.source.AbstractSource;
import simulator.util.GeoOps;
import simulator.util.datagenerators.CallSignGenerator;
import simulator.util.datagenerators.HexIdentGenerator;

public class AirplanesOverBalticSeaSource extends AbstractSource {
	
	private static final Logger log = LoggerFactory.getLogger(AirplanesOverBalticSeaSource.class);
	
	private DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd,HH:mm:ss.SSS").withZone(ZoneId.of("UTC"));
	
	private double radius = 25_000.0;

	private double totalTime;
	private double totalDistance;
	
	private int publishTime = 3; // in [s]
	private int nrOfPoints;
	
	private double angle;
	private double stepAngle;

	@Getter private GeoCoordinate currentPosition;
	private GeoCoordinate startingPosition;
	private GeoCoordinate currentPositionBefore;
	
	private String[] hexIdent;
	private String[] callsign;
	
	private int maxx = 5;
	private int maxy = 3;
	
	private static final double factor = 100_000.0;
	
	public AirplanesOverBalticSeaSource() {
		super();
	}
	
	@Override
	public void run() {
		init();
		while (!isKill()) {
			
			angle += stepAngle;
			angle %= 360.0;

			currentPositionBefore = currentPosition;
			currentPosition = GeoOps.calcPointFromAngleAndDistance(startingPosition, radius, angle);

			double curr_lat = currentPosition.getLatitude();
			double curr_lon = currentPosition.getLongitude();
			
			String track = "0";
			if (currentPositionBefore != null) {
				track = String.valueOf(GeoOps.getBearing(currentPositionBefore.getLatitude(), currentPositionBefore.getLongitude(), currentPosition.getLatitude(), currentPosition.getLongitude()));
			}
			
			for (int x = 0; x < maxx; x++) {
				curr_lon++;
				curr_lat = currentPosition.getLatitude();
				for (int y = 0; y < maxy; y++) {
					curr_lat = curr_lat - 0.7;
					
					String message1 = ADSBMessages.MSG_1;
					message1 = message1.replace("${hexident}", hexIdent[x * maxy + y]);
					message1 = message1.replace("${callsign}", callsign[x * maxy + y]);
					message1 = message1.replace("${time}", dateTimeFormatter.format(new Date().toInstant()));
					
					String message3 = ADSBMessages.MSG_3;
					message3 = message3.replace("${hexident}", hexIdent[x * maxy + y]);
					message3 = message3.replace("${lat}", String.valueOf(((int)(curr_lat * factor)) / factor));
					message3 = message3.replace("${lon}", String.valueOf(((int)(curr_lon * factor)) / factor));
					message3 = message3.replace("${time}", dateTimeFormatter.format(new Date().toInstant()));
					
					String message4 = ADSBMessages.MSG_4;
					message4 = message4.replace("${hexident}", hexIdent[x * maxy + y]);
					message4 = message4.replace("${time}", dateTimeFormatter.format(new Date().toInstant()));
					message4 = message4.replace("${speed}", String.valueOf((int)(Constants.airplaneSpeed * Constants.fromMstoKn)));
					message4 = message4.replace("${track}", track);
					
					getTrack().publish(message1);
					getTrack().publish(message3);
					getTrack().publish(message4);
				}
			}
			
			try {
				Thread.sleep(publishTime * 1_000L);
			} catch (InterruptedException e) {
				log.debug("Exception at Thread {} ", e);
			}
			
		}
		log.info("Thread killed for " + hexIdent);
	}

	@Override
	public void init() {
		hexIdent = new String[maxx * maxy];
		for (int x = 0; x < maxx; x++) { // 5
			for (int y = 0; y < maxy; y++) { // 3
				hexIdent[x * maxy + y] = HexIdentGenerator.next();
			}
		}
		callsign = new String[maxx * maxy];
		for (int x = 0; x < maxx; x++) { // 5
			for (int y = 0; y < maxy; y++) { // 3
				callsign[x * maxy + y] = CallSignGenerator.next();
			}
		}
		
		startingPosition = new GeoCoordinate(55.5, 9.0);

		totalDistance = 2 * Math.PI * radius;
		totalTime = totalDistance / Constants.airplaneSpeed;
		nrOfPoints = (int) (totalTime / this.publishTime);
		
		stepAngle = 360.0 / nrOfPoints;
		log.info("StepAngle = " + stepAngle);
		
		log.info("In total {} airplanes ({}) created: #{} at ({},{}) dist={}m, time={}s, points={}", maxx * maxy, toString(), hexIdent.length, startingPosition.getLatitude(), startingPosition.getLongitude(), (int)totalDistance, (int)totalTime, nrOfPoints);
	}
	
	@Override
	public String toString() {
		if (hexIdent == null) return "";
		return hexIdent[0] + " ... " + hexIdent[hexIdent.length-1];
	}
	
	public boolean isRunning() {
		if (hexIdent == null) {
			return false;
		}
		return true;
	}
	
	@Override
	public boolean containsName(String id) {
		if (!isRunning()) return false;
		for (int i = 0; i < hexIdent.length; i++) {
			if (hexIdent[i] == null) return false;
			if (hexIdent[i].equalsIgnoreCase(id)) return true;
		}
		return false;
	}
}
