package i_nav;

import org.json.simple.JSONObject;

public class LocationType implements INavEntity {
	
	private int location_type_id;
	private String short_name;
	private String long_name;
	private String description;
	
	public static String getLocationTypes(String id) {
		return null;
	}

	@Override
	public String getJSONString() {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("location_type_id", location_type_id);
		jsonObject.put("short_name", short_name);
		jsonObject.put("long_name", long_name);
		jsonObject.put("description", description);
		return jsonObject.toJSONString();
	}

	public int getLocation_type_id() {
		return location_type_id;
	}

	public void setLocation_type_id(int location_type_id) {
		this.location_type_id = location_type_id;
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
	
	
}
