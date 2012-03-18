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

import java.util.GregorianCalendar;
import java.io.IOException;
import java.math.BigInteger;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import org.junit.Test;

import foundation.Database;
import foundation.finder.MessageFinder;
import foundation.finder.ServerListFinder;
import foundation.tdg.MessageTDG;
import foundation.tdg.ServerListTDG;
import static org.junit.Assert.*;

public class ServerListTDGTest {

	@Test
	public void testFunctionality() throws SQLException, IOException {
		boolean previousDatabase = Database.isDatabaseCreated();
		if (!previousDatabase)
			Database.createDatabase();
		insert();
		delete();
		if (!previousDatabase)
			Database.dropDatabase();
	}

	private void insert() throws SQLException, IOException {
		final String hostname = "http://ericsson1.ericsson.com/";
		int port = 8080;
		ResultSet rs = ServerListFinder.find(hostname);
		assertFalse(rs.next());
		assertEquals(1, ServerListTDG.insert(hostname, port));
		rs = ServerListFinder.find(hostname);
		assertTrue(rs.next());
		assertEquals(hostname, rs.getString("hostname"));
		assertEquals(port, rs.getInt("port"));
	}
	
	private void delete() throws SQLException, IOException {
		final String hostname = "http://ericsson1.ericsson.com/";
		int port = 8080;
		ResultSet rs = ServerListFinder.find(hostname);
		assertTrue(rs.next());
		assertEquals(1, ServerListTDG.delete(hostname));
	}
}
