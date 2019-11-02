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
	
	
	public static JSONArray newLocationObject(JSONObject newLocationObject) {
		JSONArray jsonArray = new JSONArray();
		
		String query = "INSERT INTO `objects` (`location_id`, `short_name`, `long_name`, `description`, `object_type_id`, `x_coordinate`, `y_coordinate`, `latitude`, `longitude`, `active`) " + 
				"VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
		
		try {
			
			Connection conn = DriverManager.getConnection(url, username, password);
			PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			
			if (newLocationObject.get("location_id") != null) { stmt.setInt(1, Integer.parseInt(newLocationObject.get("location_id").toString())); } else { stmt.setNull(1, java.sql.Types.INTEGER); }
			if (newLocationObject.get("short_name") != null) { stmt.setString(2, newLocationObject.get("short_name").toString()); } else { stmt.setNull(2, java.sql.Types.VARCHAR); }
			if (newLocationObject.get("long_name") != null) { stmt.setString(3, newLocationObject.get("long_name").toString()); } else { stmt.setNull(3, java.sql.Types.VARCHAR); }
			if (newLocationObject.get("description") != null) { stmt.setString(4, newLocationObject.get("description").toString()); } else { stmt.setNull(4, java.sql.Types.INTEGER); }
			if (newLocationObject.get("object_type_id") != null) { stmt.setInt(5, Integer.parseInt(newLocationObject.get("object_type_id").toString())); } else { stmt.setNull(5, java.sql.Types.INTEGER); }
			if (newLocationObject.get("x_coordinate") != null) { stmt.setInt(6, Integer.parseInt(newLocationObject.get("x_coordinate").toString())); } else { stmt.setNull(6, java.sql.Types.INTEGER); }
			if (newLocationObject.get("y_coordinate") != null) { stmt.setInt(7, Integer.parseInt(newLocationObject.get("y_coordinate").toString())); } else { stmt.setNull(7, java.sql.Types.INTEGER); }
			if (newLocationObject.get("latitude") != null) { stmt.setDouble(8, Double.parseDouble(newLocationObject.get("latitude").toString())); } else { stmt.setNull(8, java.sql.Types.FLOAT); }
			if (newLocationObject.get("longitude") != null) { stmt.setDouble(9, Double.parseDouble(newLocationObject.get("longitude").toString())); } else { stmt.setNull(9, java.sql.Types.FLOAT); }
			stmt.setInt(10, 1);
			
			stmt.executeUpdate();
			ResultSet resultSet = stmt.getGeneratedKeys();
			if (resultSet.next()) {
                long id = resultSet.getLong(1);
                jsonArray = LocationObject.getLocationObjects("" + id);
            }
			
			
		} catch (SQLException e) {
			JSONObject obj = new JSONObject();
			obj.put("SQLException", e.getMessage());
			jsonArray.add(obj);
		}
		
		return jsonArray;
	}
	
	public static JSONArray getLocationObjects(String id) {
		String returnStr = "";
		String where = "";
		
		String select = " SELECT * FROM objects o ";
		String join = " INNER JOIN locations l ON l.location_id = o.location_id " + 
					" INNER JOIN location_types lt on l.location_type_id = lt.location_type_id " + 
					" INNER JOIN addresses a on a.address_id = l.address_id ";
		if (id != null) {
			where = " WHERE o.object_id = ? ";
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
				LocationObject locationObject = new LocationObject();
				Location location = new Location();
				LocationType locationType = new LocationType();
				Address address = new Address();
				
				location.setLocation_id(resultSet.getInt(2));
				location.setLocation_type_id(resultSet.getInt(5));
				location.setAddress_id(resultSet.getInt(17));
				location.setPrimary_object_id(resultSet.getInt(18));
				location.setScale_ft(resultSet.getDouble(19));
				
				locationObject.setObject_id(resultSet.getInt(1));
				locationObject.setLocation_id(resultSet.getInt(2));
				locationObject.setShort_name(resultSet.getString(3));
				
				locationType.setLocation_type_id(resultSet.getInt(27));
				locationType.setShort_name(resultSet.getString(28));
				
				address.setAddress_id(resultSet.getInt(17));
				address.setAddress1(resultSet.getString(32));
				
				JSONParser parser = new JSONParser();
				try {
					
					JSONObject locationJson = (JSONObject) parser.parse(location.getJSONString());
					JSONObject locationObjectJson = (JSONObject) parser.parse(locationObject.getJSONString());
					JSONObject locationTypeJson = (JSONObject) parser.parse(locationType.getJSONString());
					JSONObject addressJson = (JSONObject) parser.parse(address.getJSONString());
					
					locationObjectJson.put("location_type", locationTypeJson);
					locationObjectJson.put("location", locationJson);
					locationObjectJson.put("address", addressJson);
					
					jsonArray.add(locationObjectJson);
					
				} catch (ParseException e) {
					JSONObject obj = new JSONObject();
					obj.put("parseExceptin", e.getMessage());
					jsonArray.add(obj);
				}
				
			}
			returnStr += jsonArray.toJSONString();

		} catch (SQLException e) {
			JSONObject obj = new JSONObject();
			obj.put("SQLException", e.getMessage());
			jsonArray.add(obj);
			returnStr += e.getMessage() + " " + query;
		}

		return jsonArray;
	}

	@Override
	public String getJSONString() {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("object_id", object_id);
		jsonObject.put("location_id", location_id);
		jsonObject.put("short_name", short_name);
		jsonObject.put("long_name", long_name);
		jsonObject.put("description", description);
		jsonObject.put("object_type_id", object_type_id);
		jsonObject.put("x_coordinate", x_coordinate);
		jsonObject.put("y_coordinate", y_coordinate);
		jsonObject.put("latitude", latitude);
		jsonObject.put("longitude", longitude);
		jsonObject.put("active", active);
		
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
