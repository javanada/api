package i_nav;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class Roles implements INavEntity{
	
	private int role_id;
    private String short_name;
    private String long_name;
	private String description;

    public Static String getRoles(){
        //TODO
        return null;
    }
	@Override
	public String getJSONString() {
		//TODO
        return null;
	}
	
    public int getRole_id(){
        return role_id;
    }
    public void setRole_id(int role_id){
        this.role_id = role_id;
    }
    public String getShort_name(){
        return short_name;
    }
    public void setShort_name(String short_name){
        this.short_name = short_name;
    }
    public String getLong_name(){
        return long_name;
    }
    public void setLong_name(String long_name){
        this.long_name = long_name;
    }
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
    }
}