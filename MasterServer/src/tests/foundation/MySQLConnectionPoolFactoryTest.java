package tests.foundation;

import java.sql.SQLException;

import com.jolbox.bonecp.BoneCP;
import java.sql.Connection;

import junit.framework.TestCase;
import foundation.MySQLConnectionPoolFactory;
import foundation.registry.PropertiesRegistry;

public class MySQLConnectionPoolFactoryTest extends TestCase {
	private static String hostname;
	private static String database;
	private static String username;
	private static String password;
	
	public void initializeTestValues() {
		try {
			hostname = PropertiesRegistry.getProperty("hostname");
			database = PropertiesRegistry.getProperty("database");
			username = PropertiesRegistry.getProperty("username");
			password = PropertiesRegistry.getProperty("password");
		} catch (Exception e) {
			fail();
		}
	}
	
	public void testDefaultInitialization() {
		initializeTestValues();
		try {
			MySQLConnectionPoolFactory f = new MySQLConnectionPoolFactory(null, null, null, null);
			f.defaultInitialization();
			assertTrue(hostname.equals(f.getHostname()));
			assertTrue(database.equals(f.getDatabase()));
			assertTrue(username.equals(f.getUsername()));
			assertTrue(password.equals(f.getPassword()));
			
			BoneCP cp = f.createConnectionPool();
			
			Connection c = cp.getConnection();
			assertNotNull(c);
			
			// do a basic sql query
			c.prepareStatement("START TRANSACTION;").execute();
			c.prepareStatement("COMMIT;").execute();
			
			c.close();
		} catch (SQLException e) {
			e.printStackTrace();
			fail();
		}
	}
	
	public void testCreateConnectionPool() {
		initializeTestValues();
		try {
			MySQLConnectionPoolFactory f = new MySQLConnectionPoolFactory(null, null, null, null);
			f.defaultInitialization();
			
			BoneCP cp = f.createConnectionPool();
			
			Connection c = cp.getConnection();
			assertNotNull(c);
			
			// do a basic sql query
			c.prepareStatement("START TRANSACTION;").execute();
			c.prepareStatement("COMMIT;").execute();
			
			c.close();
		} catch (SQLException e) {
			e.printStackTrace();
			fail();
		}
	}
}
