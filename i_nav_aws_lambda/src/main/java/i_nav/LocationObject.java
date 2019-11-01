package i_nav;

import org.json.simple.JSONObject;

public class LocationObject implements INavEntity {
	
	private int object_id;
	private int location_id;
	private String short_name;
	private String long_name;
	private String description;
	private int object_type_id;
	private int x_coordinate;
	private int y_coordinate;
	private double latitude;
	private double longitude;
	private boolean active;
	
	public static String getLocationObjects(String id) {
		return null;
	}

	@Override
	public String getJSONString() {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("object_id", location_id);
		jsonObject.put("short_name", short_name);
		jsonObject.put("long_name", long_name);
		
		return jsonObject.toJSONString();
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

	public String getShort_name() {
		return short_name;
	}

	public void setShort_name(String short_name) {
		this.short_name = short_name;
	}

	public String getLong_name() {
		return long_name;
	}

	public void setLong_name(String long_name) {
		this.long_name = long_name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getObject_type_id() {
		return object_type_id;
	}

	public void setObject_type_id(int object_type_id) {
		this.object_type_id = object_type_id;
	}

	public int getX_coordinate() {
		return x_coordinate;
	}

	public void setX_coordinate(int x_coordinate) {
		this.x_coordinate = x_coordinate;
	}

	public int getY_coordinate() {
		return y_coordinate;
	}

	public void setY_coordinate(int y_coordinate) {
		this.y_coordinate = y_coordinate;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}
	
	
}
