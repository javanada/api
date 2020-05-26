package i_nav_model;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.amazonaws.services.apigateway.AmazonApiGateway;
import com.amazonaws.services.apigateway.AmazonApiGatewayClientBuilder;
import com.amazonaws.services.apigateway.model.ApiKey;
import com.amazonaws.services.apigateway.model.CreateApiKeyRequest;
import com.amazonaws.services.apigateway.model.CreateUsagePlanKeyRequest;
import com.amazonaws.services.apigateway.model.GetApiKeyRequest;
import com.amazonaws.services.apigateway.model.GetApiKeysRequest;
import com.amazonaws.services.apigateway.model.GetUsagePlanRequest;
import com.amazonaws.services.apigateway.model.UsagePlan;

/**
 * 
 * @author CSCD490 Team5
 * @version 1.0
 * 
 *
 */
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

	public static JSONArray updateUser(JSONObject userUpdate) {
		JSONArray JSONArr = new JSONArray();

		/*String update = "UPDATE `user` ";
		String set = " SET user_id = user_id";

		if (userUpdate.get("username") != null) {
			set += ", `username` = ? ";
		}
		if (userUpdate.get("salt") != null) {
			set += ", `salt` = ? ";
		}
		if (userUpdate.get("password") != null) {
			set += ", `password` = ? ";
		}
		if (userUpdate.get("first_name") != null) {
			set += ", `first_name` = ? ";
		}
		if (userUpdate.get("last_name") != null) {
			set += ", `last_name` = ? ";
		}
		if (userUpdate.get("email") != null) {
			set += ", `email` = ? ";
		}
		if (userUpdate.get("role_id") != null) {
			set += ", `role_id` = ? ";
		}

		String where = " WHERE `user_id` = ?";

		String query = update + set + where;

		try {
			Connection conn = DriverManager.getConnection(url, INavEntity.username, INavEntity.password);
			PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

			int counter = 1;

			if (userUpdate.get("username") != null){
				stmt.setString(counter++, userUpdate.get("username").toString());
			}
			if (userUpdate.get("salt") != null) {
				stmt.setString(counter++, userUpdate.get("salt").toString());
			}
			if (userUpdate.get("password") != null) {
				stmt.setString(counter++, userUpdate.get("password").toString());
			}
			if (userUpdate.get("first_name") != null) {
				stmt.setString(counter++, userUpdate.get("first_name").toString());
			}
			if (userUpdate.get("last_name") != null) {
				stmt.setString(counter++, userUpdate.get("last_name").toString());
			}
			if (userUpdate.get("email") != null) {
				stmt.setString(counter++, userUpdate.get("email").toString());
			}
			if (userUpdate.get("role_id") != null) {
				stmt.setInt(counter++, Integer.parseInt(userUpdate.get("role_id").toString()));
			}

			stmt.setInt(counter, Integer.parseInt(userUpdate.get("user_id").toString()));

			stmt.executeUpdate();
			ResultSet resultSet = stmt.getGeneratedKeys();

			if (resultSet.next()) {
				long id = resultSet.getLong(1);
				JSONArr = User.getUsers("" + id, null);
			}

		} catch (SQLException e) {
			JSONObject obj = new JSONObject();
			obj.put("SQLException", e.getMessage());
			JSONArr.add(obj);
		}*/

		return JSONArr;
	}
	public static JSONArray deleteUser(String id) {
		JSONArray JSONArr = new JSONArray();

		return JSONArr;
	}
	public User() {
		
	}
	
	public User(JSONObject jsonObject) {
		user_id = Integer.parseInt(jsonObject.get("user_id").toString());
		role_id = Integer.parseInt(jsonObject.get("role_id").toString());
		username = jsonObject.get("username").toString();
		first_name = jsonObject.get("first_name").toString();
		last_name = jsonObject.get("last_name").toString();
		email = jsonObject.get("email").toString();
		active = Boolean.parseBoolean(jsonObject.get("active").toString());
	}

	public static User getCurrentUser(String apiKey) {
		
//		AmazonApiGateway client = AmazonApiGatewayClientBuilder.standard().build();
//		GetApiKeyRequest req = new GetApiKeyRequest();
//		req.setApiKey(apiKey);
//		
//		String apiKeyUsername = client.getApiKey(req).getName();
//		JSONArray arr = getUsers(null, apiKeyUsername);
//		if (arr.size() == 1) {
//			JSONObject obj = (JSONObject)arr.get(0);
//			User u = new User(obj);
//			return u;
//		}
		
		return new User();
	}
	
	public static JSONArray getUsers(String id, String username, String subUsersId) {
		JSONArray jsonArray = new JSONArray();
		
		if ((id != null || username != null) && subUsersId != null) {
			return jsonArray;
		}
		
		String select = " SELECT u.user_id, u.username, u.first_name, u.last_name, u.email, u.role_id, u.active AS user_active, l.location_id, l.short_name, l.long_name, l.description, l.image, l.location_type_id, l.address_id, l.active AS location_active ";
		String from = " FROM users u ";
		String join = " LEFT JOIN users_locations ul ON u.user_id = ul.user_id ";
		join += " LEFT JOIN locations l ON l.location_id = ul.location_id "; 
		String where = " WHERE 1 ";
		
		String groupBy = " ";
		String orderBy = " ";
		
		if (subUsersId != null) {
			select = " SELECT u.user_id, u.username, u.first_name, u.last_name, u.email, u.role_id, u.active AS user_active ";
			from = " FROM i_nav.users AS u, ( " + 
					"    SELECT ul.location_id " + 
					"    FROM users_locations AS ul " + 
					"    WHERE ul.user_id = ? " + 
					" ) AS ul1, i_nav.users_locations AS ul2 ";
			join = " ";
			groupBy = " GROUP BY u.user_id ";
			orderBy = " ORDER BY u.user_id ";
		}
		
		if (id != null) {
			where += " AND u.user_id = ? ";
		}
		if (username != null) {
			where += " AND u.username = ? ";
		}
		if (subUsersId != null) {
			where += " AND ul2.user_id=u.user_id " + 
					" AND ul1.location_id=ul2.location_id ";
		}
		
		
		String query = select + from + join + where + groupBy + orderBy;
		
		
		int c = 1;
		
		Connection conn;
		try {
			conn = DriverManager.getConnection(url, INavEntity.username, INavEntity.password);
			PreparedStatement stmt = conn.prepareStatement(query);
			
			if (id != null) {
				stmt.setString(c++, id);
			}
			if (username != null) {
				stmt.setString(c++, username);
			}
			if (subUsersId != null) {
				stmt.setString(c++, subUsersId);
			}
			
			ResultSet resultSet = stmt.executeQuery();
			
			Map<String, List<Location>> userLocations = new HashMap<String, List<Location>>();
			Map<String, User> users = new HashMap<String, User>();
			JSONParser parser = new JSONParser();
			
			while (resultSet.next()) {
				
				User user = new User();
				user.setUser_id(resultSet.getInt("user_id"));
				user.setUsername(resultSet.getString("username"));
				user.setFirst_name(resultSet.getString("first_name"));
				user.setLast_name(resultSet.getString("last_name"));
				user.setEmail(resultSet.getString("email"));
				user.setRole_id(resultSet.getInt("role_id"));
				user.setActive(resultSet.getBoolean("user_active"));
				users.put("" + user.getUser_id(), user);
				
				if (subUsersId == null) {
					
					Location l = new Location();
					l.setLocation_id(resultSet.getInt("location_id"));
					l.setShort_name(resultSet.getString("short_name"));
					l.setLong_name(resultSet.getString("long_name"));
					l.setDescription(resultSet.getString("description"));
					l.setImage(resultSet.getString("image"));
					l.setActive(resultSet.getBoolean("location_active"));
					l.setAddress_id(resultSet.getInt("address_id"));
					l.setLocation_type_id(resultSet.getInt("location_type_id"));
					
					if (!userLocations.containsKey("" + user.getUser_id())) {
						userLocations.put("" + user.getUser_id(), new ArrayList<Location>());
					}
					if (resultSet.getInt("location_id") != 0) {
						userLocations.get("" + user.getUser_id()).add(l);
					}
				} else {
					if (!userLocations.containsKey("" + user.getUser_id())) {
						userLocations.put("" + user.getUser_id(), new ArrayList<Location>());
					}
				}
				
				
			}
			conn.close();
			
			for (String userId : userLocations.keySet()) {
				
				try {
					User user = users.get(userId);
					JSONObject userJson = (JSONObject) parser.parse(user.getJSONString());
					
					JSONArray locations = new JSONArray();
					for (Location location : userLocations.get(userId)) {
						JSONObject locationJson = (JSONObject) parser.parse(location.getJSONString());
						locations.add(locationJson);
					}
					userJson.put("locations", locations);
					
					jsonArray.add(userJson);
				}  catch (ParseException e) {
					JSONObject obj = new JSONObject();
					obj.put("ParseException", e.getMessage());
					obj.put("query", query);
					jsonArray.add(obj);
				}
				
			}
			

		} catch (SQLException e) {
			JSONObject obj = new JSONObject();
			obj.put("SQLException", e.getMessage());
			obj.put("query", query);
			jsonArray.add(obj);
		} 
		
		return jsonArray;
	}
	
	private static byte[] decodeHexString(String hexString) {
	    if (hexString.length() % 2 == 1) {
	        throw new IllegalArgumentException(
	          "Invalid hexadecimal String supplied.");
	    }
	     
	    byte[] bytes = new byte[hexString.length() / 2];
	    for (int i = 0; i < hexString.length(); i += 2) {
	        bytes[i / 2] = hexToByte(hexString.substring(i, i + 2));
	    }
	    return bytes;
	}
	private static byte hexToByte(String hexString) {
	    int firstDigit = toDigit(hexString.charAt(0));
	    int secondDigit = toDigit(hexString.charAt(1));
	    return (byte) ((firstDigit << 4) + secondDigit);
	}
	 
	private static int toDigit(char hexChar) {
	    int digit = Character.digit(hexChar, 16);
	    if(digit == -1) {
	        throw new IllegalArgumentException(
	          "Invalid Hexadecimal Character: "+ hexChar);
	    }
	    return digit;
	}
	
	public static JSONArray login(JSONObject loginUser) {
		
		JSONArray jsonArray = new JSONArray();
		
		String password = "";
		String hashedPasswordString = "";
		String saltString = "";
		String username = "";
		if (loginUser.get("password") != null) { // regex to validate username and password
			password = loginUser.get("password").toString();
		} else {
			return jsonArray;
		}
		
		if (loginUser.get("username") != null) { // regex to validate username and password
			username = loginUser.get("username").toString();
		}
		
		
		String select = " SELECT * FROM users u ";
		String join = "  ";
		String where = " WHERE u.username = ? ";
		String query = select + join + where;
		User user = new User();
		
		try {
			Connection conn = DriverManager.getConnection(url, INavEntity.username, INavEntity.password);
//			Statement stmt = conn.createStatement();
			PreparedStatement stmt = conn.prepareStatement(query);
			if (username != null) {
				stmt.setString(1, username);
			}
			ResultSet resultSet = stmt.executeQuery();
			
			while (resultSet.next()) {
				
				user.setUser_id(resultSet.getInt(1));
				user.setUsername(resultSet.getString(2));
				user.setSalt(resultSet.getString(3));
				user.setPassword(resultSet.getString(4));
				user.setFirst_name(resultSet.getString(5));
				user.setLast_name(resultSet.getString(6));
				user.setEmail(resultSet.getString(7));
				user.setRole_id(resultSet.getInt(8));
				user.setActive(resultSet.getBoolean(9));
				
			}

		} catch (SQLException e) {
			
		}

		
        MessageDigest md;
        try
        {
            // Select the message digest for the hash computation -> SHA-256
            md = MessageDigest.getInstance("SHA-256");

            // Generate the random salt
            SecureRandom random = new SecureRandom();
            byte[] salt = new byte[16];
            salt = decodeHexString(user.getSalt());

            // Passing the salt to the digest for the computation
            md.update(salt);

            // Generate the salted hash
            byte[] hashedPassword = md.digest(password.getBytes(StandardCharsets.UTF_8));

            StringBuilder sb = new StringBuilder();
            for (byte b : hashedPassword)
                sb.append(String.format("%02x", b));

//            System.out.println(sb);
            hashedPasswordString = sb.toString();
            
            if (hashedPasswordString.equals(user.getPassword())) { // login success
            	
            	AmazonApiGateway client = AmazonApiGatewayClientBuilder.standard().build();
				GetApiKeysRequest req = new GetApiKeysRequest();
				req.setIncludeValues(true);
				req.setNameQuery(user.getUsername());
				String apiKey = client.getApiKeys(req).getItems().get(0).getValue();
            	
//            	JSONObject obj = new JSONObject();
//    			obj.put("login", "success");
//    			jsonArray.add(obj);
    			
    			JSONParser parser = new JSONParser();
				try {
					
					JSONObject userJson = (JSONObject) parser.parse(user.getJSONString());
					userJson.put("x-api-key", apiKey);
					jsonArray.add(userJson);
					
				} catch (ParseException e) {
					JSONObject obj2 = new JSONObject();
					obj2.put("ParseException", e.getMessage());
					jsonArray.add(obj2);
				}
				
				
            } else {
            	JSONObject obj = new JSONObject();
    			obj.put("login", "fail");
    			jsonArray.add(obj);
            }
            
        } catch (NoSuchAlgorithmException e)
        {
        	JSONObject obj = new JSONObject();
			obj.put("NoSuchAlgorithmException", e.getMessage());
			jsonArray.add(obj);
			return jsonArray;
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
//			if (newUser.get("salt") != null) { stmt.setString(2, newUser.get("salt").toString()); } else { stmt.setNull(2, java.sql.Types.VARCHAR); }
//			if (newUser.get("password") != null) { stmt.setString(3, newUser.get("password").toString()); } else { stmt.setNull(3, java.sql.Types.VARCHAR); }
			if (newUser.get("first_name") != null) { stmt.setString(4, newUser.get("first_name").toString()); } else { stmt.setNull(4, java.sql.Types.VARCHAR); }
			if (newUser.get("last_name") != null) { stmt.setString(5, newUser.get("last_name").toString()); } else { stmt.setNull(5, java.sql.Types.VARCHAR); }
			if (newUser.get("email") != null) { stmt.setString(6, newUser.get("email").toString()); } else { stmt.setNull(6, java.sql.Types.VARCHAR); }
			if (newUser.get("role_id") != null) { stmt.setInt(7, Integer.parseInt(newUser.get("role_id").toString())); } else { stmt.setNull(7, java.sql.Types.INTEGER); }
			stmt.setInt(8, 1);
			
			String password = "";
			String hashedPasswordString = "";
			String saltString = "";
			String username = "";
			if (newUser.get("password") != null) { // regex to validate username and password
				password = newUser.get("password").toString();
			} else {
				return jsonArray;
			}
			
			if (newUser.get("username") != null) { // regex to validate username and password
				username = newUser.get("username").toString();
			}

			
	        MessageDigest md;
	        try
	        {
	            // Select the message digest for the hash computation -> SHA-256
	            md = MessageDigest.getInstance("SHA-256");

	            // Generate the random salt
	            SecureRandom random = new SecureRandom();
	            byte[] salt = new byte[16];
	            random.nextBytes(salt);

	            // Passing the salt to the digest for the computation
	            md.update(salt);

	            // Generate the salted hash
	            byte[] hashedPassword = md.digest(password.getBytes(StandardCharsets.UTF_8));

	            StringBuilder sb = new StringBuilder();
	            for (byte b : hashedPassword)
	                sb.append(String.format("%02x", b));

//	            System.out.println(sb);
	            hashedPasswordString = sb.toString();
	            
	            StringBuilder sb2 = new StringBuilder();
	            for (byte b : salt)
	            	sb2.append(String.format("%02x", b));
	            saltString = sb2.toString();
	            
	        } catch (NoSuchAlgorithmException e)
	        {
	        	JSONObject obj = new JSONObject();
				obj.put("NoSuchAlgorithmException", e.getMessage());
				jsonArray.add(obj);
				return jsonArray;
	        }
	        
	        stmt.setString(2, saltString);
	        stmt.setString(3, hashedPasswordString);
	        
			
			stmt.executeUpdate();
			ResultSet resultSet = stmt.getGeneratedKeys();
			
			if (resultSet.next()) { // success
				
//				AmazonApiGateway client = AmazonApiGatewayClientBuilder.standard().withRegion("my region").build();
				AmazonApiGateway client = AmazonApiGatewayClientBuilder.standard().build();
//				GetApiKeysRequest req = new GetApiKeysRequest();
				
				
				CreateApiKeyRequest request = new CreateApiKeyRequest();
				request.setName(username);
				request.setDescription("hi");
				
				GetUsagePlanRequest getUsagePlanRequest = new GetUsagePlanRequest();
	            getUsagePlanRequest.setUsagePlanId("hjwwwt");
				
				String uuid = UUID.randomUUID().toString();
				request.setValue(uuid);
				request.setEnabled(true);
				
				CreateUsagePlanKeyRequest createUsagePlanKeyRequest = new CreateUsagePlanKeyRequest()
	                    .withUsagePlanId("hjwwwt");

	            createUsagePlanKeyRequest.setKeyId(client.createApiKey(request).getId());
	            createUsagePlanKeyRequest.setKeyType("API_KEY");
	            client.createUsagePlanKey(createUsagePlanKeyRequest);
				
				
				
				
                long id = resultSet.getLong(1);
                jsonArray = User.getUsers("" + id, null, null);
                ((JSONObject) jsonArray.get(0)).put("x-api-key", uuid);
                
                JSONObject obj = new JSONObject();
				obj.put("API Key Request", request.toString());
				jsonArray.add(request.toString());
            }
			
			
		} catch (SQLException e) {
			JSONObject obj = new JSONObject();
			obj.put("SQLException", e.getMessage());
			jsonArray.add(obj);
		}
		
		return jsonArray;
	}
	
	public static void newCognitoUser(JSONObject newUser) {
		
		if (getUsers(null, newUser.get("username").toString(), null).size() != 0) {
			return;
		}
		
		String query = "INSERT INTO `users` (`username`, `first_name`, `last_name`, `email`, `role_id`, `active`)  " + 
				"VALUES (?, ?, ?, ?, ?, ?);";
		
		Connection conn;
		try {
			
			conn = DriverManager.getConnection(url, INavEntity.username, INavEntity.password);
			PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			
			if (newUser.get("username") != null) { stmt.setString(1, newUser.get("username").toString()); } else { stmt.setNull(1, java.sql.Types.VARCHAR); }
			if (newUser.get("first_name") != null) { stmt.setString(2, newUser.get("first_name").toString()); } else { stmt.setNull(2, java.sql.Types.VARCHAR); }
			if (newUser.get("last_name") != null) { stmt.setString(3, newUser.get("last_name").toString()); } else { stmt.setNull(3, java.sql.Types.VARCHAR); }
			if (newUser.get("email") != null) { stmt.setString(4, newUser.get("email").toString()); } else { stmt.setNull(4, java.sql.Types.VARCHAR); }
			if (newUser.get("role_id") != null) { stmt.setInt(5, Integer.parseInt(newUser.get("role_id").toString())); } else { stmt.setNull(5, java.sql.Types.INTEGER); }
			stmt.setInt(6, 1);
			
			stmt.executeUpdate();
			ResultSet resultSet = stmt.getGeneratedKeys();
			
			if (resultSet.next()) { // success
            }
			
			conn.close();
			
		} catch (SQLException e) {
		}
	}
	
	public String getJSONString() {
		return getJSON().toJSONString();
	}
	
	public JSONObject getJSON() {
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
		return jsonObject;
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
