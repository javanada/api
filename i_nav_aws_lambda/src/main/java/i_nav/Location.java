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
