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

/**
 * 
 * @author CSCD490 Team5
 * @version 1.0
 * 
 *
 */
public class Address implements INavEntity {
	
	private int address_id;
	private String address1;
	private String address2;
	private String city;
	private String state;
	private String zipcode;
	private String zipcode_ext;

	public static JSONArray updateAddress(JSONObject updateAdd) {
		JSONArray JSONArr = new JSONArray();

		String update = "UPDATE `addresses` ";
		String set = " SET address_id = address_id";

		if (updateAdd.get("address1") != null) {
			set += ", `address1` = ? ";
		}
		if (updateAdd.get("address2") != null) {
			set += ", `address2` = ? ";
		}
		if (updateAdd.get("city") != null) {
			set += ", `city` = ? ";
		}
		if (updateAdd.get("state") != null) {
			set += ", `state` = ? ";
		}
		if (updateAdd.get("zipcode") != null) {
			set += ", `zipcode` = ? ";
		}
		if (updateAdd.get("zipcode_ext") != null) {
			set += ", `zipcode_ext` = ? ";
		}

		String where = " WHERE `address_id` = ?";

		String query = update + set + where;

		try {
			Connection conn = DriverManager.getConnection(url, username, password);
			PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

			int counter = 1;

			if (updateAdd.get("address1") != null){
				stmt.setString(counter++, updateAdd.get("address1").toString());
			}
			if (updateAdd.get("address2") != null) {
				stmt.setString(counter++, updateAdd.get("address2").toString());
			}
			if (updateAdd.get("city") != null) {
				stmt.setString(counter++, updateAdd.get("city").toString());
			}
			if (updateAdd.get("state") != null) {
				stmt.setString(counter++, updateAdd.get("state").toString());
			}
			if (updateAdd.get("zipcode") != null) {
				stmt.setString(counter++, updateAdd.get("zipcode").toString());
			}
			if (updateAdd.get("zipcode_ext") != null) {
				stmt.setString(counter++, updateAdd.get("zipcode_ext").toString());
			}

			stmt.setInt(counter, Integer.parseInt(updateAdd.get("address_id").toString()));

			stmt.executeUpdate();
			ResultSet resultSet = stmt.getGeneratedKeys();

			if (resultSet.next()) {
				long id = resultSet.getLong(1);
				JSONArr = Address.getAddresses("" + id);
			}

		} catch (SQLException e) {
			JSONObject obj = new JSONObject();
			obj.put("SQLException", e.getMessage());
			JSONArr.add(obj);
		}

		return JSONArr;
	}
	public static JSONArray deleteAddress(String id){//no active variable
		JSONArray jsonArr = new JSONArray();

		/*String update = "UPDATE `addresses` ";
		String set = "SET `active` = 0";
		String where = "";

		if (id != null) {
			where = " WHERE `address_id` = " + id;
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
		}*/

		return jsonArr;
	}
	
	public static JSONArray getAddresses(String id) {
		
		String select = " SELECT * FROM addresses a ";
		String join = "  ";
		String where = "  ";
		if (id != null) {
			where = " WHERE a.address_id = ? ";
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
				
				Address address = new Address();
				address.setAddress_id(resultSet.getInt(1));
				address.setAddress1(resultSet.getString(2));
				address.setAddress2(resultSet.getString(3));
				address.setCity(resultSet.getString(4));
				address.setState(resultSet.getString(5));
				address.setZipcode(resultSet.getString(6));
				address.setZipcode_ext(resultSet.getString(7));

				JSONParser parser = new JSONParser();
				try {
					
					JSONObject addressJson = (JSONObject) parser.parse(address.getJSONString());
					
					jsonArray.add(addressJson);
					
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
	
	public static JSONArray newAddress(JSONObject newAddress) {
		
		JSONArray jsonArray = new JSONArray();
		
		String query = "INSERT INTO `addresses` (`address1`, `address2`, `city`, `state`, `zipcode`, `zipcode_ext`)  " + 
				"VALUES (?, ?, ?, ?, ?, ?);";
		
		try {
			
			Connection conn = DriverManager.getConnection(url, username, password);
			PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			
			if (newAddress.get("address1") != null) { stmt.setString(1, newAddress.get("address1").toString()); } else { stmt.setNull(1, java.sql.Types.VARCHAR); }
			if (newAddress.get("address2") != null) { stmt.setString(2, newAddress.get("address2").toString()); } else { stmt.setNull(2, java.sql.Types.VARCHAR); }
			if (newAddress.get("city") != null) { stmt.setString(3, newAddress.get("city").toString()); } else { stmt.setNull(3, java.sql.Types.VARCHAR); }
			if (newAddress.get("state") != null) { stmt.setString(4, newAddress.get("state").toString()); } else { stmt.setNull(4, java.sql.Types.VARCHAR); }
			if (newAddress.get("zipcode") != null) { stmt.setString(5, newAddress.get("zipcode").toString()); } else { stmt.setNull(5, java.sql.Types.VARCHAR); }
			if (newAddress.get("zipcode_ext") != null) { stmt.setString(6, newAddress.get("zipcode_ext").toString()); } else { stmt.setNull(6, java.sql.Types.VARCHAR); }

			stmt.executeUpdate();
			ResultSet resultSet = stmt.getGeneratedKeys();
			if (resultSet.next()) {
                long id = resultSet.getLong(1);
                jsonArray = Address.getAddresses("" + id);
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
		jsonObject.put("address_id", address_id);
		jsonObject.put("address1", address1);
		jsonObject.put("address2", address2);
		jsonObject.put("city", city);
		jsonObject.put("state", state);
		jsonObject.put("zipcode", zipcode);
		jsonObject.put("zipcode_ext", zipcode_ext);
		
		return jsonObject.toJSONString();
	}

	public int getAddress_id() {
		return address_id;
	}

	public void setAddress_id(int address_id) {
		this.address_id = address_id;
	}

	public String getAddress1() {
		return address1;
	}

	public void setAddress1(String address1) {
		this.address1 = address1;
	}

	public String getAddress2() {
		return address2;
	}

	public void setAddress2(String address2) {
		this.address2 = address2;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getZipcode() {
		return zipcode;
	}

	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}

	public String getZipcode_ext() {
		return zipcode_ext;
	}

	public void setZipcode_ext(String zipcode_ext) {
		this.zipcode_ext = zipcode_ext;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}


}
