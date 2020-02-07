package i_nav;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import i_nav_model.LocationObject;

/**
 * 
 * @author CSCD490 Team5
 * @version 1.0
 * 
 *
 */
public class CloudGraphListUndirected { // should be undirected
	
	private Map<String, List<Edge>> adj; // need to use String, not LocationObjectVertex... should be ID
	private Map<LocationObjectVertex, List<Edge>> adjVertex;
	private int numEdges;
	private List<String> Mark;
	private int maxWeight;
	
	private DBAccessGraph dbGraphAccess;
	private String graphName;
	
	public Map<String, List<Edge>> getAdj() {
		return adj;
	}
	
	
	public CloudGraphListUndirected(String graphName, boolean isLambda) {
		this.graphName = graphName;
		adj = new HashMap<String, List<Edge>>();
		adjVertex = new HashMap<LocationObjectVertex, List<Edge>>();
		Mark = new LinkedList<String>();
		numEdges = 0;
		
		
		dbGraphAccess = new DBAccessGraphDynamoDB(isLambda);
		dbGraphAccess.createTable(graphName);
		
	}
	
	public LocationObjectVertex getVertex(String id) {
		
		return dbGraphAccess.getVertex(graphName, id);
	}
	
	
	public LocationObjectVertex setVertex(int x, int y) {
		return null;
//		int i = r.nextInt(animals.length);
//		
//		LocationObjectVertex locationItem = new LocationObjectVertex();
//		locationItem.setX(x);
//		locationItem.setY(y);
//		locationItem.setObject_id(0);
//		locationItem.setLocation_id(0);
//		
//		JSONParser parser = new JSONParser();
//		JSONArray arr = new JSONArray();
//		
//		try {
//			arr = LocationObject.newLocationObject((JSONObject)parser.parse(locationItem.getJSONString()));
//		} catch (ParseException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//		if (arr.size() > 0) {
//			System.out.println(arr.get(0));
//			JSONObject newLocationJsonObject = ((JSONObject)arr.get(0));
//			locationItem.setObject_id(Integer.parseInt(newLocationJsonObject.get("object_id").toString()));
//			dbGraphAccess.addGraphVertex(newLocationJsonObject.get("object_id").toString(), null, newLocationJsonObject.toJSONString(), graphName);
//			Mark.add(locationItem);
//			return locationItem;
//			
//		} else {
//			return null;
//		}
		
	}
	
	public void setVertex(JSONObject locationObjectJson) {
		JSONObject vertex = new JSONObject();
		vertex.put("object_id", locationObjectJson.get("object_id"));
		vertex.put("location_id", locationObjectJson.get("location_id"));
		vertex.put("x", locationObjectJson.get("x_coordinate"));
		vertex.put("y", locationObjectJson.get("y_coordinate"));
		dbGraphAccess.addGraphVertex(locationObjectJson.get("object_id").toString(), null, vertex.toJSONString(), locationObjectJson.get("location_id").toString(), graphName);
	}
	
	public void setEdgeDirected(LocationObjectVertex v1, LocationObjectVertex v2, int weight) {
		
		// todo: this should set undirected edge, fix
		
		
		
		if (adj.get("" + v1.getObject_id()) == null) {
			adj.put("" + v1.getObject_id(), new LinkedList<Edge>());
			adjVertex.put(v1, new LinkedList<Edge>());
			Mark.add("" + v1.getObject_id());
		}
		double distance_x = v2.getX() - v1.getX();
		double distance_y = v2.getY() - v1.getY();
		weight = (int) Math.sqrt(distance_x * distance_x + distance_y * distance_y);
		
		Edge e = new Edge(v1, v2, weight);
		adj.get("" + v1.getObject_id()).add(e);
		
		JSONArray arr = new JSONArray();
		String str = "";
		for (Edge i : adj.get("" + v1.getObject_id())) {
			JSONObject obj = new JSONObject();
			
			obj.put("weight", i.weight());
			
			JSONObject locationV1 = new JSONObject();
			locationV1.put("x", i.v1().getX());
			locationV1.put("y", i.v1().getY());
			locationV1.put("object_id", i.v1().getObject_id());
			locationV1.put("location_id", i.v1().getLocation_id());
			
			JSONObject locationV2 = new JSONObject();
			locationV2.put("x", i.v2().getX());
			locationV2.put("y", i.v2().getY());
			locationV2.put("object_id", i.v2().getObject_id());
			locationV2.put("location_id", i.v2().getLocation_id());
			
			obj.put("v1", locationV1);
			obj.put("v2", locationV2);
			
			arr.add(obj);
		}
		
		dbGraphAccess.updateItem(graphName, "" + v1.getObject_id(), arr.toJSONString());
		
		numEdges++;
		if (weight > maxWeight) {
			maxWeight = weight;
		}
	}
	
