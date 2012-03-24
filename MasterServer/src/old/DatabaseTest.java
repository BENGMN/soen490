/**
 * SOEN 490
 * Capstone 2011
 * Test for database class.
 * Team members: 	Sotirios Delimanolis
 * 			Filipe Martinho
 * 			Adam Harrison
 * 			Vahe Chahinian
 * 			Ben Crudo
 * 			Anthony Boyer
 * 
 * @author Capstone 490 Team Moving Target
 *
 */


package old;
import java.sql.Connection;
import java.sql.SQLException;

import com.jolbox.bonecp.BoneCP;

import junit.framework.TestCase;

import foundation.DbRegistry;
import foundation.MySQLConnectionPoolFactory;
import foundation.registry.PropertiesRegistry;

public class DatabaseTest extends TestCase {
	private static final String TABLE = "testtable";
	private static final String CREATE = "CREATE TABLE " + TABLE + " (value VARCHAR(10), PRIMARY KEY (value));";
	private static final String DROP = "DROP TABLE " + TABLE;
	private static final String TEST_DB_ID = "test";
	private static final String TABLE_PREFIX = "tablePrefix"; 
	
	public void setupDbRegistry() throws SQLException {

		MySQLConnectionPoolFactory f = new MySQLConnectionPoolFactory(null, null, null, null);
		
		try {
			f.defaultInitialization(TEST_DB_ID);
		} catch (SQLException e) {
			e.printStackTrace();
			fail();
		}
		
		BoneCP cp;
		cp = f.createConnectionPool();
		DbRegistry.setConnectionPool(TEST_DB_ID, cp);
		
		String tablePrefix;
		
		try {
			tablePrefix = PropertiesRegistry.getProperty(TEST_DB_ID + TABLE_PREFIX );
		} catch (Exception e) {
			e.printStackTrace();
			tablePrefix = "";
		}

		DbRegistry.setTablePrefix(TEST_DB_ID, tablePrefix);

	}
	
	public void testDbGetConnection() {
		try {
			setupDbRegistry();
		} catch (SQLException e) {
			e.printStackTrace();
			fail();
		}
		
		try {
			Connection c1 = DbRegistry.getDbConnection(TEST_DB_ID);
			Connection c2 = DbRegistry.getDbConnection(TEST_DB_ID);
			
			assertTrue(c1 == c2);
			
		} catch (SQLException e) {
			fail();
		} finally {
			try {
				DbRegistry.dropDatabaseTables(TEST_DB_ID);
				DbRegistry.closeDbConnection(TEST_DB_ID);
			} catch(SQLException e) {				
			}
		}
	}
	
	public void testCloseDbConnection() {
		try {
			setupDbRegistry();
		} catch (SQLException e) {
			e.printStackTrace();
			fail();
		}
		System.out.println("============");
		try {
			Connection c1 = DbRegistry.getDbConnection(TEST_DB_ID);
			DbRegistry.closeDbConnection(TEST_DB_ID);
			assertTrue(c1.isClosed());
			
			// The connection pool for testing has only 1 connection
			// As such, it will be reopened (the same object) when you try to get a new connection
			
			Connection c2 = DbRegistry.getDbConnection(TEST_DB_ID);
			
			// for the same reason c1 == c2
			assertTrue(c1 == c2);
			
		} catch (SQLException e) {
			fail();
		} finally {
			try {
				DbRegistry.dropDatabaseTables(TEST_DB_ID);
				DbRegistry.closeDbConnection(TEST_DB_ID);
			} catch(SQLException e) {				
			}
		}
	}
	
	public void testHasTables() {
		try {
			setupDbRegistry();
		} catch (SQLException e) {
			e.printStackTrace();
			fail();
		}
		
		try {
			Connection connection = DbRegistry.getDbConnection(TEST_DB_ID);
			
			assertFalse(DbRegistry.hasTable(TEST_DB_ID, TABLE));
			
			connection.prepareStatement(CREATE).executeUpdate();
			
			assertTrue(DbRegistry.hasTable(TEST_DB_ID, TABLE));
			
			connection.prepareStatement(DROP).executeUpdate();
			
		} catch (SQLException e) {
			fail();
		} finally {
			try {
				DbRegistry.getDbConnection(TEST_DB_ID).prepareStatement(DROP).executeUpdate();
				DbRegistry.dropDatabaseTables(TEST_DB_ID);
				DbRegistry.closeDbConnection(TEST_DB_ID);
			} catch(SQLException e) {				
			}
		}
	}
	
	public void testIsDatabaseCreated() {
		try {
			setupDbRegistry();
		} catch (SQLException e) {
			e.printStackTrace();
			fail();
		}
		try {
			assertFalse("Test to ensure that the tables we for the applicaton exist", DbRegistry.isDatabaseCreated(TEST_DB_ID));
			DbRegistry.createDatabaseTables(TEST_DB_ID);
			assertTrue(DbRegistry.isDatabaseCreated(TEST_DB_ID));
			DbRegistry.dropDatabaseTables(TEST_DB_ID);
		} catch (SQLException e) {
			e.printStackTrace();
			fail();
		} finally {
			try {
				DbRegistry.dropDatabaseTables(TEST_DB_ID);
				DbRegistry.closeDbConnection(TEST_DB_ID);
			} catch (SQLException e) {
			}
		}
	}
	
	/*
	public void testGetConnection() {
		try {
			// these two should be the same because of same thread
			Connection conn1 = Database.getConnection();
			Connection conn2 = Database.getConnection();
			
			assertTrue(conn1 == conn2);
 		} catch (SQLException e) {
			e.printStackTrace();
			fail();
		} finally {
			try {
				Database.dropDatabaseTables();
				Database.freeConnection();
			} catch (SQLException e) {
			}
		}
	}
	
	public void testFreeConnection() {
		try {
			// these two should be different since you release it in between
			Connection conn1 = Database.getConnection();
			Database.freeConnection();
			Connection conn2 = Database.getConnection();
			
			assertTrue(conn1 != conn2);
 		} catch (SQLException e) {
			e.printStackTrace();
			fail();
		} finally {
			try {
				Database.dropDatabaseTables();
				Database.freeConnection();
			} catch (SQLException e) {
			}
		}
	}
	
	public void testHasTable() {
		try {
			Connection connection = Database.getConnection();
			
			assertFalse(Database.hasTable(TABLE));
			
			connection.prepareStatement(CREATE).executeUpdate();
			
			assertTrue(Database.hasTable(TABLE));
			
			connection.prepareStatement(DROP).executeUpdate();
 		} catch (SQLException e) {
			e.printStackTrace();
			fail();
		} finally {
			try {
				Database.getConnection().prepareStatement(DROP).executeUpdate();
				Database.dropDatabaseTables();
				Database.freeConnection();
			} catch (SQLException e) {
			}
		}
	}
	
	public void testIsDatabaseCreated() {
		try {
			assertFalse("Test to ensure that the tables we for the applicaton exist", Database.isDatabaseCreated());
			Database.createDatabaseTables();
			assertTrue(Database.isDatabaseCreated());
			Database.dropDatabaseTables();
		} catch (SQLException e) {
			e.printStackTrace();
			fail();
		} finally {
			try {
				Database.dropDatabaseTables();
				Database.freeConnection();
			} catch (SQLException e) {
			}
		}
	}
	*/
}
