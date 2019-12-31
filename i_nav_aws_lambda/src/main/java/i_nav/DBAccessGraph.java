package i_nav;

import java.util.List;
import java.util.Map;

import com.amazonaws.services.dynamodbv2.model.AttributeValue;

public interface DBAccessGraph {
	
	public void init() throws Exception;
	public void close();
	
	public void createTable(String tableName);
	public void addGraphVertex(String nodeId, String adj, String location, String locationId, String graphName);
	public void updateItem(String graphName, String type, String edge);
	public Map<LocationObjectVertex, List<Edge>> getCloudVerticesAndEdges(String graphName, String locationId);
	public Map<String, AttributeValue> getNode(String tableName, String nodeId);
	
	
	public int getNumAdds();
	public int getNumReads();
	public int getNumUpdates();
	public long getAddTime();
	public long getReadTime();
	public long getUpdateTime();
	public LocationObjectVertex getVertex(String graphName, String id);
	public List<Edge> getCloudVertexEdges(String graphName, String nodeId);
	public String getAdj(String graphName, String objectId);
	
}
