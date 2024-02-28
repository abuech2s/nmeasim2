package simulator.config;

public class Constants {

	//Identifiers
	public static final String TOKEN_ADSB = "adsb";
	public static final String TOKEN_AIS = "ais";
	public static final String TOKEN_GPS = "gps";
	public static final String TOKEN_RADAR = "radar";
	public static final String TOKEN_WEATHER = "weather";
	public static final String TOKEN_COURSE = "course";
	
	//Technical identifiers
	public static final String TOKEN_TCP = "tcp";
	public static final String TOKEN_UDP = "udp";
	
	//Config parameters
	public static final String CONFIG_FILENAME = "config.xml";
	
	//Track parameters
	public static long DEFAULT_TRACK_SLEEP_TIME = 600 * 1_000L; // in [s]
	public static double MAX_RADAR_RADIUS = 30_000.0; // in [m]
	
	//Physical constants
	public static final double fromMtoNM = 0.000539957; // from [m] to [NM]
	public static final double fromKntoMs = 0.514444; // from [kn] to [m/s]
	public static final double fromMstoKn = 1.94384; // from [m/s] to [kn]
	public static final double fromMmHgtoBar = 0.0013332; // from [mmHg] to [Bar]
	
	//Filenames
	public static final String FILE_ADSBCITIES = "adsb/cities.csv";
	public static final String FILE_AISCITIES_COORDINATES = "ais/coordinates.txt";
	public static final String FILE_AISCITIES_CONNECTIONS = "ais/connections.txt";
	public static final String FILE_VERSION = "version.txt";
	
	//Linebreak
	public static final String RCLF = "\r\n";
	
	//AIS parameters
	public static final int vesselDraughtMin = 10;
	public static final int vesselDraughtMax = 35;
	public static final int vesselBowMin = 5;
	public static final int vesselBowMax = 9;
	public static final int vesselSternMin = 5;
	public static final int vesselSternMax = 9;
	public static final int vesselPortMin = 10;
	public static final int vesselPortMax = 15;
	public static final int vesselStarboardMin = 8;
	public static final int vesselStarboardMax = 13;
	public static final int vesselShiptypeMin = 1;
	public static final int vesselShiptypeMax = 70;
	public static final int vesselSpeedMin = 15;
	public static final int vesselSpeedMax = 35;
	public static final int vesselMessagePublishTimeIntervalMin = 8;
	public static final int vesselMessagePublishTimeIntervalMax = 15;
	public static final int vesselImoMin = 1_000_000;
	public static final int vesselImoMax = 9_999_999;
	public static final long vesselMmsiMin = 100_000_000L;
	public static final long vesselMmsiMax = 999_999_999L;
	
	//ADSB parameters
	public static final double airplaneSpeed = 250.0; // in [m/s]
	public static final int airplaneHexIdentMin = 0;
	public static final int airplaneHexIdentMax = 50;
	
	//GPS parameter
	public static final double gpsSpeed = 30.0; // in[m/s]
	public static final int gpsMessagePublishTimeIntervalMin = 8;
	public static final int gpsMessagePublishTimeIntervalMax = 15;
	public static final int gpsSleepTime = 1_000;

	//Radar parameter
	public static final int radarMessagePublishTimeIntervalMin = 15;
	public static final int radarMessagePublishTimeIntervalMax = 30;
	public static final int radarTrackIdMin = 0;
	public static final int radarTrackIdMax = 99;
	
	//Course parameter
	public static final int courseMessagePublishTimeIntervalMin = 10;
	public static final int courseMessagePublishTimeIntervalMax = 15;
	
	//Weather parameter
	public static final int weatherMessagePublishTimeIntervalMin = 10;
	public static final int weatherMessagePublishTimeIntervalMax = 15;
	public static final double weatherTempMin = -5.0;
	public static final double weatherTempMax = 10.0;
	public static final double weatherWindDirMin = 0.0;
	public static final double weatherWindDirMax = 360.0;
	public static final double weatherWindSpeedMin = 10.0;
	public static final double weatherWindSpeedMax = 55.0;
	
}
