package i_nav;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class States implements INavEntity{
	
	private int state_id;
    private String short_name;
    private String long_name;
	
    public static String getStates(String id){
        //TODO
        return null;
    }
    @Override
	public String getJSONString() {
		//TODO
        return null;
	}
	
    public int getState_id() {
        return state_id;
    }
    public void setState_id(int state_id){
        this.state_id = state_id;
    }
    public String getShort_name(){
        return this.short_name;
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
}