package i_nav;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class Address implements INavEntity {
	
	private int address_id;
	private String address1;
	private String address2;
	private String city;
	private String state;
	private String zipcode;
	private String zipcode_ext;
	
	public static JSONArray getAddresses(String id) {
		return null;
	}
	
	public static JSONArray newAddress(JSONObject newAddress) {
		return null;
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
