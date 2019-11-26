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

public class LocationObjectType implements INavEntity {
	
	private int object_type_id;
	private String short_name;
	private String long_name;
	private String description;

	public static JSONArray updateObjLoc(JSONObject updateObjLoc) {
		JSONArray JSONArr = new JSONArray();

		String update = "UPDATE `object_types` ";
		String set = " SET object_type_id = object_type_id";

		if (updateObjLoc.get("short_name") != null) {
			set += ", `short_name` = ? ";
		}
		if (updateObjLoc.get("long_name") != null) {
			set += ", `long_name` = ? ";
		}
		if (updateObjLoc.get("description") != null) {
			set += ", `description` = ? ";
		}

		String where = " WHERE `object_type_id` = ?";

		String query = update + set + where;

		try {
			Connection conn = DriverManager.getConnection(url, username, password);
			PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

			int counter = 1;

			if (updateObjLoc.get("short_name") != null){
				stmt.setString(counter++, updateObjLoc.get("short_name").toString());
			}
			if (updateObjLoc.get("long_name") != null) {
				stmt.setString(counter++, updateObjLoc.get("long_name").toString());
			}
			if (updateObjLoc.get("description") != null) {
				stmt.setString(counter++, updateObjLoc.get("description").toString());
			}

			stmt.setInt(counter, Integer.parseInt(updateObjLoc.get("object_type_id").toString()));

			stmt.executeUpdate();
			ResultSet resultSet = stmt.getGeneratedKeys();

			if (resultSet.next()) {
				long id = resultSet.getLong(1);
				JSONArr = LocationObjectType.getLocationObjectTypes("" + id);
			}

		} catch (SQLException e) {
			JSONObject obj = new JSONObject();
			obj.put("SQLException", e.getMessage());
			JSONArr.add(obj);
		}

		return JSONArr;
	}
	public static JSONArray deleteObjTypes(String id){
		JSONArray JSONArr = new JSONArray();

		return JSONArr;
	}
	public static JSONArray getLocationObjectTypes(String id) {
		
		String returnStr = "";
		String where = "";
		
		String select = " SELECT * FROM object_types ot ";
		String join = "  ";
		if (id != null) {
			where = " WHERE ot.object_type_id = ? ";
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
				LocationObjectType locationObjectType = new LocationObjectType();
				
				
				locationObjectType.setObject_type_id(resultSet.getInt("object_type_id"));
				locationObjectType.setShort_name(resultSet.getString("short_name"));
				locationObjectType.setLong_name(resultSet.getString("long_name"));
				locationObjectType.setDescription(resultSet.getString("description"));
				
				
				JSONParser parser = new JSONParser();
				try {
					
					JSONObject locationObjectTypeJson = (JSONObject) parser.parse(locationObjectType.getJSONString());
					
					jsonArray.add(locationObjectTypeJson);
					
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
	
	public static JSONArray newLocationObjectType(JSONObject newLocationObjectType) {
		JSONArray jsonArray = new JSONArray();
		
		String query = "INSERT INTO `object_types` (`short_name`, `long_name`, `description`) " + 
				"VALUES (?, ?, ?);";
		
		try {
			
			Connection conn = DriverManager.getConnection(url, username, password);
			PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			
			if (newLocationObjectType.get("short_name") != null) { stmt.setString(1, newLocationObjectType.get("short_name").toString()); } else { stmt.setNull(1, java.sql.Types.VARCHAR); }
			if (newLocationObjectType.get("long_name") != null) { stmt.setString(2, newLocationObjectType.get("long_name").toString()); } else { stmt.setNull(2, java.sql.Types.VARCHAR); }
			if (newLocationObjectType.get("description") != null) { stmt.setString(3, newLocationObjectType.get("description").toString()); } else { stmt.setNull(3, java.sql.Types.VARCHAR); }
			
			stmt.executeUpdate();
			ResultSet resultSet = stmt.getGeneratedKeys();
			if (resultSet.next()) {
                long id = resultSet.getLong(1);
                jsonArray = LocationObjectType.getLocationObjectTypes("" + id);
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
