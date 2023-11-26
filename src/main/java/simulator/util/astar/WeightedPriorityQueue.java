package simulator.util.astar;

import java.util.Comparator;
import java.util.PriorityQueue;

public class WeightedPriorityQueue {
	
	private class DistanceComparator implements Comparator<Node> {

		@Override
		public int compare(Node o1, Node o2) {
			if (o1.getFValue() < o2.getFValue()) {
				return -1;
			}
			if (o1.getFValue() > o2.getFValue()) {
				return 1;
			}
			return 0;
		}
		
	}

	private PriorityQueue<Node> queue;
	
	public WeightedPriorityQueue() {
		Comparator<Node> comparator = new DistanceComparator();
		queue = new PriorityQueue<Node>(200, comparator);
	}
	
	public void enqueue(Node node) {
		queue.add(node);
	}
	
	public boolean isEmpty() {
		return queue.isEmpty();
	}
	
	public Node removeMin() {
		return queue.poll();
	}
	
	public int size() {
		return queue.size();
	}
	
	public boolean contains(Node node) {
		return queue.contains(node);
	}
	
	public void remove(Node node) {
		queue.remove(node);
	}
	
	public void clear() {
		queue.clear();
	}
	
	@Override
	public String toString() {
		String s = "";
		for (Node node : queue) {
			s += node.getPointName() + " ";
		}
		return s;
	}
	
}
