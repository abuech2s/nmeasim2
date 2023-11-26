package simulator.util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import simulator.util.astar.Node;
import simulator.util.astar.WeightedPriorityQueue;

public class WeightedPriorityQueueTest {

	
	@Test
	public void WeightedPriorityQueueImplTest() {
		WeightedPriorityQueue queue = new WeightedPriorityQueue();
		Node n1 = new Node("P1", null, false, 1.0, 1.0);
		n1.setFValue(100);
		queue.enqueue(n1);
		
		Node n2 = new Node("P2", null, false, 1.0, 1.0);
		n2.setFValue(50);
		queue.enqueue(n2);
		
		Node n3 = new Node("P3", null, false, 1.0, 1.0);
		n3.setFValue(200);
		queue.enqueue(n3);
		
		Node n4 = new Node("P4", null, false, 1.0, 1.0);
		n4.setFValue(133);
		queue.enqueue(n4);
		
		Node n5 = new Node ("P5", null, false, 1.0, 1.0);
		n5.setFValue(25);
		queue.enqueue(n5);
		
		Node p5 = queue.removeMin();	
		assertEquals(p5.getPointName(), "P5");
		assertEquals(4, queue.size());
		
		Node p2 = queue.removeMin();	
		assertEquals(p2.getPointName(), "P2");
		assertEquals(3, queue.size());
		
		Node p1 = queue.removeMin();	
		assertEquals(p1.getPointName(), "P1");
		assertEquals(2, queue.size());
		
		Node p4 = queue.removeMin();	
		assertEquals(p4.getPointName(), "P4");
		assertEquals(1, queue.size());
		
		Node p3 = queue.removeMin();	
		assertEquals(p3.getPointName(), "P3");
		assertEquals(0, queue.size());
		
	}
	
}
