/**
 * SOEN 490
 * Capstone 2011
 * Team members: 	
 * 			Sotirios Delimanolis
 * 			Filipe Martinho
 * 			Adam Harrison
 * 			Vahe Chahinian
 * 			Ben Crudo
 * 			Anthony Boyer
 * 
 * @author Capstone 490 Team Moving Target
 *
 */
package application.strategy;

import java.util.HashMap;

/**
 * Singleton Factory for creating an appropriate RetrievalStrategy based on a sort method and whether or not advertisers should be returned.
 * Whichever class uses this factory needs to register the concrete RetrievalStrategy classes with a key. These will be mapped. Whenever a call is made 
 * to createStrategy(), the factory checks its map, and returns the mapped factory.
 * Mapping is like so:
 * key = "[Type of sort]-[Yes or no advertisers]" => value = ConcreateRetrievalStrategy
 * [Type of sort] is the sort method: date, rating, random. Can also be empty, should then default to random. In the end, depends on mapping
 * [Yes or no advertisers] is whether or not to include advertiser messages, should be "true"/"false"/""
 */
public class RetrievalStrategyFactory {
	private static RetrievalStrategyFactory uniqueInstance = null;
	
	private HashMap<String, RetrievalStrategy> map = new HashMap<String, RetrievalStrategy>();
	
	private RetrievalStrategyFactory() {
		
	}
	
	/**
	 * Maps the given key to the given RetrievalStrategy.
	 * @param key Key mapping 
	 * @param strategy Concreate RetrievalStrategy
	 * @return Returns the RetrievalStrategy previously registered with the given key, or null if no RetrievalStrategy was registered.
	 */
	public RetrievalStrategy registerRetrievalStrategy(String key, RetrievalStrategy strategy) {
		return map.put(key, strategy);
	}
	
	/**
	 * Returns a RetrievalStrategy mapped to a key constructed as "[sort]-[isAdvertiser]"
	 * @param sort Sort method
	 * @param isAdvertiser Whether to include advertiser messages
	 * @return Returns a RetrievalStrategy mapped to the constructed key.
	 * @throws MissingRetrievalStrategyException
	 */
	public RetrievalStrategy createStrategy(String sort, String isAdvertiser) throws MissingRetrievalStrategyException {
		RetrievalStrategy strategy = map.get(sort + "-" + isAdvertiser);
		if (strategy == null) {
			throw new MissingRetrievalStrategyException("No RetrievalStrategy was found for 'sort=" + sort + "' and 'isAdvertiser=" + isAdvertiser + "'.");
		}
		return strategy;
	}
	
	public static RetrievalStrategyFactory getUniqueInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new RetrievalStrategyFactory();
		return uniqueInstance;
	}
}
