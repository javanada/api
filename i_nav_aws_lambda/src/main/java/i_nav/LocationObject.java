package i_nav;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

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
	private int image_x;
	private int image_y;
	private double latitude;
	private double longitude;
	private boolean active;

	public static JSONArray updateObject(JSONObject updateObj) {
		JSONArray JSONArr = new JSONArray();

		String update = "UPDATE `objects` ";
		String set = " SET object_id = object_id";

		if (updateObj.get("short_name") != null) {
			set += ", `short_name` = ? ";
		}
		if (updateObj.get("long_name") != null) {
			set += ", `long_name` = ? ";
		}
		if (updateObj.get("description") != null) {
			set += ", `description` = ? ";
		}
		if (updateObj.get("object_type_id") != null) {
			set += ", `object_type_id` = ? ";
		}
		if (updateObj.get("location_id") != null) {
			set += ", `location_id` = ? ";
		}
//		if (updateObj.get("x_coordinate") != null) {
//			set += ", `x_coordinate` = ? ";
//		}
//		if (updateObj.get("y_coordinate") != null) {
//			set += ", `y_coordinate` = ? ";
//		}
//		if (updateObj.get("image_x") != null) {
//			set += ", `image_x` = ? ";
//		}
//		if (updateObj.get("image_y") != null) {
//			set += ", `image_y` = ? ";
//		}
		if (updateObj.get("latitude") != null) {
			set += ", `latitude` = ? ";
		}
		if (updateObj.get("longitude") != null) {
			set += ", `longitude` = ? ";
		}


		String where = " WHERE `object_id` = ?";

		String query = update + set + where;

		try {
			Connection conn = DriverManager.getConnection(url, username, password);
			PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

			int counter = 1;

			if (updateObj.get("short_name") != null){
				stmt.setString(counter++, updateObj.get("short_name").toString());
			}
			if (updateObj.get("long_name") != null) {
				stmt.setString(counter++, updateObj.get("long_name").toString());
			}
			if (updateObj.get("description") != null) {
				stmt.setString(counter++, updateObj.get("description").toString());
			}
			if (updateObj.get("object_type_id") != null) {
				stmt.setString(counter++, updateObj.get("object_type_id").toString());
			}
			if (updateObj.get("location_id") != null) {
				stmt.setString(counter++, updateObj.get("location_id").toString());
			}
//			if (updateObj.get("x_coordinate") != null) {
//				stmt.setString(counter++, updateObj.get("x_coordinate").toString());
//			}
//			if (updateObj.get("y_coordinate") != null) {
//				stmt.setString(counter++, updateObj.get("y_coordinate").toString());
//			}
//			if (updateObj.get("image_x") != null) {
//				stmt.setString(counter++, updateObj.get("image_x").toString());
//			}
//			if (updateObj.get("image_y") != null) {
//				stmt.setString(counter++, updateObj.get("image_y").toString());
//			}
			if (updateObj.get("latitude") != null) {
				stmt.setString(counter++, updateObj.get("latitude").toString());
			}
			if (updateObj.get("longitude") != null) {
				stmt.setString(counter++, updateObj.get("longitude").toString());
			}

			stmt.setInt(counter, Integer.parseInt(updateObj.get("object_id").toString()));
			stmt.executeUpdate();
			
			JSONArr = LocationObject.getLocationObjects(updateObj.get("object_id").toString(), null, null);
			

		} catch (SQLException e) {
			JSONObject obj = new JSONObject();
			obj.put("SQLException", e.getMessage());
			JSONArr.add(obj);
		}

		return JSONArr;
	}
	public static JSONArray deleteObject(String id){ // need to update the graph here
		JSONArray jsonArr = new JSONArray();

		String update = "UPDATE `objects` ";
		String set = "SET `active` = 0";
		String where = "";

		if (id != null) {
			where = " WHERE `object_id` =  ? ";
		}

		String query = update + set + where;

		try {
			Connection conn = DriverManager.getConnection(url, username, password);
			PreparedStatement stmt = conn.prepareStatement(query);
			
			if (id != null) {
				stmt.setString(1, id);
			}

			stmt.executeUpdate();
			
			CloudGraphListUndirected.removeVertex(null, id);
			

		} catch (SQLException e) {
			JSONObject obj = new JSONObject();
			obj.put("SQLException", e.getMessage());
			jsonArr.add(obj);
		}

		return jsonArr;
	}
	public LocationObject() {
		
	}
	public LocationObject(JSONObject jsonObject) {
		
		object_id = Integer.parseInt(jsonObject.get("object_id") != null ? jsonObject.get("object_id").toString() : "0");
		location_id = Integer.parseInt(jsonObject.get("location_id") != null ? jsonObject.get("location_id").toString() : "0");
		short_name = jsonObject.get("short_name") != null ? jsonObject.get("short_name").toString() : "";
		long_name = jsonObject.get("long_name") != null ? jsonObject.get("long_name").toString() : "";
		description = jsonObject.get("description") != null ? jsonObject.get("description").toString() : "";
		object_type_id = Integer.parseInt(jsonObject.get("object_type_id") != null ? jsonObject.get("object_type_id").toString() : "0");
		x_coordinate = Integer.parseInt(jsonObject.get("x_coordinate") != null ? jsonObject.get("x_coordinate").toString() : "0");
		y_coordinate = Integer.parseInt(jsonObject.get("y_coordinate") != null ? jsonObject.get("y_coordinate").toString() : "0");
		image_x = Integer.parseInt(jsonObject.get("image_x") != null ? jsonObject.get("image_x").toString() : "0");
		image_y = Integer.parseInt(jsonObject.get("image_y") != null ? jsonObject.get("image_y").toString() : "0");
		latitude = Double.parseDouble(jsonObject.get("latitude") != null ? jsonObject.get("latitude").toString() : "0");
		longitude = Double.parseDouble(jsonObject.get("longitude") != null ? jsonObject.get("longitude").toString() : "0");
		active = Boolean.parseBoolean(jsonObject.get("active") != null ? jsonObject.get("active").toString() : "false");
		
	}
	
	public static JSONArray newLocationObjects(JSONArray newLocationObjects) {
		String locationId = null;
		JSONArray arr = new JSONArray();
		for (Object obj : newLocationObjects) {
			JSONObject jsonObject = (JSONObject) obj;
			if (jsonObject.get("location_id") != null) {
				locationId = jsonObject.get("location_id").toString();
			}
			JSONArray arrEach = newLocationObject(jsonObject);
			if (arrEach.size() == 1) {
				JSONObject each = (JSONObject) arrEach.get(0);
				each.put("connected", jsonObject.get("connected"));
				arr.add(each);
			}
		}
		
		CloudGraphListUndirected graph = new CloudGraphListUndirected("i_nav_graph1", true);			
		graph.getPoints(locationId);
		
		ArrayList<String> alreadyConnected = new ArrayList<String>();
		
		for (Object obj : arr) {
			JSONObject jsonObject = (JSONObject) obj;
			LocationObject o = new LocationObject(jsonObject);
			
			// important: do this with only calling getPoints ONCE, not each time
			
			for (Object obj2 : arr) {
				
				JSONObject jsonObject2 = (JSONObject) obj2;
				LocationObject o2 = new LocationObject(jsonObject2);
				
				if (jsonObject.get("connected") != null && jsonObject.get("connected").equals(true) &&  
					jsonObject2.get("connected") != null && jsonObject2.get("connected").equals(true) && 
					!obj2.equals(obj) && 
					!alreadyConnected.contains("" + o.getObject_id() + o2.getObject_id()) && 
					!alreadyConnected.contains("" + o2.getObject_id() + o.getObject_id())
					) {
						
						alreadyConnected.add("" + o.getObject_id() + o2.getObject_id());
						alreadyConnected.add("" + o2.getObject_id() + o.getObject_id());
						CloudGraphListUndirected.setEdgeUndirected(graph, "" + o.getObject_id(), "" + o.getLocation_id(), "" + o2.getObject_id(), "" + o2.getLocation_id());
						
				}
				
			}
			
		}
		
		return newLocationObjects;
	}
	
	
	
	public static JSONArray newLocationObject(JSONObject newLocationObject) { // would be faster if it re-uses the same graph reference
		JSONArray jsonArray = new JSONArray();
		
		String query = "INSERT INTO `objects` (`location_id`, `short_name`, `long_name`, `description`, `object_type_id`, `x_coordinate`, `y_coordinate`,  `image_x`, `image_y`, `latitude`, `longitude`, `active`) " + 
				"VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
		
		try {
			
			Connection conn = DriverManager.getConnection(url, username, password);
			PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			
			if (newLocationObject.get("location_id") != null) { stmt.setInt(1, Integer.parseInt(newLocationObject.get("location_id").toString())); } else { stmt.setNull(1, java.sql.Types.INTEGER); }
			if (newLocationObject.get("short_name") != null) { stmt.setString(2, newLocationObject.get("short_name").toString()); } else { stmt.setNull(2, java.sql.Types.VARCHAR); }
			if (newLocationObject.get("long_name") != null) { stmt.setString(3, newLocationObject.get("long_name").toString()); } else { stmt.setNull(3, java.sql.Types.VARCHAR); }
			if (newLocationObject.get("description") != null) { stmt.setString(4, newLocationObject.get("description").toString()); } else { stmt.setNull(4, java.sql.Types.INTEGER); }
			if (newLocationObject.get("object_type_id") != null) { stmt.setInt(5, Integer.parseInt(newLocationObject.get("object_type_id").toString())); } else { stmt.setNull(5, java.sql.Types.INTEGER); }
			if (newLocationObject.get("image_x") != null) { stmt.setInt(8, Integer.parseInt(newLocationObject.get("image_x").toString())); } else { stmt.setNull(8, java.sql.Types.INTEGER); }
			if (newLocationObject.get("image_y") != null) { stmt.setInt(9, Integer.parseInt(newLocationObject.get("image_y").toString())); } else { stmt.setNull(9, java.sql.Types.INTEGER); }
			if (newLocationObject.get("latitude") != null) { stmt.setDouble(10, Double.parseDouble(newLocationObject.get("latitude").toString())); } else { stmt.setNull(10, java.sql.Types.FLOAT); }
			if (newLocationObject.get("longitude") != null) { stmt.setDouble(11, Double.parseDouble(newLocationObject.get("longitude").toString())); } else { stmt.setNull(11, java.sql.Types.FLOAT); }
			stmt.setInt(12, 1);
			
			// calculate x and y coordinates relative to primary object
			JSONArray primaryArr = LocationObject.getLocationObjects(null, newLocationObject.get("location_id").toString(), "4");
			JSONArray secondaryArr = LocationObject.getLocationObjects(null, newLocationObject.get("location_id").toString(), "5");
			
			if (primaryArr.size() == 1 && secondaryArr.size() == 1) {
				LocationObject primary = new LocationObject((JSONObject) primaryArr.get(0));
				LocationObject secondary = new LocationObject((JSONObject) secondaryArr.get(0));
				
				int delta_x = secondary.getImage_x() - primary.getImage_x();
				double x_scale = (double) secondary.getX_coordinate() / (double) delta_x;
				
				int delta_y = secondary.getImage_y() - primary.getImage_y();
				double y_scale = (double) secondary.getY_coordinate() / (double) delta_y;
				
				int x_coord = (int) ((Integer.parseInt(newLocationObject.get("image_x").toString()) - primary.getImage_x()) * x_scale);
				int y_coord = (int) ((Integer.parseInt(newLocationObject.get("image_y").toString()) - primary.getImage_y()) * y_scale);
				
				stmt.setInt(6, Integer.parseInt("" + x_coord));
				stmt.setInt(7, Integer.parseInt("" + y_coord));
				
			} else {
				if (newLocationObject.get("x_coordinate") != null) { stmt.setInt(6, Integer.parseInt(newLocationObject.get("x_coordinate").toString())); } else { stmt.setNull(6, java.sql.Types.INTEGER); }
				if (newLocationObject.get("y_coordinate") != null) { stmt.setInt(7, Integer.parseInt(newLocationObject.get("y_coordinate").toString())); } else { stmt.setNull(7, java.sql.Types.INTEGER); }
			}
			
			stmt.executeUpdate();
			ResultSet resultSet = stmt.getGeneratedKeys();
			if (resultSet.next()) {
                long id = resultSet.getLong(1);
                jsonArray = LocationObject.getLocationObjects("" + id, null, null);
                
                if (jsonArray.size() > 0) {
	                CloudGraphListUndirected graph1 = new CloudGraphListUndirected("i_nav_graph1", true);
	                graph1.setVertex((JSONObject)jsonArray.get(0));
                }
            }
			
			
		} catch (SQLException e) {
			JSONObject obj = new JSONObject();
			obj.put("SQLException", e.getMessage());
			jsonArray.add(obj);
		}
		
		return jsonArray;
	}
	
	public static JSONArray getLocationObjects(String id, String locationId, String objectTypeId) {
		String returnStr = "";
		String where = "";
		
		String select = " SELECT " + 
						" o.object_id, o.short_name as object_short_name, o.long_name as object_long_name, o.description as object_description, o.x_coordinate, o.y_coordinate, o.latitude, o.longitude, o.image_x, o.image_y, " + 
						" l.location_id as location_location_id, l.short_name as location_short_name, l.long_name as location_long_name, l.description as location_description, l.image as canvas_image, " + 
						" lt.location_type_id, lt.short_name as location_type_short_name, lt.description as location_type_description, " + 
						" ot.object_type_id, ot.short_name as object_type_short_name,  ot.description as object_type_description, ot.image as object_type_image," + 
						" a.address_id, a.address1, a.address2, a.city, a.state, a.zipcode, a.zipcode_ext "
				;
		
		String from = " FROM objects o ";
		String join = 
					" INNER JOIN locations l ON l.location_id = o.location_id " + 
					" INNER JOIN location_types lt on l.location_type_id = lt.location_type_id " + 
					" INNER JOIN addresses a on a.address_id = l.address_id " + 
					" INNER JOIN object_types ot on ot.object_type_id = o.object_type_id "
				;
		
		if (id != null) {
			where = " WHERE o.object_id = ? ";
		}
		if (locationId != null) {
			where += " AND l.location_id = ? ";
		}
		if (objectTypeId != null) {
			where += " AND ot.object_type_id = ? ";
		}
		where += "  AND o.active = 1  ";
		String query = select + from +  join + where;
		
		
		
		JSONArray jsonArray = new JSONArray();
		boolean primarySet = false, secondarySet = false;

		try {
			Connection conn = DriverManager.getConnection(url, username, password);
//			Statement stmt = conn.createStatement();
			PreparedStatement stmt = conn.prepareStatement(query);
			
			int c = 1;
			if (id != null) {
				stmt.setString(c++, id);
			}
			if (locationId != null) {
				stmt.setString(c++, locationId);
			}
			if (objectTypeId != null) {
				stmt.setString(c++, objectTypeId);
			}
			ResultSet resultSet = stmt.executeQuery();

			
			
			while (resultSet.next()) {
				LocationObject locationObject = new LocationObject();
				Location location = new Location();
				LocationType locationType = new LocationType();
				Address address = new Address();
				LocationObjectType locationObjectType = new LocationObjectType();
				
				location.setLocation_id(resultSet.getInt("location_location_id"));
				location.setShort_name(resultSet.getString("location_short_name"));
				location.setLong_name(resultSet.getString("location_long_name"));
				location.setDescription(resultSet.getString("location_description"));
				location.setImage(resultSet.getString("canvas_image"));
				location.setActive(true);
				location.setAddress_id(resultSet.getInt("address_id"));
				
				locationObject.setObject_id(resultSet.getInt("object_id"));
				locationObject.setLocation_id(resultSet.getInt("location_location_id"));
				locationObject.setShort_name(resultSet.getString("object_short_name"));
				locationObject.setLong_name(resultSet.getString("object_long_name"));
				locationObject.setDescription(resultSet.getString("object_description"));
				locationObject.setX_coordinate(resultSet.getInt("x_coordinate"));
				locationObject.setY_coordinate(resultSet.getInt("y_coordinate"));
				locationObject.setImage_x(resultSet.getInt("image_x"));
				locationObject.setImage_y(resultSet.getInt("image_y"));
				locationObject.setLatitude(resultSet.getDouble("latitude"));
				locationObject.setLongitude(resultSet.getDouble("longitude"));
				locationObject.setObject_type_id(resultSet.getInt("object_type_id"));
				locationObject.setActive(true);
				
				if (locationObject.getObject_type_id() == 4) {
					primarySet = true;
				} else if (locationObject.getObject_type_id() == 5) {
					secondarySet = true;
				}
				
				locationObjectType.setObject_type_id(resultSet.getInt("object_type_id"));
				locationObjectType.setShort_name(resultSet.getString("object_type_short_name"));
				locationObjectType.setDescription(resultSet.getString("object_type_description"));
				locationObjectType.setImage(resultSet.getString("object_type_image"));
				
				locationType.setLocation_type_id(resultSet.getInt("location_type_id"));
				locationType.setShort_name(resultSet.getString("location_type_short_name"));
				locationType.setDescription(resultSet.getString("location_type_description"));
				
				address.setAddress_id(resultSet.getInt("address_id"));
				address.setAddress1(resultSet.getString("address1"));
				address.setAddress2(resultSet.getString("address2"));
				address.setCity(resultSet.getString("City"));
				address.setState(resultSet.getString("state"));
				address.setZipcode(resultSet.getString("zipcode"));
				address.setZipcode_ext(resultSet.getString("zipcode_ext"));
				
				JSONParser parser = new JSONParser();
				try {
					
					JSONObject locationJson = (JSONObject) parser.parse(location.getJSONString());
					JSONObject locationObjectJson = (JSONObject) parser.parse(locationObject.getJSONString());
					JSONObject locationObjectTypeJson = (JSONObject) parser.parse(locationObjectType.getJSONString());
					JSONObject locationTypeJson = (JSONObject) parser.parse(locationType.getJSONString());
					JSONObject addressJson = (JSONObject) parser.parse(address.getJSONString());
					
					locationObjectJson.put("location_type", locationTypeJson);
					locationObjectJson.put("location", locationJson);
					locationObjectJson.put("address", addressJson);
					locationObjectJson.put("object_type", locationObjectTypeJson);
					
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
		jsonObject.put("image_x", image_x);
		jsonObject.put("image_y", image_y);
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
	public int getImage_x() {
		return image_x;
	}
	public void setImage_x(int image_x) {
		this.image_x = image_x;
	}
	public int getImage_y() {
		return image_y;
	}
	public void setImage_y(int image_y) {
		this.image_y = image_y;
	}
	
	
	
}
