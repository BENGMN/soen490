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
			
			assertFalse(rs.next());
			
			// Insert
			ServerParameterTDG.insert("paramName1", "A 1st description", "A 1st value");
			// Insert
			ServerParameterTDG.insert("paramName2", "A 2nd description", "A 2nd value");
			// Insert
			ServerParameterTDG.insert("paramName3", "A 3rd description", "A 3rd value");
			
			// paramName is primary key 
			rs = ServerParameterFinder.findAll();
			
			/*
			 * Should have 3 elements 
			 * So assert true 3 times and false 1
			 */
			assertTrue(rs.next());
			assertTrue(rs.next());
			assertTrue(rs.next());
			assertFalse(rs.next());
						
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
