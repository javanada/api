package i_nav;

import org.json.simple.JSONObject;

public class LocationObjectVertex {
	
	private int object_id;
	private int location_id;
	private int x;
	private int y;
	
	
	public int getObject_id() {
		return object_id;
	}
	public void setObject_id(int object_id) {
		this.object_id = object_id;
	}
	public int getLocation_id() {
		return location_id;
	}
	public void setLocation_id(int location_id) {
		this.location_id = location_id;
	}
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	public String getJSONString() {
		
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("object_id", object_id);
		jsonObject.put("location_id", location_id);
		jsonObject.put("x", x);
		jsonObject.put("y", y);
		
		return jsonObject.toJSONString();
	}
	public JSONObject toJSON() {
		
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("object_id", object_id);
		jsonObject.put("location_id", location_id);
		jsonObject.put("x", x);
		jsonObject.put("y", y);
		
		return jsonObject;
	}
	
}
