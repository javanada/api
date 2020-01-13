package i_nav;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * 
 * @author CSCD490 Team5
 * @version 1.0
 * 
 *
 */
public class Location implements INavEntity {
	
	private int location_id;
	private String short_name;
	private String long_name;
	private String description;
	private String image;
	private int location_type_id;
	private int address_id;
	private boolean active;
	
	public Location() {
	}
	
	public Location(JSONObject jsonObject) {
		location_id = Integer.parseInt(jsonObject.get("location_id").toString());
		short_name = jsonObject.get("short_name") != null ? jsonObject.get("short_name").toString() : "";
		long_name = jsonObject.get("long_name") != null ? jsonObject.get("long_name").toString() : "";
		description = jsonObject.get("description") != null ? jsonObject.get("description").toString() : "";
		image = jsonObject.get("image") != null ? jsonObject.get("image").toString() : "";
		location_type_id = Integer.parseInt(jsonObject.get("location_type_id") != null ? jsonObject.get("location_type_id").toString() : "");
		address_id = Integer.parseInt(jsonObject.get("address_id") != null ? jsonObject.get("address_id").toString() : "");
		active = Boolean.parseBoolean(jsonObject.get("active").toString());
	}

	
	public static JSONArray updateLocation(JSONObject updateLoc) {
		JSONArray JSONArr = new JSONArray();

		String update = "UPDATE `locations` ";
		String set = " SET location_id = location_id";

		if (updateLoc.get("short_name") != null) {
			set += ", `short_name` = ? ";
		}
		if (updateLoc.get("long_name") != null) {
			set += ", `long_name` = ? ";
		}
		if (updateLoc.get("description") != null) {
			set += ", `description` = ? ";
		}
		if (updateLoc.get("location_type_id") != null) {
			set += ", `location_type_id` = ? ";
		}
		if (updateLoc.get("address_id") != null) {
			set += ", `address_id` = ? ";
		}
		if (updateLoc.get("image") != null) {
			set += ", `image` = ? ";
		}

		String where = " WHERE `location_id` = ?";

		String query = update + set + where;

		try {
			Connection conn = DriverManager.getConnection(url, username, password);
			PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

			int counter = 1;

			if (updateLoc.get("short_name") != null){
				stmt.setString(counter++, updateLoc.get("short_name").toString());
			}
			if (updateLoc.get("long_name") != null) {
				stmt.setString(counter++, updateLoc.get("long_name").toString());
			}
			if (updateLoc.get("description") != null) {
				stmt.setString(counter++, updateLoc.get("description").toString());
			}
			if (updateLoc.get("location_type_id") != null) {
				stmt.setString(counter++, updateLoc.get("location_type_id").toString());
			}
			if (updateLoc.get("address_id") != null) {
				stmt.setString(counter++, updateLoc.get("address_id").toString());
			}
			if (updateLoc.get("image") != null) {
				stmt.setString(counter++, updateLoc.get("image").toString());
			}

			stmt.setInt(counter, Integer.parseInt(updateLoc.get("location_id").toString()));

			stmt.executeUpdate();
			ResultSet resultSet = stmt.getGeneratedKeys();

			if (resultSet.next()) {
				long id = resultSet.getLong(1);
				JSONArr = Location.getLocations("" + id, null);
			}

		} catch (SQLException e) {
			JSONObject obj = new JSONObject();
			obj.put("SQLException", e.getMessage());
			JSONArr.add(obj);
		}

		return JSONArr;
	}
	public static JSONArray deleteLocation(String id){
		JSONArray jsonArr = new JSONArray();

		String update = "UPDATE `locations` ";
		String set = "SET `active` = 0";
		String where = "";

		if (id != null) {
			where = " WHERE `location_id` = " + id;
		}

		String query = update + set + where;

		try {
			Connection conn = DriverManager.getConnection(url, username, password);
			PreparedStatement stmt = conn.prepareStatement(query);

			stmt.executeUpdate();

		} catch (SQLException e) {
			JSONObject obj = new JSONObject();
			obj.put("SQLException", e.getMessage());
			jsonArr.add(obj);
		}

		return jsonArr;
	}
  
