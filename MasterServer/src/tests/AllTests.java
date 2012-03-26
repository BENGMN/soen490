package tests;

import java.sql.SQLException;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.jolbox.bonecp.BoneCP;

import foundation.DbRegistry;
import foundation.MySQLConnectionPoolFactory;
import foundation.registry.PropertiesRegistry;

import tests.application.*;
import tests.application.strategy.AllOrderByDateRetrievalStrategyTest;
import tests.application.strategy.AllOrderByRatingRetrievalStrategyTest;
import tests.application.strategy.AllOrderRandomlyRetrievalStrategyTest;
import tests.application.strategy.NoAdvertisementOrderByDateRetrievalStrategyTest;
import tests.application.strategy.NoAdvertisementOrderByRatingRetrievalStrategyTest;
import tests.application.strategy.RetrievalStrategyFactoryTest;
import tests.application.strategy.RetrievalStrategyTest;
import tests.domain.message.*;
import tests.domain.serverparameter.ServerParameterInputMapperTest;
import tests.domain.serverparameter.ServerParameterOutputMapperTest;
import tests.domain.serverparameter.ServerParameterTest;
import tests.domain.serverparameter.ServerParametersTest;
import tests.domain.user.*;
import tests.foundation.*;
import tests.technical.GeoSpatialSearchTest;

@RunWith(Suite.class)

@SuiteClasses({
		 FrontControllerTest.class,
		 
		 PurgeMonitorTest.class,
		 
		 IOUtilsTest.class,
		 
		 UnsupportedCommandTest.class,
		 
		 RetrievalStrategyFactoryTest.class,
		 RetrievalStrategyTest.class,
		 AllOrderByRatingRetrievalStrategyTest.class,
		 AllOrderRandomlyRetrievalStrategyTest.class,
		 AllOrderByDateRetrievalStrategyTest.class,
		 NoAdvertisementOrderByDateRetrievalStrategyTest.class,
		 NoAdvertisementOrderByRatingRetrievalStrategyTest.class,
		 
		 MessageCommandTest.class,
		 MessageInputMapperTest.class,
		 MessageOutputMapperTest.class,
		 MessageTest.class,
		 MessageFactoryTest.class,
		 MessageTDGTest.class,
		 MessageFinderTest.class,
		 
		 UserFactoryTest.class,
		 UserInputMapperTest.class,
		 UserOutputMapperTest.class,
		 UserProxyTest.class,
		 UserTest.class,		 
		 UserTDGTest.class,
		 UserFinderTest.class,
		 
		 DbRegistryTest.class,
		 
		 MySQLConnectionPoolFactoryTest.class,
		 
		 PropertiesRegistryTest.class,

		 GeoSpatialSearchTest.class,
		 
		 ServerParameterInputMapperTest.class,
		 ServerParameterOutputMapperTest.class,
		 ServerParametersTest.class,
		 ServerParameterTest.class,
		 ServerParameterTDGTest.class,
		 ServerParameterFinderTest.class,
		 
		 ServerListTDGTest.class,
		 
})

public class AllTests {
	private final static String TEST_DB_ID = "";
	private static final String TABLE_PREFIX = "tablePrefix";
	
	@BeforeClass
	public static void setupDbRegistry() {
		System.out.println("All Tests started.");
		try {
			MySQLConnectionPoolFactory f = new MySQLConnectionPoolFactory(null, null, null, null);
	
			f.defaultInitialization(TEST_DB_ID);
			
			BoneCP cp;
			cp = f.createConnectionPool();
			DbRegistry.setConnectionPool(TEST_DB_ID, cp);
			
			String tablePrefix;
			
			try {
				tablePrefix = PropertiesRegistry.getProperty(TEST_DB_ID + TABLE_PREFIX);
			} catch (Exception e) {
				e.printStackTrace();
				tablePrefix = "";
			}
			
			DbRegistry.setTablePrefix(TEST_DB_ID, tablePrefix);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	@AfterClass
	public static void tearDown() {
		try {
			DbRegistry.closeDbConnection(TEST_DB_ID);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println("All Tests ended.");
	} 
}