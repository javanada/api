package i_nav;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class Edge {
	
	private LocationObjectVertex v1;
	private LocationObjectVertex v2;
	private int weight;
	private String step;
	
	public Edge(LocationObjectVertex v1, LocationObjectVertex v2, int weight) {
		this.v1 = v1;
		this.v2 = v2;
		this.weight = weight;
	}
	
	public LocationObjectVertex v1() {
		return v1;
	}
	
	public LocationObjectVertex v2() {
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
		obj.put("v1", v1.toJSON());
		obj.put("v2", v2.toJSON());
		obj.put("weight", weight);
		return obj;
	}

	public String getStep() {
		return step;
	}

	public void setStep(String step) {
		this.step = step;
	}
	
	

}