	public static JSONArray newLocation(JSONObject newLocation) {
		
		JSONArray jsonArray = new JSONArray();
		
		String query = "INSERT INTO `locations` (`short_name`, `long_name`, `description`, `location_type_id`, `address_id`, `active`) " + 
				"VALUES (?, ?, ?, ?, ?, 1);";
		
		try {
			
			Connection conn = DriverManager.getConnection(url, username, password);
			PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			
			if (newLocation.get("short_name") != null) { stmt.setString(1, newLocation.get("short_name").toString()); } else { stmt.setNull(1, java.sql.Types.VARCHAR); }
			if (newLocation.get("long_name") != null) { stmt.setString(2, newLocation.get("long_name").toString()); } else { stmt.setNull(2, java.sql.Types.VARCHAR); }
			if (newLocation.get("description") != null) { stmt.setString(3, newLocation.get("description").toString()); } else { stmt.setNull(3, java.sql.Types.INTEGER); }
			if (newLocation.get("location_type_id") != null) { stmt.setInt(4, Integer.parseInt(newLocation.get("location_type_id").toString())); } else { stmt.setNull(4, java.sql.Types.INTEGER); }
			if (newLocation.get("address_id") != null) { stmt.setInt(5, Integer.parseInt(newLocation.get("address_id").toString())); } else { stmt.setNull(5, java.sql.Types.INTEGER); }
			
			
			stmt.executeUpdate();
			ResultSet resultSet = stmt.getGeneratedKeys();
			if (resultSet.next()) {
                long id = resultSet.getLong(1);
                jsonArray = Location.getLocations("" + id, null);
            }
			
			
		} catch (SQLException e) {
			JSONObject obj = new JSONObject();
			obj.put("SQLException", e.getMessage());
			jsonArray.add(obj);
		}
		
		return jsonArray;
	}
	
