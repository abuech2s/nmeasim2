package simulator.util.astar;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import simulator.data.ais.geo.Point;
import simulator.util.GeoOps;

/**
 * This algorithm is based on the pseudo code from https://de.wikipedia.org/wiki/A*-Algorithmus
 * 
 * @author Abuech2s	
 *
 */

public class AstarAlgorithm {

	private List<Node> closedList;
	private WeightedPriorityQueue openlist;
	
	private Node startNode;
	private Node targetNode;
	
	private List<Node> nodes;
	private List<Edge> edges;
	
	public AstarAlgorithm(Collection<Point> vertices) {
		nodes = new ArrayList<>();
		edges = new ArrayList<>();
		closedList = new ArrayList<>();
		openlist = new WeightedPriorityQueue();
		setNewData(vertices);
	}
	
	private Node getNode(String name) {
		for (Node node : nodes) {
			if (node.getPointName().equals(name)) return node;
		}
		return null;
	}
	
	private void setNewData(Collection<Point> vertices) {
		nodes.clear();
		edges.clear();
		
		for (Point point : vertices) {
			Node node = new Node(point.getGeoCoordinate().getPointName(), point.getGeoCoordinate().getCityName(), point.getGeoCoordinate().isACity(), point.getGeoCoordinate().getLatitude(), point.getGeoCoordinate().getLongitude());
			nodes.add(node);
		}
		
		for (Point point : vertices) {
			for (String neighbour : point.getNeighbours()) {
				Node node1 = getNode(neighbour);
				Node node2 = getNode(point.getGeoCoordinate().getPointName());
				Edge edge = new Edge(node1, node2);
				if (!edges.contains(edge)) edges.add(edge);
			}
		}
	}
	
	/**
	 * Calculate new heuristic values from a node x to the target node
	 */
	private void calculateNewHValues() {
		for (Node node : nodes) {
			double h = GeoOps.getDistance(node.getLatitude(), node.getLongitude(), targetNode.getLatitude(), targetNode.getLongitude());
			node.setHValue(h);
		}
	}
	
	public List<Node> calculateShortestRoute(Point startPoint, Point targetPoint) {

		// Reset and new init
		startNode = new Node(startPoint.getGeoCoordinate().getPointName(), startPoint.getGeoCoordinate().getCityName(), startPoint.getGeoCoordinate().isACity(), startPoint.getGeoCoordinate().getLatitude(), startPoint.getGeoCoordinate().getLongitude());
		targetNode = new Node(targetPoint.getGeoCoordinate().getPointName(), targetPoint.getGeoCoordinate().getCityName(), targetPoint.getGeoCoordinate().isACity(), targetPoint.getGeoCoordinate().getLatitude(), targetPoint.getGeoCoordinate().getLongitude());
		
		calculateNewHValues();
		openlist.clear();
		closedList.clear();
		
		// Start calculation
		openlist.enqueue(startNode);
		do {
			Node currentNode = openlist.removeMin();
			
			if (currentNode.equals(targetNode)) {
				List<Node> route = new ArrayList<>();
				route.add(currentNode);
				do {
					currentNode = currentNode.getPredecessor();
					route.add(currentNode);
				} while (currentNode.getPredecessor() != null);
				
				return route;
			}
			closedList.add(currentNode);
			
			expandNode(currentNode);

		} while (!openlist.isEmpty());
		
		return new ArrayList<>(); // No path found - should never happens for this simulator
	}
	
	private void expandNode(Node currentNode) {
		List<Node> successors = getAllSuccessors(currentNode);
		for (Node successor : successors) {
			if (closedList.contains(successor)) continue;
			
			double tentative_g = currentNode.getFValue() * GeoOps.getDistance(currentNode.getLatitude(), currentNode.getLongitude(), successor.getLatitude(), successor.getLongitude());
				
			if (openlist.contains(successor) && tentative_g > successor.getGValue()) continue;
			
			successor.setPredecessor(currentNode);
			successor.setGValue(tentative_g);
			
			double f = tentative_g + successor.getHValue();
			
			successor.setFValue(f);
			if (openlist.contains(successor)) {
				openlist.remove(successor);
			}
			openlist.enqueue(successor);
		}
	}
	
	private List<Node> getAllSuccessors(Node currentNode) {
		List<Node> successors = new ArrayList<>();
		
		for (Edge edge : edges) {
			if (edge.getNode1().getPointName().equals(currentNode.getPointName())) {
				successors.add(edge.getNode2());
			} else if (edge.getNode2().getPointName().equals(currentNode.getPointName())) {
				successors.add(edge.getNode1());
			}
		}
		return successors;
	}
	
}
