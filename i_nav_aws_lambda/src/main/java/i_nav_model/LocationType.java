package i_nav_model;

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

import i_nav.INavEntity;

/**
 * 
 * @author CSCD490 Team5
 * @version 1.0
 * 
 *
 */
public class LocationType implements INavEntity {
	
	private int location_type_id;
	private String short_name;
	private String long_name;
	private String description;

	public static JSONArray updateLocType(JSONObject updateLocType) {
		JSONArray JSONArr = new JSONArray();

		String update = "UPDATE `location_types` ";
		String set = " SET location_type_id = location_type_id";

		if (updateLocType.get("short_name") != null) {
			set += ", `short_name` = ? ";
		}
		if (updateLocType.get("long_name") != null) {
			set += ", `long_name` = ? ";
		}
		if (updateLocType.get("description") != null) {
			set += ", `description` = ? ";
		}

		String where = " WHERE `location_type_id` = ?";

		String query = update + set + where;

		try {
			Connection conn = DriverManager.getConnection(url, username, password);
			PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

			int counter = 1;

			if (updateLocType.get("short_name") != null){
				stmt.setString(counter++, updateLocType.get("short_name").toString());
			}
			if (updateLocType.get("long_name") != null) {
				stmt.setString(counter++, updateLocType.get("long_name").toString());
			}
			if (updateLocType.get("description") != null) {
				stmt.setString(counter++, updateLocType.get("description").toString());
			}

			stmt.setInt(counter, Integer.parseInt(updateLocType.get("location_type_id").toString()));

			stmt.executeUpdate();
			ResultSet resultSet = stmt.getGeneratedKeys();

			if (resultSet.next()) {
				long id = resultSet.getLong(1);
				JSONArr = Role.getRoles("" + id);
			}

		} catch (SQLException e) {
			JSONObject obj = new JSONObject();
			obj.put("SQLException", e.getMessage());
			JSONArr.add(obj);
		}

		return JSONArr;
	}
	public static JSONArray deleteLocType(String id) {
		JSONArray jsonArr = new JSONArray();

		return jsonArr;
	}
	public static JSONArray newLocationType(JSONObject newLocationType) {
		JSONArray jsonArray = new JSONArray();
		
		String query = "INSERT INTO `location_types` (`short_name`, `long_name`, `description`) " + 
				"VALUES (?, ?, ?);";
		
		try {
			
			Connection conn = DriverManager.getConnection(url, username, password);
			PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			
			if (newLocationType.get("short_name") != null) { stmt.setString(1, newLocationType.get("short_name").toString()); } else { stmt.setNull(1, java.sql.Types.VARCHAR); }
			if (newLocationType.get("long_name") != null) { stmt.setString(2, newLocationType.get("long_name").toString()); } else { stmt.setNull(2, java.sql.Types.VARCHAR); }
			if (newLocationType.get("description") != null) { stmt.setString(3, newLocationType.get("description").toString()); } else { stmt.setNull(3, java.sql.Types.VARCHAR); }
			
			stmt.executeUpdate();
			ResultSet resultSet = stmt.getGeneratedKeys();
			if (resultSet.next()) {
                long id = resultSet.getLong(1);
                jsonArray = LocationType.getLocationTypes("" + id);
            }
			
			
		} catch (SQLException e) {
			JSONObject obj = new JSONObject();
			obj.put("SQLException", e.getMessage());
			jsonArray.add(obj);
		}
		
		return jsonArray;
	}
	
	public static JSONArray getLocationTypes(String id) {
		String returnStr = "";
		String where = "";
		
		String select = " SELECT * FROM location_types lt ";
		String join = "  ";
		if (id != null) {
			where = " WHERE lt.location_type_id = ? ";
		}
		String query = select + join + where;
		
		JSONArray jsonArray = new JSONArray();

		try {
			Connection conn = DriverManager.getConnection(url, username, password);
//			Statement stmt = conn.createStatement();
			PreparedStatement stmt = conn.prepareStatement(query);
			if (id != null) {
				stmt.setString(1, id);
			}
			ResultSet resultSet = stmt.executeQuery();

			
			while (resultSet.next()) {
				LocationType locationType = new LocationType();
				
				
				locationType.setLocation_type_id(resultSet.getInt(1));
				locationType.setShort_name(resultSet.getString(2));
				locationType.setLong_name(resultSet.getString(3));
				locationType.setDescription(resultSet.getString(4));
				
				
				JSONParser parser = new JSONParser();
				try {
					
					JSONObject locationTypeJson = (JSONObject) parser.parse(locationType.getJSONString());
					
					jsonArray.add(locationTypeJson);
					
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
