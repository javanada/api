package i_nav;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class Users implements INavEntity{
	
	private int user_id;
	private String first_name;
	private String last_name;
	private String password;
    private String username;
    private String salt;
    private String email;
    private int role_id;
    private int location_id;
	private boolean active;
    
    public Static String getUsers(String id){
        //TODO
        return null;
    }
	@Override
	public String getJSONString() {
		//TODO
        return null;
	}
	
    public int getUser_id() {
        return user_id;
    }
    public void setUser_id(int user_id){
        this.user_id = user_id;
    }
    public int getRole_id(){
        return role_id;
    }
    public int setRole_id(int role_id){
        this.role_id = role_id;
    }
	public int getLocation_id() {
		return location_id;
	}
	public void setLocation_id(int location_id) {
		this.location_id = location_id;
	}
	public String getFirst_name(){
        return first_name;
    }
    public void setFirst_name(String first_name){
        this.first_name = first_name;
    }
	public String getLast_name(){
        return last_name;
    }
    public void setLast_name(String last_name){
        this.last_name = last_name;
    }
    public String getPassword(){
        return password;
    }
    public void setPassword(String password){
        this.password = password;
    }
    public String getSalt(){
        return salt;
    }
    public void setSalt(String salt){
        this.salt = salt;
    }
    public String getEmail(){
        return email;
    }
    public void setEmail(String email){
        this.email = email;
    }
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
}