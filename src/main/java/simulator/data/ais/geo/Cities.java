package simulator.data.ais.geo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import simulator.config.Constants;
import simulator.util.astar.AstarAlgorithm;
import simulator.util.astar.Node;

public class Cities {
	
	private static Map<String, Point> vertices = null;

	private static final Logger log = LoggerFactory.getLogger(Cities.class);
	
	private static AstarAlgorithm astar;

	private static InputStream getFileFromResources(String fileName) {
		ClassLoader classLoader = Cities.class.getClassLoader();
		InputStream stream = classLoader.getResourceAsStream(fileName);
		if (stream == null) {
			throw new IllegalArgumentException("file: " + fileName + " not found!");
		} else {
			return stream;
		}
	}

	private static void init() {
		vertices = new HashMap<>();

		try {
			InputStream stream = getFileFromResources(Constants.FILE_AISCITIES_COORDINATES);
			BufferedReader br = new BufferedReader(new InputStreamReader(stream));
			String line;
			while ((line = br.readLine()) != null) {
				String[] elem = line.split("->");
				String[] coord = elem[1].split(",");
				
				double lat = Double.parseDouble(coord[0]);
				double lon = Double.parseDouble(coord[1]);
				
				String harbourName = null;
				if (elem[0].contains("_")) {
					String[] names = elem[0].split("_");
					harbourName=names[1];
				}

				Point point = new Point(elem[0], harbourName, lat, lon);
				vertices.put(point.getGeoCoordinate().getPointName(), point);
			}
			br.close();
			
			stream = getFileFromResources(Constants.FILE_AISCITIES_CONNECTIONS);
			br = new BufferedReader(new InputStreamReader(stream));
			while ((line = br.readLine()) != null) {
				String[] elem = line.split("->");
				String[] neighbours = elem[1].split(",");
				
				for (String neighbour : neighbours) {
					vertices.get(elem[0]).addNeighbour(neighbour);
				}
			}
			br.close();
			
		} catch (IOException e) {
			log.warn("Exception while loading cities {}", e);
		}
		
		astar = new AstarAlgorithm(vertices.values());

		log.debug("Loaded {} vertices.", vertices.size());
	}

	public static Point getPoint(String pointName) {
		if (vertices == null)
			init();
		
		for (Point point : vertices.values()) {
			if (point.getGeoCoordinate().getPointName().equalsIgnoreCase(pointName)) {
				return point;
			}
		}
		
		return null;
	}
	
	public static Point getHarbour(String harbourName) {
		if (vertices == null)
			init();
		
		for (Point point : vertices.values()) {
			if (point.getGeoCoordinate().isACity() && point.getGeoCoordinate().getCityName().equalsIgnoreCase(harbourName)) {
				return point;
			}
		}
		
		return null;
	}

//	public synchronized static Point getRandomPoint() {
//		if (vertices == null)
//			init();
//		Random rand = new Random();
//		Point p = null;
//		do {
//			int randPos = rand.nextInt(vertices.size());
//			p = vertices.values().toArray(new Point[vertices.size()])[randPos];
//		} while (p.getGeoCoordinate().isACity());
//		
//		return p;
//	}
	
	public synchronized static Point getRandomHarbour() {
		if (vertices == null)
			init();
		Random rand = new Random();
		
		Point p = null;
		do {
			int randPos = rand.nextInt(vertices.size());
			p = vertices.values().toArray(new Point[vertices.size()])[randPos];
		} while (!p.getGeoCoordinate().isACity());
		
		return p;
	}
	
	public synchronized static List<Point> getRandomRoute(Point startHarbour, Point targetHarbour) {

		List<Node> route = astar.calculateShortestRoute(startHarbour, targetHarbour);
		
		List<Point> routePoints = new ArrayList<>();
		for (int i = route.size() - 1; i >= 0; i--) {
			routePoints.add(getPoint(route.get(i).getPointName()));
		}
		
		return routePoints;
	}
	
	public static int getSize() {
		if (vertices == null)
			init();
		return vertices.size();
	}
}