	public static JSONArray getLocations(String id, String parentId) {
		
		String select = 
					" SELECT " + 
					" l.location_id as location_location_id, l.short_name as location_short_name, l.long_name as location_long_name, l.description as location_description, l.image as canvas_image, " + 
					" lt.location_type_id, lt.short_name as location_type_short_name, lt.description as location_type_description ";
		
		String from = " FROM locations l ";
		String join = " INNER JOIN location_types lt ON l.location_type_id = lt.location_type_id ";
		String where = " WHERE 1 ";
		
		if (id != null) {
			where += " AND l.location_id = ? AND l.active = 1";
		}
		
		if (parentId != null) {
			from += " INNER JOIN location_relations lr ON lr.child_id = l.location_id ";
			where += " AND lr.parent_id = ? ";
		}
		
		String query = select + from + join + where;
		
		JSONArray jsonArray = new JSONArray();

		try {
			Connection conn = DriverManager.getConnection(url, username, password);
			PreparedStatement stmt = conn.prepareStatement(query);
			int c = 1;
			if (id != null) {
				stmt.setString(c++, id);
			}
			if (parentId != null) {
				stmt.setString(c++, parentId);
			}
			ResultSet resultSet = stmt.executeQuery();

			
			while (resultSet.next()) {
				Location location = new Location();
				LocationType locationType = new LocationType();
				
				location.setLocation_id(resultSet.getInt("location_location_id"));
				location.setShort_name(resultSet.getString("location_short_name"));
				location.setLong_name(resultSet.getString("location_long_name"));
				location.setDescription(resultSet.getString("location_description"));
				location.setImage(resultSet.getString("canvas_image"));
				
				locationType.setLocation_type_id(resultSet.getInt("location_type_id"));
				locationType.setShort_name(resultSet.getString("location_type_short_name"));
				locationType.setDescription(resultSet.getString("location_type_description"));
				
				JSONParser parser = new JSONParser();
				try {
					
					JSONObject locationJson = (JSONObject) parser.parse(location.getJSONString());
					JSONObject locationTypeJson = (JSONObject) parser.parse(locationType.getJSONString());
					
					locationJson.put("location_type", locationTypeJson);
					jsonArray.add(locationJson);
					
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
	
	public static JSONArray setLocationScale(String id) {
		JSONArray jsonArray = new JSONArray();
		
		// get primary and secondary objects for this location id
		JSONArray primaryArr = LocationObject.getLocationObjects(null, id, "4");
		JSONArray secondaryArr = LocationObject.getLocationObjects(null, id, "5");
		
		if (primaryArr.size() == 0 || secondaryArr.size() == 0) {
			JSONObject obj = new JSONObject();
			obj.put("Exception", "Primary and/or secondary objects do not exist");
			return jsonArray;
		}
		JSONObject primaryObj = (JSONObject)primaryArr.get(0);
		JSONObject secondaryObj = (JSONObject)secondaryArr.get(0);
		
		LocationObject primary = new LocationObject(primaryObj);
		LocationObject secondary = new LocationObject(secondaryObj);
		
		// calculate distance between primary and secondary lat and long and convert to feet or meters
		double primaryLat = Math.toRadians(primary.getLatitude());
		double primaryLong = Math.toRadians(primary.getLongitude());
		double secondaryLat = Math.toRadians(secondary.getLatitude());
		double secondaryLong = Math.toRadians(secondary.getLongitude());
		
		double R = 6369061; // radius of Earth (m) at Cheney, WA
		// todo: calculate exact radius by latitude
		
		double theta = (primaryLat + secondaryLat) / 2;
		double phi = (primaryLong + secondaryLong) / 2;
//		double dist_x = R * ((Math.sin(theta) * Math.cos(phi) * (primaryLat - secondaryLat)) - (Math.cos(theta) * Math.sin(phi) * (primaryLong - secondaryLong)));
//		double dist_y = R * ((Math.sin(theta) * Math.sin(phi) * (primaryLat - secondaryLat)) - (Math.cos(theta) * Math.cos(phi) * (primaryLong - secondaryLong)));
//		double dist_x = R * (Math.cos(secondaryLat) * Math.cos(secondaryLong) - Math.cos(primaryLat) * Math.cos(primaryLong));
//		double dist_y = R * (Math.cos(secondaryLat) * Math.sin(secondaryLong) - Math.cos(primaryLat) * Math.sin(primaryLong));
		double dist_x1 = R * (Math.cos(secondaryLat) * Math.cos(secondaryLong) - Math.cos(primaryLat) * Math.cos(primaryLong));
		double dist_x2 = R * (Math.cos(secondaryLat) * Math.sin(secondaryLong) - Math.cos(primaryLat) * Math.sin(primaryLong));
		double dist_x = Math.sqrt(dist_x2 * dist_x2 + dist_x1 * dist_x1); 
		
		double dist_y = R * (Math.sin(secondaryLat) - Math.sin(primaryLat));
		
		dist_x = Math.abs(dist_x) * 3.28084;
		dist_y = Math.abs(dist_y) * 3.28084;
		// there should be an x_scale and a y_scale
		// this distance is the scale
		// set secondary x and y to the max and location max_x and max_y
		
		JSONObject tempObj = new JSONObject();
		tempObj.put("dist_x", dist_x);
		tempObj.put("dist_y", dist_y);
		tempObj.put("primaryLat", primaryLat);
		tempObj.put("primaryLong", primaryLong);
		tempObj.put("secondaryLat", secondaryLat);
		tempObj.put("secondaryLong", secondaryLong);
//		tempObj.put("theta", theta);
//		tempObj.put("phi", phi);
		
		jsonArray.add(tempObj);
		
		double secondaryX = primaryLong < secondaryLong ? dist_x : -1 * dist_x;
		double secondaryY = primaryLat < secondaryLat ? dist_y : -1 * dist_y;
		
//		String query = "UPDATE locations SET max_x_coordinate = '" + dist_x + "', max_y_coordinate = '" + dist_y + "' WHERE location_id = ? ";
		String query2 = "UPDATE objects SET x_coordinate = '" + secondaryX + "', y_coordinate = '" + secondaryY + "' WHERE location_id = ? AND object_type_id = 5 ";
		try {
			Connection conn = DriverManager.getConnection(url, username, password);
	//		Statement stmt = conn.createStatement();
//			PreparedStatement stmt = conn.prepareStatement(query);
//			if (id != null) {
//				stmt.setString(1, id);
//			}
//			int result = stmt.executeUpdate();
			
			PreparedStatement stmt2 = conn.prepareStatement(query2);
			if (id != null) {
				stmt2.setString(1, id);
			}
			int result2 = stmt2.executeUpdate();
			
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
		jsonObject.put("location_id", location_id);
		jsonObject.put("short_name", short_name);
		jsonObject.put("long_name", long_name);
		jsonObject.put("description", description);
		jsonObject.put("canvas_image", image);
		jsonObject.put("location_type_id", location_type_id);
		jsonObject.put("address_id", address_id);
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
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

}
