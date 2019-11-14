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

public class Role implements INavEntity {
	
	private int role_id;
	private String short_name;
	private String long_name;
	private String description;
	
	public static JSONArray getRoles(String id) {
		
		String select = " SELECT * FROM roles r ";
		String join = "  ";
		String where = "  ";
		
		if (id != null) {
			where = " WHERE r.role_id = ? ";
		}
		String query = select + join + where;
		
		JSONArray jsonArray = new JSONArray();

		try {
			Connection conn = DriverManager.getConnection(url, username, password);
			PreparedStatement stmt = conn.prepareStatement(query);
			if (id != null) {
				stmt.setString(1, id);
			}
			ResultSet resultSet = stmt.executeQuery();

			
			while (resultSet.next()) {
				
				Role role = new Role();
				role.setRole_id(resultSet.getInt(1));
				role.setShort_name(resultSet.getString(2));
				role.setLong_name(resultSet.getString(3));
				role.setDescription(resultSet.getString(4));
				
				JSONParser parser = new JSONParser();
				try {
					
					JSONObject roleJson = (JSONObject) parser.parse(role.getJSONString());
					
					jsonArray.add(roleJson);
					
				} catch (ParseException e) {
					JSONObject obj = new JSONObject();
					obj.put("ParseException", e.getMessage());
					jsonArray.add(obj);
				}
				
			}

		} catch (SQLException e) {
			JSONObject obj = new JSONObject();
			obj.put("SQLException", e.getMessage());
			jsonArray.add(obj);
		}

		return jsonArray;
	}
	
	public static JSONArray newRole(JSONObject newRole) {
		JSONArray jsonArray = new JSONArray();
		
		String query = "INSERT INTO `roles` (`short_name`, `long_name`, `description`) " + 
				"VALUES (?, ?, ?);";
		
		try {
			
			Connection conn = DriverManager.getConnection(url, username, password);
			PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			
			if (newRole.get("short_name") != null) { stmt.setString(1, newRole.get("short_name").toString()); } else { stmt.setNull(1, java.sql.Types.VARCHAR); }
			if (newRole.get("long_name") != null) { stmt.setString(2, newRole.get("long_name").toString()); } else { stmt.setNull(2, java.sql.Types.VARCHAR); }
			if (newRole.get("description") != null) { stmt.setString(3, newRole.get("description").toString()); } else { stmt.setNull(3, java.sql.Types.VARCHAR); }
			
			stmt.executeUpdate();
			ResultSet resultSet = stmt.getGeneratedKeys();
			if (resultSet.next()) {
                long id = resultSet.getLong(1);
                jsonArray = Role.getRoles("" + id);
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
		jsonObject.put("role_id", role_id);
		jsonObject.put("short_name", short_name);
		jsonObject.put("long_name", long_name);
		jsonObject.put("description", description);
		return jsonObject.toJSONString();
	}

	public int getRole_id() {
		return role_id;
	}

	public void setRole_id(int role_id) {
		this.role_id = role_id;
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
