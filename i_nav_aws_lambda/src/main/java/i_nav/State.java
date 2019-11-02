package i_nav;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class State implements INavEntity {
	
	private int state_id;
	private String short_name;
	private String long_name;
	
	public static JSONArray getState(String id) {
		return null;
	}
	
	public static JSONArray newState(JSONObject newState) {
		return null;
	}

	@Override
	public String getJSONString() {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("state_id", state_id);
		jsonObject.put("short_name", short_name);
		jsonObject.put("long_name", long_name);
		return jsonObject.toJSONString();
	}

	public int getState_id() {
		return state_id;
	}

	public void setState_id(int state_id) {
		this.state_id = state_id;
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
	
	
	
}
