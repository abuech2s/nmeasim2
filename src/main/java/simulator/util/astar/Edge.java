package simulator.util.astar;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
public class Edge {

	@Getter @Setter private Node node1; 
	@Getter @Setter private Node node2;
	
	@Override
	public boolean equals(Object o) {
		if (!(o instanceof Edge)) return false;
		Edge edge = (Edge)o;
		if (node1.getPointName().equals(edge.getNode1().getPointName()) && node2.getPointName().equals(edge.getNode2().getPointName())) return true;
		if (node1.getPointName().equals(edge.getNode2().getPointName()) && node2.getPointName().equals(edge.getNode1().getPointName())) return true;
		return false;
	}
	
}
