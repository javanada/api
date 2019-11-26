package i_nav;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Search {
	
	private HashMap<LocationObjectVertex, Integer> dist;
	private HashMap<LocationObjectVertex, ArrayList<Edge>> path;
	private CloudGraphListUndirected G;
	
	public Search(CloudGraphListUndirected G) {
		this.G = G;
	}
	
	public HashMap<LocationObjectVertex, ArrayList<Edge>> getPath() {
		return path;
	}
	
	public Map<LocationObjectVertex, Integer> getDist() {
		return dist;
	}
	
	public void calculatePath(LocationObjectVertex start, LocationObjectVertex end) {
		
		dist = new HashMap<LocationObjectVertex, Integer>();
		path = new HashMap<LocationObjectVertex, ArrayList<Edge>>();
		calculatePath(start, end, 0, new ArrayList<Edge>());
		
	}
	
	
	private void calculatePath(LocationObjectVertex current, LocationObjectVertex end, int currentDistance, ArrayList<Edge> currentEdges) {
		
		if (!dist.containsKey(current)) {
			dist.put(current, currentDistance);
		}
		if (!path.containsKey(current)) {
			path.put(current, currentEdges);
		}
		
		if (!current.equals(end)) {
			
			List<Map<LocationObjectVertex, Edge>> neighbors = G.neighbors(current);
			
			for (Map<LocationObjectVertex, Edge> n : neighbors) {
				LocationObjectVertex neighbor = n.entrySet().iterator().next().getKey();
				Edge neighborEdge = n.entrySet().iterator().next().getValue();
				
				if (!dist.containsKey(neighbor)) {
					currentEdges.add(neighborEdge);
					calculatePath(neighbor, end, currentDistance + neighborEdge.weight(), currentEdges);
				}
			}
			
		}
	}
}
