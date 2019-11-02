package i_nav;

public interface INavEntity {
	
	String username = System.getenv("aws_username");
	String password = System.getenv("aws_password");
	String endpoint = System.getenv("aws_endpoint");
	String database = System.getenv("aws_database");
	
//	String username = System.getenv("bh_username");
//	String password = System.getenv("bh_password");
//	String endpoint = System.getenv("bh_endpoint");
//	String database = System.getenv("bh_database");
	

	
	String url = "jdbc:mysql://" + endpoint + ":3306/" + database;
	
	public String getJSONString();
	
}
