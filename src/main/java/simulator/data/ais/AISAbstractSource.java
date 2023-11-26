package simulator.data.ais;

import lombok.Getter;
import simulator.model.GeoCoordinate;
import simulator.model.source.AbstractSource;

public abstract class AISAbstractSource extends AbstractSource {

	@Getter protected GeoCoordinate currentPosition;
	@Getter protected String mmsi;
	
	protected double speed; // in [m/s]
	protected int messagePublishTimeInterval; // in [s]
	
	protected int sentPoints;
	
	protected int navStatus = 0;
	protected int posFixType = 0;
	
	protected IVessel vessel;
	protected ETA eta; // we use an approx. distance recalculated from currentPoint to targetPoint
	
	
	protected double factorLat = 1.0;
	protected double factorLon = 1.0;
	protected double stepLat;
	protected double stepLon;
	
	
	protected double totalTime;
	protected double totalDistance;
	protected int nrOfPoints;
	
	protected int currentWayPointIndex;
	
}
