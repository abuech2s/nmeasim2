package simulator.data.weather;

import java.util.concurrent.ThreadLocalRandom;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import lombok.Getter;
import simulator.config.Constants;
import simulator.model.GeoCoordinate;
import simulator.model.source.AbstractSource;
import simulator.model.tracks.TrackAdministration;
import simulator.util.GeoOps;

public class WeatherSource extends AbstractSource {

	private static final Logger log = LoggerFactory.getLogger(WeatherSource.class);
	
	@Getter private GeoCoordinate currentPosition; 
	
	private double weatherTemp = 0.1;
	private double weatherWindDir = 0.1;
	private double weatherWindSpeed = Constants.weatherWindSpeedMin;
	
	private int messagePublishTimeInterval; // in [s]
	
	@Override
	public void run() {
		init();
		while (!kill) {
			currentPosition = TrackAdministration.getTrack(Constants.TOKEN_GPS).getSources().get(0).getCurrentPosition();
			
			String msgWimda = buildMessage();
			
			getTrack().publish(msgWimda);
			
			try {
				Thread.sleep(messagePublishTimeInterval * 1_000);
			} catch (InterruptedException e) {
				log.debug("Exception at Thread {} ", e);
			}
			
		}
	}
	
	private String buildMessage() {
		String msgWimda = WeatherMessages.MSG_WEATHER;
		
		double barpressure = 1.000;
		msgWimda = msgWimda.replace("${barpressure1}", String.format("%.3f", barpressure).replaceAll(",", "."));
		msgWimda = msgWimda.replace("${barpressure2}", String.format("%.3f", barpressure * Constants.fromMmHgtoBar).replaceAll(",", "."));
		
		msgWimda = msgWimda.replace("${airtemp}", String.format("%.1f", 15.0 + weatherTemp).replaceAll(",", "."));
		msgWimda = msgWimda.replace("${relhum}", "55.5");
		msgWimda = msgWimda.replace("${abshum}", "60.3");
		msgWimda = msgWimda.replace("${dewpoint}", "23");
		
		msgWimda = msgWimda.replace("${winddir1}", String.format("%.1f", weatherWindDir).replaceAll(",", "."));
		msgWimda = msgWimda.replace("${winddir2}", String.format("%.1f", weatherWindDir + 5.0).replaceAll(",", "."));
		
		weatherTemp += 0.1;
		if (weatherTemp >= Constants.weatherTempMax) weatherTemp = Constants.weatherTempMin;
		
		weatherWindDir += 0.1;
		if (weatherWindDir >= Constants.weatherWindDirMax) weatherWindDir = Constants.weatherWindDirMin;
		
		weatherWindSpeed += 0.3;
		if (weatherWindSpeed >= Constants.weatherWindSpeedMax) weatherWindSpeed = Constants.weatherWindSpeedMin;
		
		msgWimda = msgWimda.replace("${windspeed1}", String.format("%.1f", weatherWindSpeed).replaceAll(",", "."));
		msgWimda = msgWimda.replace("${windspeed2}", String.format("%.1f", weatherWindSpeed * Constants.fromKntoMs).replaceAll(",", "."));
		
		msgWimda = "$" + msgWimda + "*" + GeoOps.calcCheckSum(msgWimda);
		
		return msgWimda;
	}
	
	@Override
	public void init() {
		messagePublishTimeInterval = ThreadLocalRandom.current().nextInt(Constants.courseMessagePublishTimeIntervalMin, Constants.courseMessagePublishTimeIntervalMax);
		log.info("course device created.");
	}
	
	@Override
	public boolean containsName(String id) {
		return true;
	}
	
}