	public Set<LocationObjectVertex> getPoints(String locationId) {
		Map<LocationObjectVertex, List<Edge>> pointsAndEdges = dbGraphAccess.getCloudVerticesAndEdges(graphName, locationId);
		
		for (LocationObjectVertex item : pointsAndEdges.keySet()) {
			
			for (Edge e : pointsAndEdges.get(item)) {
				
				LocationObjectVertex v1 = e.v1();
				LocationObjectVertex v2 = e.v2();
				
				if (adj.get("" + v1.getObject_id()) == null) {
					adj.put("" + v1.getObject_id(), new LinkedList<Edge>());
					Mark.add("" + v1.getObject_id());
				}
				adj.get("" + v1.getObject_id()).add(e);
			}
		}
		return pointsAndEdges.keySet();
	}
	
	public int getNumEdges() {
		return numEdges;
	}
	
	public int getMaxWeight() {
		return maxWeight;
	}

	public int n() {
		return Mark.size();
	}
	
	public List<Edge> getEdges() {
		List<Edge> list = new ArrayList<Edge>();
		for (String item : adj.keySet()) {
			for (Edge e : adj.get(item)) {
				list.add(e);
			}
		}
		return list;
	}
	
	public static JSONArray getEdges(String locationId) {
		JSONArray jsonArray = new JSONArray();
		
		CloudGraphListUndirected graph1 = new CloudGraphListUndirected("i_nav_graph1", true);
		graph1.getPoints(locationId);
		List<Edge> list = graph1.getEdges();
		
		for (Edge e : list) {
			jsonArray.add(e.getJson());
		}
		
		return jsonArray;
	}
	
	public static JSONArray removeEdge(CloudGraphListUndirected graph, String source, String dest) {
		JSONArray jsonArray = new JSONArray();
		
		if (graph == null)  {
			graph = new CloudGraphListUndirected("i_nav_graph1", true);		
		}
		
		JSONParser parser = new JSONParser();
		try {
			JSONArray arr = ((JSONArray)parser.parse(graph.dbGraphAccess.getAdj("i_nav_graph1", source)));
			
			JSONArray arrKeep = new JSONArray();
			for (int i = 0; i < arr.size(); i++) {
				JSONObject keep = ((JSONObject) arr.get(i));
				JSONObject o = ((JSONObject) arr.get(i));
				JSONObject v2 = ((JSONObject)o.get("v2"));
				if (!dest.equals(v2.get("object_id").toString())) {
					arrKeep.add(keep);
				}
			}
			graph.dbGraphAccess.updateItem("i_nav_graph1", source, arrKeep.toJSONString());
			
		} catch (ParseException e) {
			JSONObject obj = new JSONObject();
			obj.put("ParseException", e.getMessage());
			jsonArray.add(obj);
			e.printStackTrace();
		}
		
		return jsonArray;
	}
	
	public static JSONArray removeVertex(CloudGraphListUndirected graph, String id) {
		JSONArray jsonArray = new JSONArray();
		
		if (graph == null)  {
			graph = new CloudGraphListUndirected("i_nav_graph1", true);			
		}
		
		JSONParser parser = new JSONParser();
		try {
			JSONArray arr = ((JSONArray)parser.parse(graph.dbGraphAccess.getAdj("i_nav_graph1", id)));
			
			for (int i = 0; i < arr.size(); i++) {
				JSONObject o = ((JSONObject) arr.get(i));
				JSONObject v2 = ((JSONObject)o.get("v2"));
				jsonArray.addAll(removeEdge(graph, v2.get("object_id").toString(), id));
			}
			graph.dbGraphAccess.updateItem("i_nav_graph1", id, "[]");
			
		} catch (ParseException e) {
			JSONObject obj = new JSONObject();
			obj.put("ParseException", e.getMessage());
			jsonArray.add(obj);
			e.printStackTrace();
		}
		
		return jsonArray;
	}
	
