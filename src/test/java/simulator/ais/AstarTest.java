package simulator.ais;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;

import simulator.data.ais.geo.Point;
import simulator.util.astar.AstarAlgorithm;
import simulator.util.astar.Node;

public class AstarTest {
	
	@Test
	public void AstarImplTest() {
	
		Point p1 = new Point("P1", "H1", 54.554, 5.334);
		p1.addNeighbour("P2");
		Point p2 = new Point("P2", null, 54.547, 5.596);
		p2.addNeighbour("P3");
		p2.addNeighbour("P4");
		Point p3 = new Point("P3", null, 54.733, 6.236);
		p3.addNeighbour("P5");
		Point p4 = new Point("P4", null, 54.153, 6.212);
		p4.addNeighbour("P5");
		Point p5 = new Point("P5", null, 54.549, 6.898);
		p5.addNeighbour("P6");
		Point p6 = new Point("P6", "H2", 54.544, 7.906);
		Point p7 = new Point("P7", "H3", 55.544, 7.906);

		Set<Point> set = new HashSet<>();
		set.add(p1);
		set.add(p2);
		set.add(p3);
		set.add(p4);
		set.add(p5);
		set.add(p6);
		set.add(p7);
		
		AstarAlgorithm astar = new AstarAlgorithm(set);
		List<Node> route = astar.calculateShortestRoute(p1, p6);
		
		// There is a route from P1 to P6
		assertEquals(5, route.size());
		assertEquals(route.get(0).getPointName(), "P6");
		assertEquals(route.get(1).getPointName(), "P5");
		assertEquals(route.get(2).getPointName(), "P3");
		assertEquals(route.get(3).getPointName(), "P2");
		assertEquals(route.get(4).getPointName(), "P1");
	
		// There is no route from P1 to P7
		route = astar.calculateShortestRoute(p1, p7);
		assertEquals(0, route.size());
	}
}
