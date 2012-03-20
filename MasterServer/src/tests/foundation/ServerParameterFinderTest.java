package tests.foundation;

import java.sql.ResultSet;
import java.sql.SQLException;

import foundation.finder.ServerParameterFinder;
import foundation.tdg.ServerParameterTDG;
import junit.framework.TestCase;

public class ServerParameterFinderTest extends TestCase {
	// TODO THESE TESTS SHOULD NOT RUN IN THE SAME DB TABLES AS THE PROJECT
	/**
	 * Test to find one row in the table
	 */
	public void testFind() {
		try {
			ServerParameterTDG.create();
			// No rows yet
			
			ResultSet rs = ServerParameterFinder.find("paramName");
			
			assertFalse(rs.next());
			
			// Insert
			ServerParameterTDG.insert("paramName", "A description", "A value");
			
			// paramName is primary key 
			rs = ServerParameterFinder.find("paramName");
			
			assertTrue(rs.next());
			
			assertEquals("paramName", rs.getString("paramName"));
			assertEquals("A description", rs.getString("description"));
			assertEquals("A value", rs.getString("value"));
			
			rs.close();
			ServerParameterTDG.drop();
			
		} catch (SQLException e) {
			e.printStackTrace();
			fail();			
		} finally {
			try {
				ServerParameterTDG.drop();
			} catch (SQLException e) {
			}
		}
	}
	
	/**
	 * Test to find all rows in the table
	 */
	public void testFindAll() {
		try {
			ServerParameterTDG.create();
			// No rows yet
			
			ResultSet rs = ServerParameterFinder.findAll();
			
			int size = ServerParameterTDG.INSERTIONS.length;
						
			for (int i = 0; i < size; i++) {
				assertTrue(rs.next());
			}
			
			assertFalse(rs.next());
			
			rs.close();
			
			ServerParameterTDG.drop();
			
		} catch (SQLException e) {
			e.printStackTrace();
			fail();			
		} finally {
			try {
				ServerParameterTDG.drop();
			} catch (SQLException e) {
			}
		}
	}
}