	public static JSONArray setEdgeUndirected(CloudGraphListUndirected graph, String sourceObjectId, String sourceLocationId, String destObjectId, String destLocationId) {
		
		JSONArray jsonArray = new JSONArray();
		
		JSONArray arrSource = LocationObject.getLocationObjects(sourceObjectId, sourceLocationId, null);
		JSONArray arrDest = LocationObject.getLocationObjects(destObjectId, destLocationId, null);
		
		if (arrSource.size() == 1 && arrDest.size() == 1) {
			
			JSONObject source = (JSONObject) arrSource.get(0);
			JSONObject dest = (JSONObject) arrDest.get(0);
			
			LocationObject sourceObject = new LocationObject(source);
			LocationObject destObject = new LocationObject(dest);
			
			LocationObjectVertex sourceVertex = new LocationObjectVertex();
			LocationObjectVertex destVertex = new LocationObjectVertex();
			
			sourceVertex.setLocation_id(sourceObject.getLocation_id());
			sourceVertex.setObject_id(sourceObject.getObject_id());
			sourceVertex.setX(sourceObject.getX_coordinate());
			sourceVertex.setY(sourceObject.getY_coordinate());
			
			destVertex.setLocation_id(destObject.getLocation_id());
			destVertex.setObject_id(destObject.getObject_id());
			destVertex.setX(destObject.getX_coordinate());
			destVertex.setY(destObject.getY_coordinate());
			
			if (graph == null)  {
				graph = new CloudGraphListUndirected("i_nav_graph1", true);
				graph.getPoints(sourceLocationId);
				if (!sourceLocationId.equals(destLocationId)) {
					graph.getPoints(destLocationId);
				}
			}
			List<Edge> edgesSource = graph.getAdj().get(sourceObjectId);
			for (Edge e : edgesSource) {
				if (
					sourceObjectId.equals("" + e.v1().getObject_id()) && destObjectId.equals("" + e.v2().getObject_id()) ||
					sourceObjectId.equals("" + e.v2().getObject_id()) && destObjectId.equals("" + e.v1().getObject_id())
						) { // already exists
					return jsonArray;
				}
			}
			
//			graph1.adj.put(sourceObjectId, graph1.dbGraphAccess.getCloudVertexEdges(graph1.graphName, sourceObjectId));
//			graph1.adj.put(destObjectId, graph1.dbGraphAccess.getCloudVertexEdges(graph1.graphName, destObjectId));
			
			graph.setEdgeDirected(sourceVertex, destVertex, 0);
			graph.setEdgeDirected(destVertex, sourceVertex, 0);
			
			JSONObject ret = new JSONObject();
			ret.put("success", "maybe");
			ret.put("source", sourceVertex.getJSONString());
			ret.put("dest", destVertex.getJSONString());
			jsonArray.add(ret);
			
		} else {
			return jsonArray;
		}
		
		
		return jsonArray;
	}
	
	public static JSONArray getShortestPath(String sourceObjectId, String destObjectId, boolean isLambda) {
		
		JSONArray jsonArray = new JSONArray();
		
		CloudGraphListUndirected graph1 = new CloudGraphListUndirected("i_nav_graph1", isLambda);
		LocationObjectVertex start = graph1.getVertex(sourceObjectId);
		LocationObjectVertex end = graph1.getVertex(destObjectId);
		
		graph1.getPoints("" + start.getLocation_id());
		Search search = new Search(graph1);
		
		search.dijkstra(start, end);
		ArrayList<Edge> path = search.getPathToVertex();
		
		JSONArray arr;
//		arr = LocationObject.getLocationObjects(null, "" + start.getLocation_id(), null); // this is slow, arguably should be on the front end since front end already has this
		arr = new JSONArray();
		
		HashMap<String, LocationObject> allObjects = new HashMap<String, LocationObject>();
		for (int i = 0; i < arr.size(); i++) {
			JSONObject o = ((JSONObject)arr.get(i));
			LocationObject locationObject = new LocationObject(o);
			allObjects.put("" + locationObject.getObject_id(), locationObject);
		}
		
		if (path != null) {
			for (int i = 0; i < path.size(); i++) {
				
				Edge e = path.get(i);
				
				JSONObject obj = e.getJson();
				double dist = Math.sqrt(((e.v1().getX() - e.v2().getX()) * (e.v1().getX() - e.v2().getX()) + (e.v1().getY() - e.v2().getY()) * (e.v1().getY() - e.v2().getY())));
				
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
				
				String str = "Walk " + Math.round(dist) + " ft. ";
				
				if (i == path.size() - 1) {
					str += " from {" + from + "}. Arrive at your destination, {" + to + "}";
				} else {
					str += " from {" + from + "} to {" + to + "}";
					str += ", turn";
				}
				str += ".";
				
				
				obj.put("directions", str);
				jsonArray.add(obj);
			}
		} else {
//			jsonArray.add("path is null");
		}
		
//		if (start != null) { jsonArray.add(start.toJSON()); }
//		if (end != null) { jsonArray.add(end.toJSON()); }
		
		return jsonArray;
	}

	public List<LocationObjectVertex> neighbors(LocationObjectVertex u) {
		
		List<LocationObjectVertex> uNeighbors = new ArrayList<LocationObjectVertex>();
		
		if (u == null) {
			return uNeighbors;
		}
		
		List<Map<LocationObjectVertex, Edge>> ret = new ArrayList<Map<LocationObjectVertex, Edge>>();
		
		System.out.println("adj size: " + adj.size());
		for (String s : adj.keySet()) {
			System.out.print(", " + s);
		}
		System.out.println();
		if (adj.containsKey("" + u.getObject_id())) {
			for (Edge e : adj.get("" + u.getObject_id())) {
				if (e != null) {
					LocationObjectVertex v1 = e.v1();
					LocationObjectVertex v2 = e.v2();
					if (v1.equals(u)) {
						uNeighbors.add(v2);
					} else {
						uNeighbors.add(v1);
					}
				}
			}
		}
		return uNeighbors;
	}
	
}
