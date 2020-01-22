package i_nav;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import i_nav_model.LocationObject;

/**
 * 
 * @author CSCD490 Team5
 * @version 1.0
 * 
 *
 */
public class Search {
	
	private HashMap<LocationObjectVertex, Integer> dist;
	private HashMap<LocationObjectVertex, ArrayList<Edge>> path;
	private HashMap<LocationObjectVertex, LocationObjectVertex> prev;
	private HashMap<LocationObjectVertex, Boolean> mark;
	private CloudGraphListUndirected G;
	LocationObjectVertex start;
	LocationObjectVertex end;
	private int c = 0;
	
	public Search(CloudGraphListUndirected G) {
		this.G = G;
	}
	
	public HashMap<LocationObjectVertex, ArrayList<Edge>> getPath() {
		return path;
	}
	
	public ArrayList<Edge> getPathToVertex() {
		
		return path.get(end);
	}
	
	public Map<LocationObjectVertex, Integer> getDist() {
		return dist;
	}
	
	
	
	public void printInfo() {
		System.out.println("DIST: ");
		for (LocationObjectVertex v : dist.keySet()) {
			System.out.println("  " + v.getObject_id() + ": " + dist.get(v) + ", ");
		}
		System.out.println();
		System.out.println("PATH: ");
		for (LocationObjectVertex v : path.keySet()) {
			System.out.println("  " + v.getObject_id());
			for (Edge e : path.get(v)) {
				System.out.println("    " + e.getJson() + ", ");
			}
		}
		System.out.println();
		System.out.println("PREV: ");
		for (LocationObjectVertex v : prev.keySet()) {
			System.out.println("  " + v.getObject_id() + ": " + prev.get(v).getObject_id() + ", ");
			
		}
		System.out.println();
		System.out.println("MARK: ");
		for (LocationObjectVertex v : mark.keySet()) {
			System.out.println("  " + v.getObject_id() + ": " + mark.get(v) + ", ");
			
		}
	}
	
	public void dijkstra(LocationObjectVertex start, LocationObjectVertex end) {
		System.out.println("Dijkstra start");
		this.start = start;
		this.end = end;
		dist = new HashMap<LocationObjectVertex, Integer>();
		path = new HashMap<LocationObjectVertex, ArrayList<Edge>>();
		prev = new HashMap<LocationObjectVertex, LocationObjectVertex>();
		mark = new HashMap<LocationObjectVertex, Boolean>();
		for (String s : G.getAdj().keySet()) {
			LocationObjectVertex v = G.getVertex(s);
			dist.put(v, 10000);
		}
		path.put(start, new ArrayList<Edge>());
		dist.put(start, 0);
		dijkstra(start);
		printInfo();
	}
	
	private void dijkstra(LocationObjectVertex current) {
		
		if (mark.containsKey(current)) {
			return;
		}
		mark.put(current, true);
		
		List<LocationObjectVertex> neighbors = G.neighbors(current);
		
		for (LocationObjectVertex n : neighbors) {
			System.out.println("NEIGHBOR of " + current.getObject_id() + ": " + n.getObject_id());
		}
		
		if (G.getAdj().containsKey("" + current.getObject_id())) {
			
			List<Edge> edges = G.getAdj().get("" + current.getObject_id());
			System.out.println("NUM EDGES: " + edges.size());
			for (Edge e : edges) {
				System.out.println("Comparing " + e.v1().getObject_id() + " and " + e.v2().getObject_id() + " current: " + current.getObject_id() + " cur dist: " + dist.get(current));
				if (true) { // (!path.containsKey(e.v2())) {
					
					int prevDist = 0;
					if (prev.containsKey(current)) {
						prevDist = dist.get(prev.get(current)) + dist.get(current);
					}
	
					if ((prevDist + e.weight()) < dist.get(e.v2())) {
	
						dist.put(e.v2(), prevDist + e.weight());
	
						prev.put(e.v2(), current);
						
						
						path.put(e.v2(), (ArrayList<Edge>) path.get(prev.get(e.v2())).clone());
						path.get(e.v2()).add(e);
						
						
						
					}
					
				}
			}
			for (Edge e : edges) {
				dijkstra(e.v2());
			}
		}
		
	}
	
	public static List<Edge> getDirections(List<Edge> edges, Map<String, LocationObject> allObjects) {
		
		
		for (int i = 0; i < edges.size(); i++) { // (Edge e : edges) {
			Edge e = edges.get(i);
			
			double deltaY = (e.v2().getY() - e.v1().getY());
            double deltaX = (e.v2().getX() - e.v1().getX());
            double dist = Math.sqrt(deltaX * deltaX + deltaY * deltaY);
            
            if (i > 0) {
            	
            	Edge last = edges.get(i - 1);
            	
            	
            }

            String from = "";
            if (allObjects.containsKey("" + e.v1().getObject_id())) {
                from = allObjects.get("" + e.v1().getObject_id()).getShort_name() + "#" + e.v1().getObject_id();
            } else {
                from = "" + e.v1().getObject_id();
            }
            String to = "";
            if (allObjects.containsKey("" + e.v2().getObject_id())) {
                to = allObjects.get("" + e.v2().getObject_id()).getShort_name() + "#" + e.v2().getObject_id();
            } else {
                to = "" + e.v2().getObject_id();
            }
            double angle = Math.atan(deltaY / deltaX);
            angle = angle * (180 / Math.PI);
            angle = Math.round(angle);

            String direction = "";

            if (deltaX > 0 && deltaY > 0) { // quadrant 1
                direction = "NE";
                angle = Math.abs(angle);
            } else if (deltaX < 0 && deltaY > 0) { // quadrant 2
                direction = "NW";
                angle = Math.abs(angle) + 270;
            } else if (deltaX < 0 && deltaY < 0) { // quadrant 3
                direction = "SW";
                angle = Math.abs(angle) + 180;
            } else if (deltaX > 0 && deltaY < 0) { // quadrant 4
                direction = "SE";
                angle = Math.abs(angle) + 90;
            } else if (deltaX == 0 && deltaY > 0) { // N
                direction = "N";
            } else if (deltaX == 0 && deltaY < 0) { // S
                direction = "S";
            } else if (deltaX > 0 && deltaY == 0) { // E
                direction = "E";
            } else if (deltaX < 0 && deltaY == 0) { // W
                direction = "W";
            }


            System.out.println("###" + "v1: " + e.v1().getX() + ", " + e.v1().getY() + "    v2: " + e.v2().getX() + ", " + e.v2().getY());

            String str = "Walk... " + angle + " deg (" + direction + ") " +  Math.round(dist) + " ft. ";

            if (i == edges.size() - 1) {
                str += "from " + from + ". Arrive at your destination, " + to + "";
            } else {
                str += "from " + from + " to " + to + "";
                str += ", turn";
            }
            str += ".";

            edges.get(i).setStep(str);
            
		}
		return edges;
	}
	
}
