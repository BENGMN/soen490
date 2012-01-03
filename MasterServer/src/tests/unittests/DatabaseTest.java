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


package tests.unittests;

import java.sql.SQLException;

import foundation.Database;
import foundation.MessageTDG;
import foundation.UserTDG;
import junit.framework.TestCase;

public class DatabaseTest extends TestCase {
	public void testConnection()
	{
		assertTrue(Database.getInstance().canConnect());
	}
	
	public void testTables()
	{
		partTestCreateTable();
		partTestDeleteTable();
	}
	
	
	private void partTestCreateTable()
	{
		try
		{
			assertTrue("Test table already exists!", !Database.getInstance().hasTable("testTable"));
			Database.getInstance().update("CREATE TABLE testTable (testColumn1 varchar(40), testColumn2 varchar(50))");
			assertTrue("Test table not created.", Database.getInstance().hasTable("testTable"));
		}
		catch (SQLException e)
		{
			fail("Failed to create table: " + e);
		}
	}
	
	private void partTestDeleteTable()
	{
		try
		{
			assertTrue("Test table not created.", Database.getInstance().hasTable("testTable"));
			Database.getInstance().update("DROP TABLE testTable");
			assertTrue("Test table not deleted.", !Database.getInstance().hasTable("testTable"));
		}
		catch (SQLException e)
		{
			fail("Failed to delete table: " + e);
		}
	}
}
