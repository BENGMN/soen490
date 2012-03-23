package tests.foundation;

import java.sql.Connection;
import java.sql.SQLException;

import junit.framework.TestCase;

import foundation.DbRegistry;

public class DbRegistryTest extends TestCase {
	private static final String TABLE = "testtable";
	private static final String CREATE = "CREATE TABLE " + TABLE + " (value VARCHAR(10), PRIMARY KEY (value));";
	private static final String DROP = "DROP TABLE " + TABLE;
	
	public void testDbGetConnection() {
		try {
			Connection c1 = DbRegistry.getDbConnection();
			Connection c2 = DbRegistry.getDbConnection();
			
			assertTrue(c1 == c2);
			
		} catch (SQLException e) {
			fail();
		} finally {
			try {
				DbRegistry.dropDatabaseTables();
				DbRegistry.closeDbConnection();
			} catch(SQLException e) {				
			}
		}
	}
	
	public void testCloseDbConnection() {
		try {
			Connection c1 = DbRegistry.getDbConnection();
			DbRegistry.closeDbConnection();
			assertTrue(c1.isClosed());			
		} catch (SQLException e) {
			fail();
		} finally {
			try {
				DbRegistry.dropDatabaseTables();
				DbRegistry.closeDbConnection();
			} catch(SQLException e) {				
			}
		}
	}
	
	public void testHasTables() {		
		try {
			Connection connection = DbRegistry.getDbConnection();
			
			assertFalse(DbRegistry.hasTable(TABLE));
			
			connection.prepareStatement(CREATE).executeUpdate();
			
			assertTrue(DbRegistry.hasTable(TABLE));
			
			connection.prepareStatement(DROP).executeUpdate();
			
		} catch (SQLException e) {
			fail();
		} finally {
			try {
				DbRegistry.getDbConnection().prepareStatement(DROP).executeUpdate();
				DbRegistry.dropDatabaseTables();
				DbRegistry.closeDbConnection();
			} catch(SQLException e) {				
			}
		}
	}
	
	public void testIsDatabaseCreated() {
		try {
			assertFalse("Test to ensure that the tables we for the applicaton exist", DbRegistry.isDatabaseCreated());
			DbRegistry.createDatabaseTables();
			assertTrue(DbRegistry.isDatabaseCreated());
			DbRegistry.dropDatabaseTables();
		} catch (SQLException e) {
			e.printStackTrace();
			fail();
		} finally {
			try {
				DbRegistry.dropDatabaseTables();
				DbRegistry.closeDbConnection();
			} catch (SQLException e) {
			}
		}
	}
}
