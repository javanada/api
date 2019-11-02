package i_nav;

import org.json.simple.JSONObject;

public class LocationObjectType implements INavEntity {
	
	private int object_type_id;
	private String short_name;
	private String long_name;
	private String description;
	

	@Override
	public String getJSONString() {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("object_type_id", object_type_id);
		jsonObject.put("short_name", short_name);
		jsonObject.put("long_name", long_name);
		jsonObject.put("description", description);
		return jsonObject.toJSONString();
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public String getLong_name() {
		return long_name;
	}


	public void setLong_name(String long_name) {
		this.long_name = long_name;
	}


	public String getShort_name() {
		return short_name;
	}


	public void setShort_name(String short_name) {
		this.short_name = short_name;
	}


	public int getObject_type_id() {
		return object_type_id;
	}


	public void setObject_type_id(int object_type_id) {
		this.object_type_id = object_type_id;
	}

}
