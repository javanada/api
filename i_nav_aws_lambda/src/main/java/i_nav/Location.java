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

public class Location implements INavEntity {
	
	private int location_id;
	private String short_name;
	private String long_name;
	private String description;
	private int location_type_id;
	private int address_id;
	private int primary_object_id;
	private LocationObject primary_object;
	private double scale_ft;
	private int min_x_coordinate;
	private int min_y_coordinate;
	private int max_x_coordinate;
	private int max_y_coordinate;
	private double latitude;
	private double longitude;
	private boolean active;

	public static JSONArray updateLocation(JSONObject updateLoc){
		JSONArray JSONArr = new JSONArray();

		String update = "UPDATE `locations`";
		String set = " SET `short_name` = `?`, `long_name` = `?`, `description` = `?`, `min_x_coordinate` = `?`, `min_y_coordinate` = `?`, `max_x_coordinate` = `?`, `max_y_coordinate` = `?`, `active` = `?`";
		String where = "";

		if (where != null){
			where = " WHERE `location_id` = " + id;
		}

		String query = update + set + where;

		try {
			Connection conn = DriverManager.getConnection(url, username, password);
			PreparedStatement stmt = conn.prepareStatement(query);

			if (id != null) {
				stmt.setString(1, id);
			}

			stmt.executeUpdate();
			ResultSet result = stmt.executeQuery();

			while (result.next()){

				updateLoc.setShort_name(result.getString(1));
				updateLoc.setLong_name(result.getString(2));
				updateLoc.setDescription(result.getString(3));
				updateLoc.setMax_x_coordinate(result.getDouble(10));
				updateLoc.setMax_y_coordinate(result.getDouble(11));
				updateLoc.setMin_x_coordinate(result.getDouble(8));
				updateLoc.setMin_y_coordinate(result.getDouble(9));
			}

			if (resultSet.next()) {
				long id = result.getLong(1);
				JSONArr = Location.getLocations("" + id);
			}

		} catch (Exception e) {
			JSONObject obj = new JSONObject();
			obj.put("Exception Occured", e.getMessage());
			JSONArr.add(obj);
		}

		return JSONArr;
	}
	public static JSONArray deleteLocation(String id){
		JSONArray jsonArr = new JSONArray();

		String update = "UPDATE `locations` ";
		String set = "SET `active` = `false`";
		String where = "";

		if (id != null) {
			where = " WHERE `location_id` = " + id;
		}

		String query = update + set + where;

		try {
			Connection conn = DriverManager.getConnection(url, username, password);
			PreparedStatement stmt = conn.prepareStatement(query);

			ResultSet resultSet = stmt.executeUpdate();

			while (resultSet.next()){
				Location location = new Location();

				if (location.isActive()){
					location.setActive(false);
				}
				//stmt.executeUpdate();
			}

			JSONObject locationJson = (JSONObject) parser.parse(location.getJSONString());
			jsonArray.add(locationJson);

			stmt.executeUpdate();
		} catch (Exception e) {
			JSONObject obj = new JSONObject();
			obj.put("Exception Occured", e.getMessage());
			jsonArray.add(obj);
		}

		return jsonArr;
	}
	public static JSONArray newLocation(JSONObject newLocation) {
		
		JSONArray jsonArray = new JSONArray();
		
		String query = "INSERT INTO `locations` (`short_name`, `long_name`, `description`, `location_type_id`, `address_id`, `primary_object_id`, `scale_ft`, `min_x_coordinate`, `min_y_coordinate`, `max_x_coordinate`, `max_y_coordinate`, `latitude`, `longitude`, `active`) " + 
				"VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
		
		try {
			
			Connection conn = DriverManager.getConnection(url, username, password);
			PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			
			if (newLocation.get("short_name") != null) { stmt.setString(1, newLocation.get("short_name").toString()); } else { stmt.setNull(1, java.sql.Types.VARCHAR); }
			if (newLocation.get("long_name") != null) { stmt.setString(2, newLocation.get("long_name").toString()); } else { stmt.setNull(2, java.sql.Types.VARCHAR); }
			if (newLocation.get("description") != null) { stmt.setString(3, newLocation.get("description").toString()); } else { stmt.setNull(3, java.sql.Types.INTEGER); }
			if (newLocation.get("location_type_id") != null) { stmt.setInt(4, Integer.parseInt(newLocation.get("location_type_id").toString())); } else { stmt.setNull(4, java.sql.Types.INTEGER); }
			if (newLocation.get("address_id") != null) { stmt.setInt(5, Integer.parseInt(newLocation.get("address_id").toString())); } else { stmt.setNull(5, java.sql.Types.INTEGER); }
			if (newLocation.get("primary_object_id") != null) { stmt.setInt(6, Integer.parseInt(newLocation.get("primary_object_id").toString())); } else { stmt.setNull(6, java.sql.Types.INTEGER); }
			if (newLocation.get("scale_ft") != null) { stmt.setDouble(7, Double.parseDouble(newLocation.get("scale_ft").toString())); } else { stmt.setNull(7, java.sql.Types.FLOAT); }
			if (newLocation.get("min_x_coordinate") != null) { stmt.setInt(8, Integer.parseInt(newLocation.get("min_x_coordinate").toString())); } else { stmt.setNull(8, java.sql.Types.INTEGER); }
			if (newLocation.get("min_y_coordinate") != null) { stmt.setInt(9, Integer.parseInt(newLocation.get("min_y_coordinate").toString())); } else { stmt.setNull(9, java.sql.Types.INTEGER); }
			if (newLocation.get("max_x_coordinate") != null) { stmt.setInt(10, Integer.parseInt(newLocation.get("max_x_coordinate").toString())); } else { stmt.setNull(10, java.sql.Types.INTEGER); }
			if (newLocation.get("max_y_coordinate") != null) { stmt.setInt(11, Integer.parseInt(newLocation.get("max_y_coordinate").toString())); } else { stmt.setNull(11, java.sql.Types.INTEGER); }
			if (newLocation.get("latitude") != null) { stmt.setDouble(12, Double.parseDouble(newLocation.get("latitude").toString())); } else { stmt.setNull(12, java.sql.Types.FLOAT); }
			if (newLocation.get("longitude") != null) { stmt.setDouble(13, Double.parseDouble(newLocation.get("longitude").toString())); } else { stmt.setNull(13, java.sql.Types.FLOAT); }
			stmt.setInt(14, 1);
			
			stmt.executeUpdate();
			ResultSet resultSet = stmt.getGeneratedKeys();
			if (resultSet.next()) {
                long id = resultSet.getLong(1);
                jsonArray = Location.getLocations("" + id);
            }
			
			
		} catch (SQLException e) {
			JSONObject obj = new JSONObject();
			obj.put("SQLException", e.getMessage());
			jsonArray.add(obj);
		}
		
		return jsonArray;
	}
	
