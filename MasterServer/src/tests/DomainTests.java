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

import tests.domain.message.*;
import tests.domain.serverparameter.ServerParameterInputMapperTest;
import tests.domain.serverparameter.ServerParameterOutputMapperTest;
import tests.domain.serverparameter.ServerParameterTest;
import tests.domain.serverparameter.ServerParametersTest;
import tests.domain.user.*;

@RunWith(Suite.class)

@SuiteClasses({ 
		 MessageInputMapperTest.class,
		 MessageOutputMapperTest.class,
		 MessageTest.class,
		 MessageFactoryTest.class,
		 
		 UserFactoryTest.class,
		 UserInputMapperTest.class,
		 UserOutputMapperTest.class,
		 UserProxyTest.class,
		 UserTest.class,
		 
		 ServerParameterInputMapperTest.class,
		 ServerParameterOutputMapperTest.class,
		 ServerParametersTest.class,
		 ServerParameterTest.class
})

public class DomainTests {
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