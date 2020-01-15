import static org.junit.jupiter.api.Assertions.*;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import i_nav_model.User;

class UserTests {

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void CreateUser_ValidData_Works() {
		
		// Arrange
		String fName = "david";
		User user = new User();
		
		// Act
		user.setFirst_name(fName);
		
		// Assert
		Assertions.assertEquals(fName, user.getFirst_name());
	}
	
	@Test
	void GetUser_ValidData_Works() {
		
		// Arrange
		String userId = "1";
		String fName = "david";
		
		// Act
		JSONArray arr = User.getUsers(userId, null);
		JSONObject obj = (JSONObject) arr.get(0);
		User user = new User(obj);
		
		// Assert
		Assertions.assertEquals(fName, user.getFirst_name());
	}

}
