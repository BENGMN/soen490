package tests.application.strategy;

import java.sql.SQLException;

import foundation.tdg.ServerParameterTDG;
import application.strategy.AllOrderByDateRetrievalStrategy;
import application.strategy.AllOrderByRatingRetrievalStrategy;
import application.strategy.AllOrderRandomlyRetrievalStrategy;
import application.strategy.MissingRetrievalStrategyException;
import application.strategy.NoAdvertisementOrderByDateRetrievalStrategy;
import application.strategy.NoAdvertisementOrderByRatingRetrievalStrategy;
import application.strategy.OnlyAdvertisementRetrievalStrategy;
import application.strategy.RetrievalStrategy;
import application.strategy.RetrievalStrategyFactory;
import junit.framework.TestCase;

public class RetrievalStrategyFactoryTest extends TestCase {
	
	private String sortDate = "date";
	private String sortRating = "rating";
	private String sortRandom = "random";
			
	public void testSingleton() {
		try {
			ServerParameterTDG.create();
			RetrievalStrategyFactory f = RetrievalStrategyFactory.getUniqueInstance();
			RetrievalStrategyFactory f2 = RetrievalStrategyFactory.getUniqueInstance();
			
			assertTrue(f == f2);
		} catch (SQLException e) {
			e.printStackTrace();
			fail();
		} finally {
			try {
				ServerParameterTDG.drop();
			} catch (SQLException e) {
			}
		}
	}
	
	public void testRegisterStrategyAndCreateStrateg() {
		try {
			ServerParameterTDG.create();
			RetrievalStrategy retrievalStrategy = null;
			RetrievalStrategyFactory strategyFactory = RetrievalStrategyFactory.getUniqueInstance();
			
			RetrievalStrategy onlyAdv = new OnlyAdvertisementRetrievalStrategy();			
			RetrievalStrategy allOrderByDate = new AllOrderByDateRetrievalStrategy();
			RetrievalStrategy allOrderByRating = new AllOrderByRatingRetrievalStrategy();
			RetrievalStrategy allOrderRandomly = new AllOrderRandomlyRetrievalStrategy();
			RetrievalStrategy noAdvOrderDate = new NoAdvertisementOrderByDateRetrievalStrategy();
			RetrievalStrategy noAdvOrderRating = new NoAdvertisementOrderByRatingRetrievalStrategy();
			
			strategyFactory.registerRetrievalStrategy("date-", allOrderByDate);
			strategyFactory.registerRetrievalStrategy("rating-", allOrderByRating);
			strategyFactory.registerRetrievalStrategy("random-", allOrderRandomly);
			
			retrievalStrategy = strategyFactory.createStrategy(sortRating, "");
			assertTrue(allOrderByRating == retrievalStrategy);
			
			retrievalStrategy = strategyFactory.createStrategy(sortDate, "");
			assertTrue(allOrderByDate == retrievalStrategy);
			
			retrievalStrategy = strategyFactory.createStrategy(sortRandom, "");
			assertTrue(allOrderRandomly == retrievalStrategy);
			
			strategyFactory.registerRetrievalStrategy("date-false", noAdvOrderDate);
			strategyFactory.registerRetrievalStrategy("rating-false", noAdvOrderRating);
			
			retrievalStrategy = strategyFactory.createStrategy(sortRating, "false");
			assertTrue(noAdvOrderRating == retrievalStrategy);
			
			retrievalStrategy = strategyFactory.createStrategy(sortDate, "false");
			assertTrue(noAdvOrderDate == retrievalStrategy);
			
			strategyFactory.registerRetrievalStrategy("random-true", onlyAdv);
			strategyFactory.registerRetrievalStrategy("-true", onlyAdv);
	
			
			retrievalStrategy = strategyFactory.createStrategy(sortRandom, "true");
			assertTrue(onlyAdv == retrievalStrategy);
			
			retrievalStrategy = strategyFactory.createStrategy("", "true");
			assertTrue(onlyAdv == retrievalStrategy);
			
			
		} catch (SQLException e) {
			e.printStackTrace();
			fail();
		} catch (MissingRetrievalStrategyException e){
			e.printStackTrace();
			fail();
		} finally {
			try {
				ServerParameterTDG.drop();
			} catch (SQLException e) {
			}
		}
		
		
	}

}
