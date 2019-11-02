package i_nav;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class User implements INavEntity {
	
	private int user_id;
	private String username;
	private String salt;
	private String password;
	private String first_name;
	private String last_name;
	private String email;
	private int role_id;
	private boolean active;
	
	public static JSONArray getUsers(String id) {
		return null;
	}
	
	public static JSONArray newUser(JSONObject newUser) {
		return null;
	}

	@Override
	public String getJSONString() {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("user_id", user_id);
		jsonObject.put("username", username);
		jsonObject.put("salt", salt);
		jsonObject.put("password", password);
		jsonObject.put("first_name", first_name);
		jsonObject.put("last_name", last_name);
		jsonObject.put("email", email);
		jsonObject.put("role_id", role_id);
		jsonObject.put("active", active);
		return jsonObject.toJSONString();
	}

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFirst_name() {
		return first_name;
	}

	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}

	public String getLast_name() {
		return last_name;
	}

	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getRole_id() {
		return role_id;
	}

	public void setRole_id(int role_id) {
		this.role_id = role_id;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}
	
	

}
