package i_nav_model;

import java.util.Date;

import org.json.simple.JSONObject;

public class FingerprintEntityBase implements INavEntity {
	
	private Date created_on;
	private String created_by;
	private Date modified_on;
	private String modified_by;

	@Override
	public String getJSONString() {
		return getJSON().toJSONString();
	}
	public JSONObject getJSON() {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("created_on", created_on);
		jsonObject.put("created_by", created_by);
		jsonObject.put("modified_on", modified_on);
		jsonObject.put("modified_by", modified_by);
		return jsonObject;
	}
	
	public Date getCreated_on() {
		return created_on;
	}
	public void setCreated_on(Date created_on) {
		this.created_on = created_on;
	}
	public String getCreated_by() {
		return created_by;
	}
	public void setCreated_by(String created_by) {
		this.created_by = created_by;
	}
	public Date getModified_on() {
		return modified_on;
	}
	public void setModified_on(Date modified_on) {
		this.modified_on = modified_on;
	}
	public String getModified_by() {
		return modified_by;
	}
	public void setModified_by(String modified_by) {
		this.modified_by = modified_by;
	}

}
