package application.strategy;

import java.util.HashMap;

public class RetrievalStrategyFactory {
	private static RetrievalStrategyFactory uniqueInstance = null;
	
	private HashMap<String, RetrievalStrategy> map = new HashMap<String, RetrievalStrategy>();
	
	private RetrievalStrategyFactory() {
		
	}
	
	public RetrievalStrategy registerRetrievalStrategy(String key, RetrievalStrategy strategy) {
		return map.put(key, strategy);
	}
	
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
