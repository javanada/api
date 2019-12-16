package i_nav;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	
	public void calculatePath(LocationObjectVertex start, LocationObjectVertex end) {
		this.start = start;
		this.end = end;
		dist = new HashMap<LocationObjectVertex, Integer>();
		path = new HashMap<LocationObjectVertex, ArrayList<Edge>>();
		
		calculatePath(start, end, 0, new ArrayList<Edge>());
		
	}
	
	public void testPath(LocationObjectVertex start, LocationObjectVertex end) {
		this.start = start;
		this.end = end;
		
		List<LocationObjectVertex> neighbors = G.neighbors(start);
		
		for (LocationObjectVertex n : neighbors) {
			System.out.println("NEIGHBOR of " + start.getObject_id() + ": " + n.getObject_id());
			if (G.getAdj().containsKey("" + n.getObject_id())) {
				List<Edge> edges = G.getAdj().get("" + n.getObject_id());
				for (Edge e : edges) {
					
				}
			}
		}
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
		if (c > 100) {
			return;
		}
		c++;
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
	
	
	private void calculatePath(LocationObjectVertex current, LocationObjectVertex end, int currentDistance, ArrayList<Edge> currentEdges) {
		
		System.out.println("current: " + current.getObject_id() + " currentDist: " + currentDistance);
		if (!dist.containsKey(current)) {
			dist.put(current, currentDistance);
		}
		if (!path.containsKey(current)) {
			path.put(current, currentEdges);
		}
		
		if (!current.equals(end)) {
			
//			List<Map<LocationObjectVertex, Edge>> neighbors = G.neighbors(current);
//			
//			for (Map<LocationObjectVertex, Edge> n : neighbors) {
//				LocationObjectVertex neighbor = n.entrySet().iterator().next().getKey();
//				Edge neighborEdge = n.entrySet().iterator().next().getValue();
//				
//				if (!dist.containsKey(neighbor)) {
//					currentEdges.add(neighborEdge);
//					calculatePath(neighbor, end, currentDistance + neighborEdge.weight(), currentEdges);
//				}
//			}
			
			List<LocationObjectVertex> neighbors = G.neighbors(current);
			
			for (LocationObjectVertex n : neighbors) {
				System.out.println("NEIGHBOR of " + current.getObject_id() + ": " + n.getObject_id());
				if (G.getAdj().containsKey("" + n.getObject_id())) {
					List<Edge> edges = G.getAdj().get("" + n.getObject_id());
					for (Edge e : edges) {
						if (!dist.containsKey(n)) {
							currentEdges.add(e);
							calculatePath(n, end, currentDistance + e.weight(), currentEdges);
						}
					}
				}
			}
			
		} else {
			System.out.println("we're done... current: " + current.getObject_id());
		}
	}
}
