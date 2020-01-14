import static org.junit.jupiter.api.Assertions.*;

import org.json.simple.JSONArray;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import i_nav_model.Location;
import i_nav_model.LocationObject;
import i_nav_model.Role;

/**
 * 
 * @author CSCD490 Team5
 * @version 1.0
 * 
 *
 */
class RoleTests {

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
	void GetRoleByRoleId_ValidData_SizeOne() {
		
		// Arrange
		String roleId = "1";
		
		// Act
		JSONArray arr = Role.getRoles(roleId);
		
		// Assert
		Assertions.assertTrue(arr.size() == 1);
	}
	
	

}
