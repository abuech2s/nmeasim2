package simulator.data.adsb;

public class ADSBMessages {
	public final static String MSG_1 = "MSG,1,123,321,${hexident},11267,${time},${time},${callsign},,,,,,,,,,,";
	public final static String MSG_3 = "MSG,3,123,321,${hexident},10057,${time},${time},,37000,,,${lat},${lon},,,0,0,0,0";
	public final static String MSG_4 = "MSG,4,496,469,${hexident},27854,${time},${time},,,${speed},${track},,,0,,,,,";
	
	protected static String createMsg1(String hexIdent, String callsign, String time) {
		StringBuilder sb = new StringBuilder();
		sb.append("MSG,1,123,321,");
		sb.append(hexIdent);
		sb.append(",11267,");
		sb.append(time);
		sb.append(",");
		sb.append(time);
		sb.append(",");
		sb.append(callsign);
		sb.append(",,,,,,,,,,,");
		return sb.toString();
	}
	
	protected static String createMsg3(String hexIdent, double lat, double lon, String time) {
		StringBuilder sb = new StringBuilder();
		sb.append("MSG,3,123,321,");
		sb.append(hexIdent);
		sb.append(",10057,");
		sb.append(time);
		sb.append(",");
		sb.append(time);
		sb.append(",,37000,,,");
		sb.append(lat);
		sb.append(",");
		sb.append(lon);
		sb.append(",,,0,0,0,0");
		return sb.toString();
	}
	
	protected static String createMsg4(String hexIdent, String time, double speed, double track) {
		StringBuilder sb = new StringBuilder();
		sb.append("MSG,4,496,469,");
		sb.append(hexIdent);
		sb.append(",27854,");
		sb.append(time);
		sb.append(",");
		sb.append(time);
		sb.append(",,,");
		sb.append(speed);
		sb.append(",");
		sb.append(track);
		sb.append(",,,0,,,,,");
		return sb.toString();
	}
}
