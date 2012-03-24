/**
 * SOEN 490
 * Capstone 2011
 * Test for MessageTDG.
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

import java.sql.ResultSet;
import java.sql.SQLException;

import junit.framework.TestCase;

import foundation.finder.ServerListFinder;
import foundation.tdg.ServerListTDG;

public class ServerListTDGTest extends TestCase {
	private final static String HOSTNAME = "http://ericsson1.ericsson.com/";
	private static final int PORT = 8080;
	
	public void testInsert() {
		try {
			ServerListTDG.create();
			// Should not be in there
			ResultSet rs = ServerListFinder.find(HOSTNAME);
			assertFalse(rs.next());
			rs.close();
			
			int inserted = ServerListTDG.insert(HOSTNAME, PORT);
			assertEquals(1, inserted);
			
			// Should find one
			rs = ServerListFinder.find(HOSTNAME);
			assertTrue(rs.next());
			String hostname = rs.getString("hostname");
			int port = rs.getInt("port");
			
			assertEquals(hostname, HOSTNAME);
			assertEquals(port, PORT);
			
			assertFalse(rs.next());
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
			fail();
		} finally {
			try {
				ServerListTDG.drop();
			} catch(SQLException e) {
			}
		}
	}
	
	public void testDelete() {
		try {
			ServerListTDG.create();
				
			int inserted = ServerListTDG.insert(HOSTNAME, PORT);
			assertEquals(1, inserted);

			int deleted = ServerListTDG.delete(HOSTNAME);
			
			assertEquals(1, deleted);
		} catch (SQLException e) {
			e.printStackTrace();
			fail();
		} finally {
			try {
				ServerListTDG.drop();
			} catch(SQLException e) {
			}
		}
	}
}
