import static org.junit.jupiter.api.Assertions.*;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import i_nav.CloudGraphListUndirected;
import i_nav_model.User;

class CloudGraphTests {

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
	void CreateConnection_AlreadyExists_Fails() {
		
		// Arrange
		String sourceObjectId = "357";
		String destObjectId = "356";
		String sourceLocationId = "1";
		String destLocationId = "1";
		
		CloudGraphListUndirected graph = new CloudGraphListUndirected("i_nav_graph1", false);
		graph.getPoints(sourceLocationId);
		if (!sourceLocationId.equals(destLocationId)) {
			graph.getPoints(destLocationId);
		}
		
		// Act
		JSONArray responseBodyArray = new JSONArray();
		responseBodyArray = CloudGraphListUndirected.setEdgeUndirected(graph, sourceObjectId, sourceLocationId, destObjectId, destLocationId);
		
		// Assert
		Assertions.assertEquals(0, responseBodyArray.size());
	}
	
}
