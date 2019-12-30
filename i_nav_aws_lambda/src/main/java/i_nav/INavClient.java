package i_nav;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class INavClient {
	
	public static JSONArray get(String resource) {
		
		String baseUrl = "https://6ifyh4p4z2.execute-api.us-west-2.amazonaws.com/dev";
		try {
			
			URL urlObj = new URL(baseUrl + "/" + resource);
			URLConnection urlCon = urlObj.openConnection();
			urlCon.setRequestProperty("Content-Type", "application/json");
			urlCon.setRequestProperty("x-api-key", "Il5Hx547OB3VWglNlnYM35XJL4sv1ok57bJakZav");
			urlCon.setRequestProperty("Authorization", "");
			InputStream inputStream = urlCon.getInputStream();
			BufferedInputStream reader = new BufferedInputStream(inputStream);
			
			byte[] contents = new byte[1024];
			int bytesRead = 0;
			String jsonStr = "";
			while((bytesRead = reader.read(contents)) != -1) { 
				jsonStr += new String(contents, 0, bytesRead);              
			}
			
			JSONParser parser = new JSONParser();
			JSONObject object = (JSONObject) parser.parse(jsonStr);
			JSONObject body = (JSONObject) object.get("body");
			JSONArray arr = (JSONArray) body.get("data");
//			System.out.println("data: " + arr.toJSONString());
			return arr;
			
		} catch (Exception e) {
			System.out.println("*******-------****** Error: " + e.getMessage());
			e.printStackTrace();
		}
		
		return new JSONArray();
	}
}
