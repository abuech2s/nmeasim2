package simulator.data.gps;

public class GPSMessages {
	public static String MSG_GPGGA = "GPGGA,${time},${lat},${latNS},${lon},${lonWE},1,03,10.0,0.0,M,0.0,M,,";
	public static String MSG_GPRMC = "GPRMC,${time},A,${lat},${latNS},${lon},${lonWE},${speed},,${date},011.3,E";
	public static String MSG_GPHDT = "GPHDT,${course},T";
}
