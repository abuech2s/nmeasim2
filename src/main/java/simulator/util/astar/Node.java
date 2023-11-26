package simulator.util.astar;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
public class Node {

	@Setter @Getter private String pointName = "";
	@Setter @Getter private String harbourName = "";
	@Setter @Getter private boolean isACity = false;
	@Getter private double latitude = Double.NaN;
	@Getter private double longitude = Double.NaN;
	
	@Getter @Setter double fValue;
	@Getter @Setter double gValue;
	@Getter @Setter double hValue;
	
	@Getter @Setter Node predecessor;
	
	public Node (String pointName, String harbourName, boolean isACity, double latitude, double longitude) {
		this.pointName = pointName;
		this.harbourName = harbourName;
		this.isACity = isACity;
		this.latitude = latitude;
		this.longitude = longitude;
	}

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof Node)) return false;
		Node node = (Node)o;
		if (node.getPointName().equals(pointName)) return true;
		return false;
	}
	
}
