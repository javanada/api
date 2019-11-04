package i_nav;

import org.json.simple.JSONObject;

public class Edge {
	
	private LocationObject v1;
	private LocationObject v2;
	private int weight;
	
	public Edge(LocationObject v1, LocationObject v2, int weight) {
		this.v1 = v1;
		this.v2 = v2;
		this.weight = weight;
	}
	
	public LocationObject v1() {
		return v1;
	}
	
	public LocationObject v2() {
		return v2;
	}
	
	public int weight() {
		return weight;
	}
	
	public String toString() {
		return "" + v1.toString() + "-" + v2.toString();
	}
	
	public JSONObject getJson() {
		JSONObject obj = new JSONObject();
		obj.put("v1", v1.getJSONString());
		obj.put("v2", v2.getJSONString());
		obj.put("weight", weight);
		return obj;
	}

}
