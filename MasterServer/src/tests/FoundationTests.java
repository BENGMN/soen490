package tests;

import java.sql.SQLException;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import tests.foundation.DbRegistryTest;
import tests.foundation.MessageFinderTest;
import tests.foundation.MessageTDGTest;
import tests.foundation.MySQLConnectionPoolFactoryTest;
import tests.foundation.PropertiesRegistryTest;
import tests.foundation.ServerListTDGTest;
import tests.foundation.ServerParameterFinderTest;
import tests.foundation.ServerParameterTDGTest;
import tests.foundation.UserFinderTest;
import tests.foundation.UserTDGTest;

import com.jolbox.bonecp.BoneCP;

import foundation.DbRegistry;
import foundation.MySQLConnectionPoolFactory;
import foundation.registry.PropertiesRegistry;

@RunWith(Suite.class)
@Suite.SuiteClasses({
	DbRegistryTest.class,
	MessageFinderTest.class,
	MessageTDGTest.class,
	MySQLConnectionPoolFactoryTest.class,
	PropertiesRegistryTest.class,
	ServerListTDGTest.class,
	ServerParameterFinderTest.class,
	ServerParameterTDGTest.class,
	UserFinderTest.class,
	UserTDGTest.class
})

public class FoundationTests {
	private final static String TEST_DB_ID = "";
	private static final String TABLE_PREFIX = "tablePrefix";
	@BeforeClass
	public static void setupDbRegistry() {
		System.out.println("Foundation Tests started.");
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
		System.out.println("Foundation Tests ended.");
	}
	
	@AfterClass
	public static void tearDown() {
		try {
			DbRegistry.closeDbConnection(TEST_DB_ID);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	} 
}
