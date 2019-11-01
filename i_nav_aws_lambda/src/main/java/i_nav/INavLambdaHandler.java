package i_nav;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.LambdaLogger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Base64;
import com.amazonaws.services.lambda.runtime.RequestStreamHandler;
import com.mysql.cj.xdevapi.JsonString;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class INavLambdaHandler implements RequestStreamHandler {
//public class INavLambdaHandler implements RequestHandler<Request, Object> {

	JSONParser parser = new JSONParser();

//	@Override
//	public Object handleRequest(Request request, Context context) {
//		return "hi " + getLocations(context) + " [[" + request.getPathParameters().keySet().size() + "]]";
//	}

	@SuppressWarnings("unchecked")
	@Override
	public void handleRequest(InputStream inputStream, OutputStream outputStream, Context context) throws IOException {
		String proxy = null;
		BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
		JSONObject responseJson = new JSONObject();
		String responseCode = "200";
		JSONObject event = null;
		JSONObject pps = new JSONObject();
		
		
//		OutputStreamWriter writer = new OutputStreamWriter(outputStream, "UTF-8");
//		String str = reader.readLine();
//		while (str != null) {
//			writer.write(str);
//			str = reader.readLine();
//		}
//		
//		try {
//			JSONObject event2 = (JSONObject) parser.parse(reader);
//			writer.write(event2.toJSONString());
//		} catch (ParseException e) {
//			writer.write(e.getMessage());
//		}
		
		
		try {
			event = (JSONObject) parser.parse(reader);
			if (event.get("pathParameters") != null) {
				pps = (JSONObject) event.get("pathParameters");
				if (pps.get("proxy") != null) {
					proxy = (String) pps.get("proxy");
				}

			}
		} catch (Exception pex) {
			responseJson.put("statusCode", "400");
			responseJson.put("exception", pex);
		}

		

		JSONObject responseBodyItem = new JSONObject();
		JSONArray responseBodyArray = new JSONArray();
//		responseBody.put("input", event.toJSONString());
		
		
		String entity = event.get("entity").toString();
		String queryResultJson = "[]";
		
		if (entity.equals("location")) {
			
			String locationId = ((JSONObject)event).get("id").toString();
			responseBodyArray = Location.getLocations(locationId);
			
//			try {
//				responseBodyItem = (JSONObject) parser.parse(queryResultJson);
//				responseJson.put("body", responseBodyItem.toJSONString());
//			} catch (ParseException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
			
		} else if (entity.equals("locations")) {
			
			responseBodyArray = Location.getLocations(null);
			
		}
		
//		try {
//			responseBodyArray = (JSONArray) parser.parse(queryResultJson);
//		} catch (ParseException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
		JSONObject responseBody = new JSONObject();
//		responseBody.put("input", event.toJSONString());
		responseBody.put("data", responseBodyArray);
		
		responseJson.put("body", responseBody);

		JSONObject headerJson = new JSONObject();
		headerJson.put("x-custom-header", "my custom header value");
		headerJson.put("Access-Control-Allow-Origin", "*");

		responseJson.put("isBase64Encoded", false);
		responseJson.put("statusCode", responseCode);
		responseJson.put("headers", headerJson);
		

		OutputStreamWriter writer = new OutputStreamWriter(outputStream, "UTF-8");
		
		writer.write(responseJson.toJSONString());
		writer.close();

	}

}