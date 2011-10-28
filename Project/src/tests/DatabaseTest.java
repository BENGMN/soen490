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


package tests;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import foundation.Database;
import junit.framework.TestCase;

public class DatabaseTest extends TestCase {
	public void testCreateTable()
	{
		try {
			PreparedStatement statement = Database.getInstance().getStatement("CREATE TABLE testTable (testColumn1 varchar(40), testColumn2 varchar(50)");
			assertTrue(statement.execute());
		}
		catch (Exception e)
		{
			fail("Failed to create table: " + e);
		}
	}
	
	// Here we test the various tables in the database to see if they're present.
	public void testDatabaseComposition()
	{
		
	}
}
