package i_nav;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

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
		String returnStr = "";
		String where = "";
		
		String select = " SELECT * FROM users u ";
		String join = "  ";
		if (id != null) {
			where = " WHERE u.user_id = ? ";
		}
		String query = select + join + where;
		
		JSONArray jsonArray = new JSONArray();

		try {
			Connection conn = DriverManager.getConnection(url, INavEntity.username, INavEntity.password);
//			Statement stmt = conn.createStatement();
			PreparedStatement stmt = conn.prepareStatement(query);
			if (id != null) {
				stmt.setString(1, id);
			}
			ResultSet resultSet = stmt.executeQuery();

			
			while (resultSet.next()) {
				User user = new User();
				
				
				user.setUser_id(resultSet.getInt(1));
				user.setUsername(resultSet.getString(2));
				user.setSalt(resultSet.getString(3));
				user.setPassword(resultSet.getString(4));
				user.setFirst_name(resultSet.getString(5));
				user.setLast_name(resultSet.getString(6));
				user.setEmail(resultSet.getString(7));
				user.setRole_id(resultSet.getInt(8));
				user.setActive(resultSet.getBoolean(9));
				
				
				JSONParser parser = new JSONParser();
				try {
					
					JSONObject userJson = (JSONObject) parser.parse(user.getJSONString());
					
					jsonArray.add(userJson);
					
				} catch (ParseException e) {
					JSONObject obj = new JSONObject();
					obj.put("ParseException", e.getMessage());
					jsonArray.add(obj);
				}
				
			}
			returnStr += jsonArray.toJSONString();

		} catch (SQLException e) {
			returnStr += e.getMessage() + " " + query;
		}

		return jsonArray;
	}
	
	public static JSONArray newUser(JSONObject newUser) {
		JSONArray jsonArray = new JSONArray();
		
		String query = "INSERT INTO `users` (`username`, `salt`, `password`, `first_name`, `last_name`, `email`, `role_id`, `active`)  " + 
				"VALUES (?, ?, ?, ?, ?, ?, ?, ?);";
		
		try {
			
			Connection conn = DriverManager.getConnection(url, INavEntity.username, INavEntity.password);
			PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			
			if (newUser.get("username") != null) { stmt.setString(1, newUser.get("username").toString()); } else { stmt.setNull(1, java.sql.Types.VARCHAR); }
			if (newUser.get("salt") != null) { stmt.setString(2, newUser.get("salt").toString()); } else { stmt.setNull(2, java.sql.Types.VARCHAR); }
			if (newUser.get("password") != null) { stmt.setString(3, newUser.get("password").toString()); } else { stmt.setNull(3, java.sql.Types.VARCHAR); }
			if (newUser.get("first_name") != null) { stmt.setString(4, newUser.get("first_name").toString()); } else { stmt.setNull(4, java.sql.Types.VARCHAR); }
			if (newUser.get("last_name") != null) { stmt.setString(5, newUser.get("last_name").toString()); } else { stmt.setNull(5, java.sql.Types.VARCHAR); }
			if (newUser.get("email") != null) { stmt.setString(6, newUser.get("email").toString()); } else { stmt.setNull(6, java.sql.Types.VARCHAR); }
			if (newUser.get("role_id") != null) { stmt.setInt(7, Integer.parseInt(newUser.get("role_id").toString())); } else { stmt.setNull(7, java.sql.Types.INTEGER); }
			stmt.setInt(8, 1);
			
			stmt.executeUpdate();
			ResultSet resultSet = stmt.getGeneratedKeys();
			if (resultSet.next()) {
                long id = resultSet.getLong(1);
                jsonArray = User.getUsers("" + id);
            }
			
			
		} catch (SQLException e) {
			JSONObject obj = new JSONObject();
			obj.put("SQLException", e.getMessage());
			jsonArray.add(obj);
		}
		
		return jsonArray;
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
