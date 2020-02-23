import static org.junit.jupiter.api.Assertions.*;

import org.json.simple.JSONArray;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import i_nav_model.LocationObject;

/**
 * 
 * @author CSCD490 Team5
 * @version 1.0
 * 
 *
 */
class LocationObjectTests {

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
	void GetLocationObjectsByLocationId_ValidData_SizeNotZero() {
		
		// Arrange
		String locationId = "1";
		
		// Act
		JSONArray arr = LocationObject.getLocationObjects(null, locationId, null, false);
		
		// Assert
		Assertions.assertTrue(arr.size() > 0);
	}
	
	@Test
	void GetLocationObjectsByLocationId_InvalidData_SizeZero() {
		
		// Arrange
		String locationId = "999999";
		
		// Act
		JSONArray arr = LocationObject.getLocationObjects(null, locationId, null, false);
		
		// Assert
		Assertions.assertTrue(arr.size() == 0);
	}

}
