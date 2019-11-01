package i_nav;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class Addresses implements INavEntity{
	
	private int address_id;
    private String address1;
    private String address2;
    private String city;
    private int state_id;
    private String zipcode;
    private String zipcode_ext;
	
    public Static String getAddresses(String id){
        //TODO
        return null;
    }
    @Override
	public String getJSONString() {
		//TODO
        return null;
	}
	
	public int getAddress_id(){
        return address_id;
    }
    public void setAddress_id(int address_id){
        this.address_id = address_id;
    }
    public int getState_id(){
        return state_id;
    }
    public void setState_id(int state_id){
        this.state_id = state_id;
    }
    public String getFirstAddress(){
        return address1;
    }
    public void setFirstAddress(String address1){
        this.address1 = address1;
    }
    public String getSecondAddress(){
        return address2;
    }
    public void setSecondAddress(String address2){
        this.address2 = address2;
    }
    public String getCity(){
        return city;
    }
    public void setCity(String city){
        this.city = city;
    }
    public String getZipcode(){
        return zipcode;
    }
    public void setZipcode(String zipcode){
        this.zipcode = zipcode;
    }
    public String getZipecode_ext(){
        return zipcode_ext;
    }
    public void setZipcode_ext(String zipcode_ext){
        this.zipcode_ext = zipcode_ext;
    }
}