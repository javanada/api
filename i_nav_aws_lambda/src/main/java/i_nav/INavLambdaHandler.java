package i_nav;

import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProvider;
import com.amazonaws.services.cognitoidp.model.AdminGetUserRequest;
import com.amazonaws.services.cognitoidp.model.AdminGetUserResult;
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

/**
 * 
 * @author CSCD490 Team5
 * @version 1.0
 * 
 *
 */
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
//		String proxy = null;
		BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
		JSONObject responseJson = new JSONObject();
		String responseCode = "200";
		JSONObject event = null;
//		JSONObject pps = new JSONObject();

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

//			if (event.get("pathParameters") != null) {
//				pps = (JSONObject) event.get("pathParameters");
//				if (pps.get("proxy") != null) {
//					proxy = (String) pps.get("proxy");
//				}
//
//			}

		} catch (Exception pex) {
			responseJson.put("statusCode", "400");
			responseJson.put("exception", pex);
		}

		JSONObject responseBodyItem = new JSONObject();
		JSONArray responseBodyArray = new JSONArray();
//		responseBody.put("input", event.toJSONString());

		String entity = event.get("entity").toString();

		String requestBody = null;
		if (event.get("httpMethod").equals("POST")) {
			requestBody = event.get("requestBody").toString();
		}

		if (entity.equals("location")) {

			String locationId = ((JSONObject) event).get("id").toString();
			responseBodyArray = Location.getLocations(locationId, null);

		} else if (entity.equals("location/set-scale")) {

			String locationId = ((JSONObject) event).get("id").toString();
			responseBodyArray = Location.setLocationScale(locationId);

		} else if (entity.equals("location/new")) {

			try {
				responseBodyArray = Location.newLocation((JSONObject) parser.parse(requestBody));
			} catch (ParseException e) {
				JSONObject obj = new JSONObject();
				obj.put("parseException", e.getMessage());
				responseBodyArray.add(obj);
			}

		} else if (entity.equals("location/update")) {

			try {
				responseBodyArray = Location.updateLocation((JSONObject) parser.parse(requestBody));
			} catch (ParseException e) {
				JSONObject obj = new JSONObject();
				obj.put("parseExceptin", e.getMessage());
				responseBodyArray.add(obj);
			}
		} else if (entity.equals("location/delete")) {
			responseBodyArray = Location.deleteLocation(((JSONObject) event).get("id").toString());
		} else if (entity.equals("locations")) {

			responseBodyArray = Location.getLocations(null, null);

		} else if (entity.equals("objects")) {

			responseBodyArray = LocationObject.getLocationObjects(null, null, null);

		} else if (entity.equals("objects/location")) {

			String locationId = ((JSONObject) event).get("id").toString();
			responseBodyArray = LocationObject.getLocationObjects(null, locationId, null);

		} else if (entity.equals("object")) {

			String objectId = ((JSONObject) event).get("id").toString();
			responseBodyArray = LocationObject.getLocationObjects(objectId, null, null);

		} else if (entity.equals("object/update")) {

			try {
				responseBodyArray = LocationObject.updateObject((JSONObject) parser.parse(requestBody));
			} catch (ParseException e) {
				JSONObject obj = new JSONObject();
				obj.put("parseExceptin", e.getMessage());
				responseBodyArray.add(obj);
			}
		} else if (entity.equals("object/delete")) {
			responseBodyArray = LocationObject.deleteObject(((JSONObject) event).get("id").toString());
		} else if (entity.equals("object/new")) {

			try {
				responseBodyArray = LocationObject.newLocationObject((JSONObject) parser.parse(requestBody));
			} catch (ParseException e) {
				JSONObject obj = new JSONObject();
				obj.put("parseException", e.getMessage());
				responseBodyArray.add(obj);
			}

		} else if (entity.equals("objects/new")) {

			try {
				responseBodyArray = LocationObject.newLocationObjects((JSONArray) parser.parse(requestBody));
			} catch (ParseException e) {
				JSONObject obj = new JSONObject();
				obj.put("parseException", e.getMessage());
				responseBodyArray.add(obj);
			}

		} else if (entity.equals("object-types")) {

			responseBodyArray = LocationObjectType.getLocationObjectTypes(null);

		} else if (entity.equals("object-type")) {

			String objectTypeId = ((JSONObject) event).get("id").toString();
			responseBodyArray = LocationObjectType.getLocationObjectTypes(objectTypeId);

		} else if (entity.equals("object-type/new")) {

			try {
				responseBodyArray = LocationObjectType.newLocationObjectType((JSONObject) parser.parse(requestBody));
			} catch (ParseException e) {
				JSONObject obj = new JSONObject();
				obj.put("parseException", e.getMessage());
				responseBodyArray.add(obj);
			}

		} else if (entity.equals("object-type/update")) {

			try {
				responseBodyArray = LocationObjectType.updateObjLoc((JSONObject) parser.parse(requestBody));
			} catch (ParseException e) {
				JSONObject obj = new JSONObject();
				obj.put("parseExceptin", e.getMessage());
				responseBodyArray.add(obj);
			}
		} else if (entity.equals("location-types")) {

			responseBodyArray = LocationType.getLocationTypes(null);

		} else if (entity.equals("location-type")) {

			String locationTypeId = ((JSONObject) event).get("id").toString();
			responseBodyArray = LocationType.getLocationTypes(locationTypeId);

		} else if (entity.equals("location-type/update")) {

			try {
				responseBodyArray = LocationType.updateLocType((JSONObject) parser.parse(requestBody));
			} catch (ParseException e) {
				JSONObject obj = new JSONObject();
				obj.put("parseExceptin", e.getMessage());
				responseBodyArray.add(obj);
			}
		} else if (entity.equals("location-type/new")) {

			try {
				responseBodyArray = LocationType.newLocationType((JSONObject) parser.parse(requestBody));
			} catch (ParseException e) {
				JSONObject obj = new JSONObject();
				obj.put("parseException", e.getMessage());
				responseBodyArray.add(obj);
			}

		} else if (entity.equals("addresses")) {

			responseBodyArray = Address.getAddresses(null);

		} else if (entity.equals("address/update")) {

			try {
				responseBodyArray = Address.updateAddress((JSONObject) parser.parse(requestBody));
			} catch (ParseException e) {
				JSONObject obj = new JSONObject();
				obj.put("parseExceptin", e.getMessage());
				responseBodyArray.add(obj);
			}
		} else if (entity.equals("address/delete")) {
			responseBodyArray = Address.deleteAddress(((JSONObject) event).get("id").toString());
		} else if (entity.equals("address")) {

			String addressId = ((JSONObject) event).get("id").toString();
			responseBodyArray = Address.getAddresses(addressId);

		} else if (entity.equals("address/new")) {

			try {
				responseBodyArray = Address.newAddress((JSONObject) parser.parse(requestBody));
			} catch (ParseException e) {
				JSONObject obj = new JSONObject();
				obj.put("parseException", e.getMessage());
				responseBodyArray.add(obj);
			}

		} else if (entity.equals("roles")) {

			responseBodyArray = Role.getRoles(null);

		} else if (entity.equals("role")) {

			String roleId = ((JSONObject) event).get("id").toString();
			responseBodyArray = Role.getRoles(roleId);

		} else if (entity.equals("role/update")) {

			try {
				responseBodyArray = Role.updateRole((JSONObject) parser.parse(requestBody));
			} catch (ParseException e) {
				JSONObject obj = new JSONObject();
				obj.put("parseExceptin", e.getMessage());
				responseBodyArray.add(obj);
			}
		} else if (entity.equals("role/new")) {

			try {
				responseBodyArray = Role.newRole((JSONObject) parser.parse(requestBody));
			} catch (ParseException e) {
				JSONObject obj = new JSONObject();
				obj.put("parseException", e.getMessage());
				responseBodyArray.add(obj);
			}

		} else if (entity.equals("users")) {

			responseBodyArray = User.getUsers(null, null);

		} else if (entity.equals("user")) {

			String userId = ((JSONObject) event).get("id").toString();
			responseBodyArray = User.getUsers(userId, null);

		} else if (entity.equals("user/update")) {

			try {
				responseBodyArray = User.updateUser((JSONObject) parser.parse(requestBody));
			} catch (ParseException e) {
				JSONObject obj = new JSONObject();
				obj.put("parseExceptin", e.getMessage());
				responseBodyArray.add(obj);
			}
		} else if (entity.equals("user/new")) {

			try {
				responseBodyArray = User.newUser((JSONObject) parser.parse(requestBody));
			} catch (ParseException e) {
				JSONObject obj = new JSONObject();
				obj.put("parseException", e.getMessage());
				responseBodyArray.add(obj);
			}

		} else if (entity.equals("user/login")) {

			try {
				responseBodyArray = User.login((JSONObject) parser.parse(requestBody));
			} catch (ParseException e) {
				JSONObject obj = new JSONObject();
				obj.put("parseException", e.getMessage());
				responseBodyArray.add(obj);
			}

		} else if (entity.equals("edge/set-undirected")) { // undirected

			String sourceObjectId = ((JSONObject) event).get("source_object_id").toString();
			String sourceLocationId = ((JSONObject) event).get("source_location_id").toString();
			String destObjectId = ((JSONObject) event).get("dest_object_id").toString();
			String destLocationId = ((JSONObject) event).get("dest_location_id").toString();
			
			
			responseBodyArray = CloudGraphListUndirected.setEdgeUndirected(null, sourceObjectId, sourceLocationId,
					destObjectId, destLocationId);

		} else if (entity.equals("edges/location")) {

			String locationId = ((JSONObject) event).get("id").toString();
			responseBodyArray = CloudGraphListUndirected.getEdges(locationId);

		} else if (entity.equals("locations/parent")) {

			String parentId = ((JSONObject) event).get("id").toString();
			responseBodyArray = Location.getLocations(null, parentId);
			
		} else if (entity.equals("path/shortest-source-dest")) {

			String sourceObjectId = ((JSONObject) event).get("source_object_id").toString();
			String destObjectId = ((JSONObject) event).get("dest_object_id").toString();
			
			responseBodyArray = CloudGraphListUndirected.getShortestPath(sourceObjectId, destObjectId, true);
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
		responseJson.put("event", event);

		JSONObject headers = (JSONObject) event.get("headers");
		if (headers != null && headers.containsKey("x-api-key")) {
			responseJson.put("current_user", headers.get("x-api-key").toString());
//			responseJson.put("current_user", User.getCurrentUser(headers.get("x-api-key").toString()).getJSON());
		}

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
	
	public void authTrigger(InputStream inputStream, OutputStream outputStream, Context context) throws IOException {
//		LambdaLogger logger = context.getLogger();
		BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
		JSONObject event = null;
		try {
			event = (JSONObject) parser.parse(reader);
		} catch (Exception e) {
		}
		
		
		OutputStreamWriter writer = new OutputStreamWriter(outputStream, "UTF-8");
//		OutputStreamWriter writer = new OutputStreamWriter(outputStream);
		
		JSONObject eventRequestAttrs = ((JSONObject)((JSONObject)event.get("request")).get("userAttributes"));
		JSONObject newUser = new JSONObject();
		newUser.put("username", event.get("userName").toString());
		newUser.put("first_name", eventRequestAttrs.get("name").toString().split(" ")[0]);
		newUser.put("last_name", eventRequestAttrs.get("name").toString().split(" ")[1]);
		newUser.put("email", eventRequestAttrs.get("email").toString());
		newUser.put("role_id", "1");
		
		((JSONObject)event.get("response")).put("testuser", newUser);
//		logger.log(event.toJSONString());
		
		User.newCognitoUser(newUser);
		
		writer.write(event.toJSONString());
		writer.flush();
	}

//	public UserResponse getUserInfo(String username) {
//		 
//	       AWSCognitoIdentityProvider cognitoClient = getAmazonCognitoIdentityClient();             
//	AdminGetUserRequest userRequest = new AdminGetUserRequest()
//	                      .withUsername(username)
//	                      .withUserPoolId(cognitoConfig.getUserPoolId());
//	 
//	 
//	       AdminGetUserResult userResult = cognitoClient.adminGetUser(userRequest);
//	 
//	       UserResponse userResponse = new UserResponse();
//	       userResponse.setUsername(userResult.getUsername());
//	       userResponse.setUserStatus(userResult.getUserStatus());
//	       userResponse.setUserCreateDate(userResult.getUserCreateDate());
//	       userResponse.setLastModifiedDate(userResult.getUserLastModifiedDate());
//	 
//	       List userAttributes = userResult.getUserAttributes();
//	       for(AttributeTypeattribute: userAttributes) {
//	              if(attribute.getName().equals("custom:companyName")) {
//	                 userResponse.setCompanyName(attribute.getValue());
//	}else if(attribute.getName().equals("custom:companyPosition")) {
//	                 userResponse.setCompanyPosition(attribute.getValue());
//	              }else if(attribute.getName().equals("email")) {
//	                 userResponse.setEmail(attribute.getValue());
//	              }
//	       }
//	 
//	        cognitoClient.shutdown();
//	       return userResponse;
//	              
//	}

}