	public static JSONArray getLocations(String id) {

		String returnStr = "";
		String where = "";
		
		String select = " SELECT * FROM locations l ";
		String join = " INNER JOIN location_types lt ON l.location_type_id = lt.location_type_id ";
		if (id != null) {
			where = " WHERE l.location_id = ? ";
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
				Location location = new Location();
				LocationType locationType = new LocationType();
				
				location.setLocation_id(resultSet.getInt(1));
				location.setShort_name(resultSet.getString(2));
				location.setLong_name(resultSet.getString(3));
				location.setDescription(resultSet.getString(4));
				location.setLocation_type_id(resultSet.getInt(5));
				location.setAddress_id(resultSet.getInt(6));
				location.setPrimary_object_id(resultSet.getInt(7));
				location.setScale_ft(resultSet.getDouble(8));
				locationType.setLocation_type_id(resultSet.getInt(16));
				locationType.setShort_name(resultSet.getString(17));
				
				JSONParser parser = new JSONParser();
				try {
					
					JSONObject locationJson = (JSONObject) parser.parse(location.getJSONString());
					JSONObject locationTypeJson = (JSONObject) parser.parse(locationType.getJSONString());
					
					locationJson.put("location_type", locationTypeJson);
					jsonArray.add(locationJson);
					
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
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
		jsonObject.put("location_id", location_id);
		jsonObject.put("short_name", short_name);
		jsonObject.put("long_name", long_name);
		jsonObject.put("description", description);
		jsonObject.put("location_type_id", location_type_id);
		jsonObject.put("address_id", address_id);
		jsonObject.put("primary_object_id", primary_object_id);
		jsonObject.put("scale_ft", scale_ft);
		jsonObject.put("min_x_coordinate", min_x_coordinate);
		jsonObject.put("min_y_coordinate", min_y_coordinate);
		jsonObject.put("max_x_coordinate", max_x_coordinate);
		jsonObject.put("max_y_coordinate", max_y_coordinate);
		jsonObject.put("latitude", latitude);
		jsonObject.put("longitude", longitude);
		jsonObject.put("active", active);
		
		return jsonObject.toJSONString();
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
	public int getLocation_type_id() {
		return location_type_id;
	}
	public void setLocation_type_id(int location_type_id) {
		this.location_type_id = location_type_id;
	}
	public int getAddress_id() {
		return address_id;
	}
	public void setAddress_id(int address_id) {
		this.address_id = address_id;
	}
	public int getPrimary_object_id() {
		return primary_object_id;
	}
	public void setPrimary_object_id(int primary_object_id) {
		this.primary_object_id = primary_object_id;
	}
	public LocationObject getPrimary_object() {
		return primary_object;
	}
	public void setPrimary_object(LocationObject primary_object) {
		this.primary_object = primary_object;
	}
	public double getScale_ft() {
		return scale_ft;
	}
	public void setScale_ft(double scale_ft) {
		this.scale_ft = scale_ft;
	}
	public int getMin_x_coordinate() {
		return min_x_coordinate;
	}
	public void setMin_x_coordinate(int min_x_coordinate) {
		this.min_x_coordinate = min_x_coordinate;
	}
	public int getMin_y_coordinate() {
		return min_y_coordinate;
	}
	public void setMin_y_coordinate(int min_y_coordinate) {
		this.min_y_coordinate = min_y_coordinate;
	}
	public int getMax_x_coordinate() {
		return max_x_coordinate;
	}
	public void setMax_x_coordinate(int max_x_coordinate) {
		this.max_x_coordinate = max_x_coordinate;
	}
	public int getMax_y_coordinate() {
		return max_y_coordinate;
	}
	public void setMax_y_coordinate(int max_y_coordinate) {
		this.max_y_coordinate = max_y_coordinate;
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
