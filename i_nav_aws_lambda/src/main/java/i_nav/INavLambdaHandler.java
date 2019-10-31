package i_nav;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Base64;

public class INavLambdaHandler implements RequestHandler<Object, Object> {

	public String getLocations(Context context) {

		String str = "";

		try {

//			String username = AWSEnvironment.decryptKey("username");
//			String password = AWSEnvironment.decryptKey("password");
//			String endpoint = AWSEnvironment.decryptKey("endpoint");
			
			String username = System.getenv("username");
			String password = System.getenv("password");
			String endpoint = System.getenv("endpoint");
			
			String url = "jdbc:mysql://" + endpoint + ":3306/i_nav";
			
			
			try {
				Connection conn = DriverManager.getConnection(url, username, password);
				
				Statement stmt = conn.createStatement();
				ResultSet resultSet = stmt.executeQuery("select * from locations");
//				str += resultSet.toString();
				
				while (resultSet.next()) {
					str += resultSet.getString(1);
					str += resultSet.getString(2);
					str += resultSet.getString(3);
					
				}
				
			} catch (SQLException e) {
				str += e.getMessage();
			}
			
		} catch (Exception e) {
//			str += e.getMessage() + "  <br />";
//			for (StackTraceElement s : e.getStackTrace()) {
//				str += s.toString() + "  <br />";
//			}
		}

		return str;

	}

	@Override
	public Object handleRequest(Object input, Context context) {
		return "hi " + getLocations(context);
	}

}