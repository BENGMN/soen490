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


package tests.foundation;
import java.sql.Connection;
import java.sql.SQLException;

import junit.framework.TestCase;

import foundation.Database;

public class DatabaseTest extends TestCase {
	private static final String TABLE = "testtable";
	private static final String CREATE = "CREATE TABLE " + TABLE + " (value VARCHAR(10), PRIMARY KEY (value));";
	private static final String DROP = "DROP TABLE " + TABLE;
	
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
}
