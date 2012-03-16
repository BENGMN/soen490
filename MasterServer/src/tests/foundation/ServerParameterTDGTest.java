package tests.foundation;

import java.sql.SQLException;

import foundation.Database;
import foundation.tdg.ServerParameterTDG;
import junit.framework.TestCase;

public class ServerParameterTDGTest extends TestCase {
	/**
	 * Test to create the table
	 */
	public void testCreate() {
		try {
			ServerParameterTDG.create();
			assertTrue(Database.hasTable(ServerParameterTDG.TABLE));
			ServerParameterTDG.drop();
		} catch (SQLException e) {
			fail();
			e.printStackTrace();
		} finally {
			try {
				ServerParameterTDG.drop();
			} catch (SQLException e) {
			}
		}
	}
	
	/**
	 * Test to drop the table
	 */
	public void testDrop() {
		try {
			ServerParameterTDG.create();
			assertTrue(Database.hasTable(ServerParameterTDG.TABLE));
			ServerParameterTDG.drop();
			assertFalse(Database.hasTable(ServerParameterTDG.TABLE));
		} catch (SQLException e) {
			fail();
			e.printStackTrace();
		} finally {
			try {
				ServerParameterTDG.drop();
			} catch (SQLException e) {
			}
		}
	}
	
	/**
	 * Test to insert a row into the table 
	 */
	public void testInsert() {
		try {
			ServerParameterTDG.create();

			ServerParameterTDG.insert("paramName", "A description", "A value");
			try {
				ServerParameterTDG.insert("paramName", "other description", "other value");
			} catch (SQLException e) {
				// paramName is primary key, no duplicates
				assertTrue(true);
			}			
			
			ServerParameterTDG.drop();
		} catch (SQLException e) {
			fail();
			e.printStackTrace();
		} finally {
			try {
				ServerParameterTDG.drop();
			} catch (SQLException e) {
			}
		}
	}
	
	/**
	 * Test to delete a row from the table
	 */
	public void testDelete() {
		try {
			ServerParameterTDG.create();

			// insert
			ServerParameterTDG.insert("paramName", "A description", "A value");
			// delete
			assertEquals(1, ServerParameterTDG.delete("paramName"));
				
			ServerParameterTDG.drop();
		} catch (SQLException e) {
			fail();
			e.printStackTrace();
		} finally {
			try {
				ServerParameterTDG.drop();
			} catch (SQLException e) {
			}
		}
	}
	
	/**
	 * Test to update a row in the table 
	 */
	public void testUpdate() {
		try {
			ServerParameterTDG.create();

			ServerParameterTDG.insert("paramName", "A description", "A value");
			
			// update
			assertEquals(1, ServerParameterTDG.update("paramName", "some description", "other value"));	
			
			// update again
			assertEquals(1, ServerParameterTDG.update("paramName", "some other description", "and another value"));	
			
			// delete
			assertEquals(1, ServerParameterTDG.delete("paramName"));	
			
			ServerParameterTDG.drop();
		} catch (SQLException e) {
			fail();
			e.printStackTrace();
		} finally {
			try {
				ServerParameterTDG.drop();
			} catch (SQLException e) {
			}
		}
	}
}
