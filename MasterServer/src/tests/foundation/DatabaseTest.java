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
import static org.junit.Assert.*;

import java.io.IOException;
import java.sql.SQLException;

import org.junit.Test;

import foundation.Database;

public class DatabaseTest {

	@Test
	public void testTables() throws IOException, SQLException
	{
		partTestCreateTable();
		partTestDeleteTable();
	}
	
	private void partTestCreateTable() throws IOException, SQLException
	{
		assertTrue("Test table already exists!", !Database.hasTable("testTable"));
		Database.update("CREATE TABLE testTable (testColumn1 varchar(40), testColumn2 varchar(50))");
		assertTrue("Test table not created.", Database.hasTable("testTable"));
	}
	
	private void partTestDeleteTable() throws IOException, SQLException
	{
		assertTrue("Test table not created.", Database.hasTable("testTable"));
		Database.update("DROP TABLE testTable");
		assertTrue("Test table not deleted.", !Database.hasTable("testTable"));
	}
	
	public void testIsDatabaseCreated() throws SQLException, IOException {
		assertTrue("Test to ensure that the tables we for the applicaton exist", Database.isDatabaseCreated());
	}
}
