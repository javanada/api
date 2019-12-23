package i_nav;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class CloudGraphListUndirected { // should be undirected
	
	private Map<String, List<Edge>> adj; // need to use String, not LocationObjectVertex... should be ID
	private Map<LocationObjectVertex, List<Edge>> adjVertex;
	private int numEdges;
	private List<String> Mark;
	private int maxWeight;
	
	String[] animals = {"Abyssinian", "Adelie Penguin", "Affenpinscher", "Afghan Hound", "African Bush Elephant", "African Civet", "African Clawed Frog", "African Forest Elephant", "African Palm Civet", "African Penguin", "African Tree Toad", "African Wild Dog", "Ainu Dog", "Airedale Terrier", "Akbash", "Akita", "Alaskan Malamute", "Albatross", "Aldabra Giant Tortoise", "Alligator", "Alpine Dachsbracke", "American Bulldog", "American Cocker Spaniel", "American Coonhound", "American Eskimo Dog", "American Foxhound", "American Pit Bull Terrier", "American Staffordshire Terrier", "American Water Spaniel", "Anatolian Shepherd Dog", "Angelfish", "Ant", "Anteater", "Antelope", "Appenzeller Dog", "Arctic Fox", "Arctic Hare", "Arctic Wolf", "Armadillo", "Asian Elephant", "Asian Giant Hornet", "Asian Palm Civet", "Asiatic Black Bear", "Australian Cattle Dog", "Australian Kelpie Dog", "Australian Mist", "Australian Shepherd", "Australian Terrier", "Avocet", "Axolotl", "Aye Aye", "Baboon", "Bactrian Camel", "Badger", "Balinese", "Banded Palm Civet", "Bandicoot", "Barb", "Barn Owl", "Barnacle", "Barracuda", "Basenji Dog", "Basking Shark", "Basset Hound", "Bat", "Bavarian Mountain Hound", "Beagle", "Bear", "Bearded Collie", "Bearded Dragon", "Beaver", "Bedlington Terrier", "Beetle", "Bengal Tiger", "Bernese Mountain Dog", "Bichon Frise", "Binturong", "Bird", "Birds Of Paradise", "Birman", "Bison", "Black Bear", "Black Rhinoceros", "Black Russian Terrier", "Black Widow Spider", "Bloodhound", "Blue Lacy Dog", "Blue Whale", "Bluetick Coonhound", "Bobcat", "Bolognese Dog", "Bombay", "Bongo", "Bonobo", "Booby", "Border Collie", "Border Terrier", "Bornean Orang-utan", "Borneo Elephant", "Boston Terrier", "Bottle Nosed Dolphin", "Boxer Dog", "Boykin Spaniel", "Brazilian Terrier", "Brown Bear", "Budgerigar", "Buffalo", "Bull Mastiff", "Bull Shark", "Bull Terrier", "Bulldog", "Bullfrog", "Bumble Bee", "Burmese", "Burrowing Frog", "Butterfly", "Butterfly Fish", "Caiman", "Caiman Lizard", "Cairn Terrier", "Camel", "Canaan Dog", "Capybara", "Caracal", "Carolina Dog", "Cassowary", "Cat", "Caterpillar", "Catfish", "Cavalier King Charles Spaniel", "Centipede", "Cesky Fousek", "Chameleon", "Chamois", "Cheetah", "Chesapeake Bay Retriever", "Chicken", "Chihuahua", "Chimpanzee", "Chinchilla", "Chinese Crested Dog", "Chinook", "Chinstrap Penguin", "Chipmunk", "Chow Chow", "Cichlid", "Clouded Leopard", "Clown Fish", "Clumber Spaniel", "Coati", "Cockroach", "Collared Peccary", "Collie", "Common Buzzard", "Common Frog", "Common Loon", "Common Toad", "Coral", "Cottontop Tamarin", "Cougar", "Cow", "Coyote", "Crab", "Crab-Eating Macaque", "Crane", "Crested Penguin", "Crocodile", "Cross River Gorilla", "Curly Coated Retriever", "Cuscus", "Cuttlefish", "Dachshund", "Dalmatian", "Darwin's Frog", "Deer", "Desert Tortoise", "Deutsche Bracke", "Dhole", "Dingo", "Discus", "Doberman Pinscher", "Dodo", "Dog", "Dogo Argentino", "Dogue De Bordeaux", "Dolphin", "Donkey", "Dormouse", "Dragonfly", "Drever", "Duck", "Dugong", "Dunker", "Dusky Dolphin", "Dwarf Crocodile", "Eagle", "Earwig", "Eastern Gorilla", "Eastern Lowland Gorilla", "Echidna", "Edible Frog", "Egyptian Mau", "Electric Eel", "Elephant", "Elephant Seal", "Elephant Shrew", "Emperor Penguin", "Emperor Tamarin", "Emu", "English Cocker Spaniel", "English Shepherd", "English Springer Spaniel", "Entlebucher Mountain Dog", "Epagneul Pont Audemer", "Eskimo Dog", "Estrela Mountain Dog", "Falcon", "Fennec Fox", "Ferret", "Field Spaniel", "Fin Whale", "Finnish Spitz", "Fire-Bellied Toad", "Fish", "Fishing Cat", "Flamingo", "Flat Coat Retriever", "Flounder", "Fly", "Flying Squirrel", "Fossa", "Fox", "Fox Terrier", "French Bulldog", "Frigatebird", "Frilled Lizard", "Frog", "Fur Seal", "Galapagos Penguin", "Galapagos Tortoise", "Gar", "Gecko", "Gentoo Penguin", "Geoffroys Tamarin", "Gerbil", "German Pinscher", "German Shepherd", "Gharial", "Giant African Land Snail", "Giant Clam", "Giant Panda Bear", "Giant Schnauzer", "Gibbon", "Gila Monster", "Giraffe", "Glass Lizard", "Glow Worm", "Goat", "Golden Lion Tamarin", "Golden Oriole", "Golden Retriever", "Goose", "Gopher", "Gorilla", "Grasshopper", "Great Dane", "Great White Shark", "Greater Swiss Mountain Dog", "Green Bee-Eater", "Greenland Dog", "Grey Mouse Lemur", "Grey Reef Shark", "Grey Seal", "Greyhound", "Grizzly Bear", "Grouse", "Guinea Fowl", "Guinea Pig", "Guppy", "Hammerhead Shark", "Hamster", "Hare", "Harrier", "Havanese", "Hedgehog", "Hercules Beetle", "Hermit Crab", "Heron", "Highland Cattle", "Himalayan", "Hippopotamus", "Honey Bee", "Horn Shark", "Horned Frog", "Horse", "Horseshoe Crab", "Howler Monkey", "Human", "Humboldt Penguin", "Hummingbird", "Humpback Whale", "Hyena", "Ibis", "Ibizan Hound", "Iguana", "Impala", "Indian Elephant", "Indian Palm Squirrel", "Indian Rhinoceros", "Indian Star Tortoise", "Indochinese Tiger", "Indri", "Insect", "Irish Setter", "Irish WolfHound", "Jack Russel", "Jackal", "Jaguar", "Japanese Chin", "Japanese Macaque", "Javan Rhinoceros", "Javanese", "Jellyfish", "Kakapo", "Kangaroo", "Keel Billed Toucan", "Killer Whale", "King Crab", "King Penguin", "Kingfisher", "Kiwi", "Koala", "Komodo Dragon", "Kudu", "Labradoodle", "Labrador Retriever", "Ladybird", "Leaf-Tailed Gecko", "Lemming", "Lemur", "Leopard", "Leopard Cat", "Leopard Seal", "Leopard Tortoise", "Liger", "Lion", "Lionfish", "Little Penguin", "Lizard", "Llama", "Lobster", "Long-Eared Owl", "Lynx", "Macaroni Penguin", "Macaw", "Magellanic Penguin", "Magpie", "Maine Coon", "Malayan Civet", "Malayan Tiger", "Maltese", "Manatee", "Mandrill", "Manta Ray", "Marine Toad", "Markhor", "Marsh Frog", "Masked Palm Civet", "Mastiff", "Mayfly", "Meerkat", "Millipede", "Minke Whale", "Mole", "Molly", "Mongoose", "Mongrel", "Monitor Lizard", "Monkey", "Monte Iberia Eleuth", "Moorhen", "Moose", "Moray Eel", "Moth", "Mountain Gorilla", "Mountain Lion", "Mouse", "Mule", "Neanderthal", "Neapolitan Mastiff", "Newfoundland", "Newt", "Nightingale", "Norfolk Terrier", "Norwegian Forest", "Numbat", "Nurse Shark", "Ocelot", "Octopus", "Okapi", "Old English Sheepdog", "Olm", "Opossum", "Orang-utan", "Ostrich", "Otter", "Oyster", "Quail", "Quetzal", "Quokka", "Quoll", "Rabbit", "Raccoon", "Raccoon Dog", "Radiated Tortoise", "Ragdoll", "Rat", "Rattlesnake", "Red Knee Tarantula", "Red Panda", "Red Wolf", "Red-handed Tamarin", "Reindeer", "Rhinoceros", "River Dolphin", "River Turtle", "Robin", "Rock Hyrax", "Rockhopper Penguin", "Roseate Spoonbill", "Rottweiler", "Royal Penguin", "Russian Blue", "Sabre-Toothed Tiger", "Saint Bernard", "Salamander", "Sand Lizard", "Saola", "Scorpion", "Scorpion Fish", "Sea Dragon", "Sea Lion", "Sea Otter", "Sea Slug", "Sea Squirt", "Sea Turtle", "Sea Urchin", "Seahorse", "Seal", "Serval", "Sheep", "Shih Tzu", "Shrimp", "Siamese", "Siamese Fighting Fish", "Siberian", "Siberian Husky", "Siberian Tiger", "Silver Dollar", "Skunk", "Sloth", "Slow Worm", "Snail", "Snake", "Snapping Turtle", "Snowshoe", "Snowy Owl", "Somali", "South China Tiger", "Spadefoot Toad", "Sparrow", "Spectacled Bear", "Sperm Whale", "Spider Monkey", "Spiny Dogfish", "Sponge", "Squid", "Squirrel", "Squirrel Monkey", "Sri Lankan Elephant", "Staffordshire Bull Terrier", "Stag Beetle", "Starfish", "Stellers Sea Cow", "Stick Insect", "Stingray", "Stoat", "Striped Rocket Frog", "Sumatran Elephant", "Sumatran Orang-utan", "Sumatran Rhinoceros", "Sumatran Tiger", "Sun Bear", "Swan", "Tang", "Tapir", "Tarsier", "Tasmanian Devil", "Tawny Owl", "Termite", "Tetra", "Thorny Devil", "Tibetan Mastiff", "Tiffany", "Tiger", "Tiger Salamander", "Tiger Shark", "Tortoise", "Toucan", "Tree Frog", "Tropicbird", "Tuatara", "Turkey", "Turkish Angora", "Uakari", "Uguisu", "Umbrellabird", "Vampire Bat", "Vervet Monkey", "Vulture", "Wallaby", "Walrus", "Warthog", "Wasp", "Water Buffalo", "Water Dragon", "Water Vole", "Weasel", "Welsh Corgi", "West Highland Terrier", "Western Gorilla", "Western Lowland Gorilla", "Whale Shark", "Whippet", "White Faced Capuchin", "White Rhinoceros", "White Tiger", "Wild Boar", "Wildebeest", "Wolf", "Wolverine", "Wombat", "Woodlouse", "Woodpecker", "Woolly Mammoth", "Woolly Monkey", "Wrasse", "X-Ray Tetra", "Yak", "Yellow-Eyed Penguin", "Yorkshire Terrier", "Zebra", "Zebra Shark", "Zebu", "Zonkey", "Zorse"};
	Random r = new Random();
	
	private DBAccessGraph dbGraphAccess;
	private String graphName;
	
	public Map<String, List<Edge>> getAdj() {
		return adj;
	}
	
	
	public CloudGraphListUndirected(String graphName, boolean isLambda) {
		this.graphName = graphName;
		adj = new HashMap<String, List<Edge>>();
		adjVertex = new HashMap<LocationObjectVertex, List<Edge>>();
		Mark = new LinkedList<String>();
		numEdges = 0;
		
		
		dbGraphAccess = new DBAccessGraphDynamoDB(isLambda);
		dbGraphAccess.createTable(graphName);
		
	}
	
	public LocationObjectVertex getVertex(String id) {
		
		return dbGraphAccess.getVertex(graphName, id);
	}
	
	
	public LocationObjectVertex setVertex(int x, int y) {
		return null;
//		int i = r.nextInt(animals.length);
//		
//		LocationObjectVertex locationItem = new LocationObjectVertex();
//		locationItem.setX(x);
//		locationItem.setY(y);
//		locationItem.setObject_id(0);
//		locationItem.setLocation_id(0);
//		
//		JSONParser parser = new JSONParser();
//		JSONArray arr = new JSONArray();
//		
//		try {
//			arr = LocationObject.newLocationObject((JSONObject)parser.parse(locationItem.getJSONString()));
//		} catch (ParseException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//		if (arr.size() > 0) {
//			System.out.println(arr.get(0));
//			JSONObject newLocationJsonObject = ((JSONObject)arr.get(0));
//			locationItem.setObject_id(Integer.parseInt(newLocationJsonObject.get("object_id").toString()));
//			dbGraphAccess.addGraphVertex(newLocationJsonObject.get("object_id").toString(), null, newLocationJsonObject.toJSONString(), graphName);
//			Mark.add(locationItem);
//			return locationItem;
//			
//		} else {
//			return null;
//		}
		
	}
	
	public void setVertex(JSONObject locationObjectJson) {
		JSONObject vertex = new JSONObject();
		vertex.put("object_id", locationObjectJson.get("object_id"));
		vertex.put("location_id", locationObjectJson.get("location_id"));
		vertex.put("x", locationObjectJson.get("x_coordinate"));
		vertex.put("y", locationObjectJson.get("y_coordinate"));
		dbGraphAccess.addGraphVertex(locationObjectJson.get("object_id").toString(), null, vertex.toJSONString(), locationObjectJson.get("location_id").toString(), graphName);
	}
	
	public void setEdgeDirected(LocationObjectVertex v1, LocationObjectVertex v2, int weight) {
		
		// todo: this should set undirected edge, fix
		
		
		
		if (adj.get("" + v1.getObject_id()) == null) {
			adj.put("" + v1.getObject_id(), new LinkedList<Edge>());
			adjVertex.put(v1, new LinkedList<Edge>());
			Mark.add("" + v1.getObject_id());
		}
		double distance_x = v2.getX() - v1.getX();
		double distance_y = v2.getY() - v1.getY();
		weight = (int) Math.sqrt(distance_x * distance_x + distance_y * distance_y);
		
		Edge e = new Edge(v1, v2, weight);
		adj.get("" + v1.getObject_id()).add(e);
		
		JSONArray arr = new JSONArray();
		String str = "";
		for (Edge i : adj.get("" + v1.getObject_id())) {
			JSONObject obj = new JSONObject();
			
			obj.put("weight", i.weight());
			
			JSONObject locationV1 = new JSONObject();
			locationV1.put("x", i.v1().getX());
			locationV1.put("y", i.v1().getY());
			locationV1.put("object_id", i.v1().getObject_id());
			locationV1.put("location_id", i.v1().getLocation_id());
			
			JSONObject locationV2 = new JSONObject();
			locationV2.put("x", i.v2().getX());
			locationV2.put("y", i.v2().getY());
			locationV2.put("object_id", i.v2().getObject_id());
			locationV2.put("location_id", i.v2().getLocation_id());
			
			obj.put("v1", locationV1);
			obj.put("v2", locationV2);
			
			arr.add(obj);
		}
		
		dbGraphAccess.updateItem(graphName, "" + v1.getObject_id(), arr.toJSONString());
		
		numEdges++;
		if (weight > maxWeight) {
			maxWeight = weight;
		}
	}
	
	public Set<LocationObjectVertex> getPoints(String locationId) {
		Map<LocationObjectVertex, List<Edge>> pointsAndEdges = dbGraphAccess.getCloudVerticesAndEdges(graphName, locationId);
		
		for (LocationObjectVertex item : pointsAndEdges.keySet()) {
			
			for (Edge e : pointsAndEdges.get(item)) {
				
				LocationObjectVertex v1 = e.v1();
				LocationObjectVertex v2 = e.v2();
				
				if (adj.get("" + v1.getObject_id()) == null) {
					adj.put("" + v1.getObject_id(), new LinkedList<Edge>());
					Mark.add("" + v1.getObject_id());
				}
				adj.get("" + v1.getObject_id()).add(e);
			}
		}
		return pointsAndEdges.keySet();
	}
	
	public int getNumEdges() {
		return numEdges;
	}
	
	public int getMaxWeight() {
		return maxWeight;
	}

	public int n() {
		return Mark.size();
	}
	
	public List<Edge> getEdges() {
		List<Edge> list = new ArrayList<Edge>();
		for (String item : adj.keySet()) {
			for (Edge e : adj.get(item)) {
				list.add(e);
			}
		}
		return list;
	}
	
	public static JSONArray getEdges(String locationId) {
		JSONArray jsonArray = new JSONArray();
		
		CloudGraphListUndirected graph1 = new CloudGraphListUndirected("i_nav_graph1", true);
		graph1.getPoints(locationId);
		List<Edge> list = graph1.getEdges();
		
		for (Edge e : list) {
			jsonArray.add(e.getJson());
		}
		
		return jsonArray;
	}
	
	public static JSONArray setEdgeUndirected(CloudGraphListUndirected graph, String sourceObjectId, String sourceLocationId, String destObjectId, String destLocationId) {
		
		JSONArray jsonArray = new JSONArray();
		
		JSONArray arrSource = LocationObject.getLocationObjects(sourceObjectId, sourceLocationId, null);
		JSONArray arrDest = LocationObject.getLocationObjects(destObjectId, destLocationId, null);
		
		if (arrSource.size() == 1 && arrDest.size() == 1) {
			
			JSONObject source = (JSONObject) arrSource.get(0);
			JSONObject dest = (JSONObject) arrDest.get(0);
			
			LocationObject sourceObject = new LocationObject(source);
			LocationObject destObject = new LocationObject(dest);
			
			LocationObjectVertex sourceVertex = new LocationObjectVertex();
			LocationObjectVertex destVertex = new LocationObjectVertex();
			
			sourceVertex.setLocation_id(sourceObject.getLocation_id());
			sourceVertex.setObject_id(sourceObject.getObject_id());
			sourceVertex.setX(sourceObject.getX_coordinate());
			sourceVertex.setY(sourceObject.getY_coordinate());
			
			destVertex.setLocation_id(destObject.getLocation_id());
			destVertex.setObject_id(destObject.getObject_id());
			destVertex.setX(destObject.getX_coordinate());
			destVertex.setY(destObject.getY_coordinate());
			
			if (graph == null)  {
				graph = new CloudGraphListUndirected("i_nav_graph1", true);			
				graph.getPoints(sourceLocationId);
				if (!sourceLocationId.equals(destLocationId)) {
					graph.getPoints(destLocationId);
				}
			}
//			graph1.adj.put(sourceObjectId, graph1.dbGraphAccess.getCloudVertexEdges(graph1.graphName, sourceObjectId));
//			graph1.adj.put(destObjectId, graph1.dbGraphAccess.getCloudVertexEdges(graph1.graphName, destObjectId));
			
			graph.setEdgeDirected(sourceVertex, destVertex, 0);
			graph.setEdgeDirected(destVertex, sourceVertex, 0);
			
			JSONObject ret = new JSONObject();
			ret.put("success", "maybe");
			ret.put("source", sourceVertex.getJSONString());
			ret.put("dest", destVertex.getJSONString());
			jsonArray.add(ret);
			
		} else {
			return jsonArray;
		}
		
		
		return jsonArray;
	}
	
	public static JSONArray getShortestPath(String sourceObjectId, String destObjectId, boolean isLambda) {
		
		JSONArray jsonArray = new JSONArray();
		
		CloudGraphListUndirected graph1 = new CloudGraphListUndirected("i_nav_graph1", isLambda);
		graph1.getPoints(null);
		Search search = new Search(graph1);
		LocationObjectVertex start = graph1.getVertex(sourceObjectId);
		LocationObjectVertex end = graph1.getVertex(destObjectId);
		search.dijkstra(start, end);
		ArrayList<Edge> path = search.getPathToVertex();
		if (path != null) {
			for (Edge e : path) {
				JSONObject obj = e.getJson();
				double dist = Math.sqrt(((e.v1().getX() - e.v2().getX()) * (e.v1().getX() - e.v2().getX()) + (e.v1().getY() - e.v2().getY()) * (e.v1().getY() - e.v2().getY())));
				String str = "walk " + Math.round(dist) + " ft. from " + e.v1().getObject_id() + " to " + e.v2().getObject_id();
				obj.put("directions", str);
				jsonArray.add(obj);
			}
		} else {
			jsonArray.add("path is null");
		}
		
//		if (start != null) { jsonArray.add(start.toJSON()); }
//		if (end != null) { jsonArray.add(end.toJSON()); }
		
		return jsonArray;
	}

	public List<LocationObjectVertex> neighbors(LocationObjectVertex u) {
		
		List<LocationObjectVertex> uNeighbors = new ArrayList<LocationObjectVertex>();
		
		if (u == null) {
			return uNeighbors;
		}
		
		List<Map<LocationObjectVertex, Edge>> ret = new ArrayList<Map<LocationObjectVertex, Edge>>();
		
		System.out.println("adj size: " + adj.size());
		for (String s : adj.keySet()) {
			System.out.print(", " + s);
		}
		System.out.println();
		if (adj.containsKey("" + u.getObject_id())) {
			for (Edge e : adj.get("" + u.getObject_id())) {
				if (e != null) {
					LocationObjectVertex v1 = e.v1();
					LocationObjectVertex v2 = e.v2();
					if (v1.equals(u)) {
						uNeighbors.add(v2);
					} else {
						uNeighbors.add(v1);
					}
				}
			}
		}
		return uNeighbors;
	}
	
}
