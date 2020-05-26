package i_nav;

import java.util.Objects;

import org.json.simple.JSONObject;

/**
 * 
 * @author CSCD490 Team5
 * @version 1.0
 * 
 *
 */
public class LocationObjectVertex {
	
	private int object_id;
	private int location_id;
	private int x;
	private int y;
	
	public LocationObjectVertex() {
		
	}
	
	public LocationObjectVertex(JSONObject jsonObject) {
		object_id = Integer.parseInt(jsonObject.get("object_id") != null ? jsonObject.get("object_id").toString() : "0");
		location_id = Integer.parseInt(jsonObject.get("location_id") != null ? jsonObject.get("location_id").toString() : "0");
		x = Integer.parseInt(jsonObject.get("x").toString());
		y = Integer.parseInt(jsonObject.get("y").toString());
	}
	
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
	
	@Override
	public boolean equals(Object other) {
		if (other instanceof LocationObjectVertex) {
			return object_id == ((LocationObjectVertex)other).object_id;
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(object_id);
	}
}
