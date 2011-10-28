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
}
