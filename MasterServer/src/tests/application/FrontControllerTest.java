/**
 * SOEN 490
 * Capstone 2011
 * Test for front controller class.
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


package tests.application;

import static org.junit.Assert.*;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;

import org.junit.Test;

import foundation.Database;
import foundation.DbRegistry;
import foundation.finder.ServerListFinder;
import foundation.tdg.ServerListTDG;

import application.FrontController;


public class FrontControllerTest {
	
	@Test
	public void testFrontController() {
		FrontController frontController;
		try {
			DbRegistry.dropDatabaseTables();
			ServerListTDG.create();
			
			frontController = new FrontController();
			// init creates all database tables
			frontController.init();
			// that's what init does
			InetAddress addr = InetAddress.getLocalHost();
			String hostname = addr.getHostName();
			int port = 8080;
			
			ResultSet rs = ServerListFinder.find(hostname);
			assertTrue(rs.next());
			assertEquals(hostname, rs.getString("hostname"));
			assertEquals(port, rs.getInt("port"));
			
			assertTrue(DbRegistry.isDatabaseCreated());
		} catch (SQLException e) {
			e.printStackTrace();
			fail();
		} catch (ServletException e) {
			e.printStackTrace();
			fail();
		} catch (UnknownHostException e) {
			e.printStackTrace();
			fail();
		} finally {
			try {
				// need to drop all tables because FrontController init creates all of them
				DbRegistry.dropDatabaseTables();
			} catch (SQLException e) {
			}
		}
		
	}
}
