import static org.junit.jupiter.api.Assertions.*;

import org.json.simple.JSONArray;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import i_nav.Location;
import i_nav.LocationObject;

/**
 * 
 * @author CSCD490 Team5
 * @version 1.0
 * 
 *
 */
class LocationTests {

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
	void GetLocationByLocationId_ValidData_SizeOne() {
		
		// Arrange
		String locationId = "1";
		
		// Act
		JSONArray arr = Location.getLocations(locationId, null);
		
		// Assert
		Assertions.assertTrue(arr.size() == 1);
	}
	
	

}
