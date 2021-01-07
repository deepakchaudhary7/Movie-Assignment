package fun.crejo;

import java.util.HashMap;

public class ServiceDatabase {
	// static because all the objects of the ServiceDatabase will share the same hashmap 
	static HashMap<String, Movies> movieMapping = new HashMap<String,Movies>();
	static HashMap<String, User> userMapping    = new HashMap<String, User>();
	
	static final int[] noOfReviewsForCapability = {0, 3};
	static final int[] capabilityFactor = {1, 2};
	
	static final int noOfLevels = 2;  // normal_viewer, critic etc...
	
}
