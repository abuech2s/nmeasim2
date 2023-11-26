package simulator.data.ais;

import java.util.Calendar;
import java.util.GregorianCalendar;

import lombok.Getter;
import simulator.model.GeoCoordinate;
import simulator.util.GeoOps;

public class ETA {
	
	@Getter int month;
	@Getter int day;
	@Getter int hour;
	@Getter int minute;
	
	public ETA(GeoCoordinate currentPosition, GeoCoordinate targetPosition, double speed) {
		
		double approxDistance = GeoOps.getDistance(currentPosition.getLatitude(), currentPosition.getLongitude(), targetPosition.getLatitude(), targetPosition.getLongitude());
		int approxTimeMs = (int)(approxDistance / speed * 1000);
		long currentTimeMs = System.currentTimeMillis();
		
		long etaTimeMs = currentTimeMs + approxTimeMs;
		
		GregorianCalendar c = new GregorianCalendar();
		c.setTimeInMillis(etaTimeMs);
		
		this.month = c.get(Calendar.MONTH) + 1;
		this.day = c.get(Calendar.DAY_OF_MONTH);
		this.hour = c.get(Calendar.HOUR_OF_DAY);
		this.minute = c.get(Calendar.MINUTE);
	}
	
}
