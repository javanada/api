package i_nav_model;

/**
 * 
 * @author CSCD490 Team5
 * @version 1.0
 * 
 *
 */
public interface INavEntity {
	
	String username = System.getenv("aws_username");
	String password = System.getenv("aws_password");
	String endpoint = System.getenv("aws_endpoint");
	String database = System.getenv("aws_database");
	
	String url = "jdbc:mysql://" + endpoint + ":3306/" + database;
	
	public String getJSONString();
	
}
