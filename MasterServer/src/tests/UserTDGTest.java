/**
 * SOEN 490
 * Capstone 2011
 * Test for UserTDG.
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

import java.sql.ResultSet;
import static org.junit.Assert.*;
import java.sql.SQLException;

import org.junit.Test;

import foundation.Database;
import foundation.UserFinder;
import foundation.UserTDG;

public class UserTDGTest {
	
	static long uid = 158749857934L;
	
	@Test
	public void testFunctionality()
	{
		create();
		insert();
		update();
		delete();
		drop();
	}
	
	private void create()
	{
		try {
			assertFalse(Database.getInstance().hasTable("User"));
			UserTDG.create();
			// This should be expanded to check the schemas too. Probably.
			assertTrue(Database.getInstance().hasTable("User"));
		}
		catch (SQLException E) {
			fail("Exception Failure: " + E);
		}
	}
	
	private void drop()
	{
		try {
			assertTrue(Database.getInstance().hasTable("User"));
			UserTDG.drop();
			// This should be expanded to check the schemas too. Probably.
			assertFalse(Database.getInstance().hasTable("User"));
		}
		catch (SQLException E) {
			fail("Exception Failure: " + E);
		}
	}

	private void insert()
	{
		try {
			final String email = "example@example.com";
			final String password = "password";
			final int type = 1;
			final int version = 1;
			assertFalse(UserFinder.find(uid).next());
			assertEquals(UserTDG.insert(uid, version, email, password, type), 1);
			ResultSet rs = UserFinder.find(uid);
			assertTrue(rs.next());
			assertEquals(rs.getLong("u.uid"), uid);
			assertEquals(rs.getString("u.email"), email);
			assertEquals(rs.getString("u.password"), password);
			assertEquals(rs.getInt("u.version"), version);
			assertEquals(rs.getInt("u.type"), type);
		}
		catch (SQLException e) {
			fail("Exception failure:" + e);
		}
	}
	
	private void update()
	{
		try {
			final String email = "example2@example.com";
			final String password = "password2";
			final int type = 0;
			final int version = 1;
			assertTrue(UserFinder.find(uid).next());
			assertEquals(1, UserTDG.update(uid, version, email, password, type));
			ResultSet rs = UserFinder.find(uid);
			assertTrue(rs.next());
			assertEquals(rs.getLong("u.uid"), uid);
			assertEquals(rs.getString("u.email"), email);
			assertEquals(rs.getString("u.password"), password);
			assertEquals(rs.getInt("u.version"), version+1);
			assertEquals(rs.getInt("u.type"), type);
		}
		catch (SQLException e) {
			fail("Exception failure:" + e);
		}
	}
	
	private void delete()
	{
		try {
			final int version = 2;
			assertTrue(UserFinder.find(uid).next());
			assertEquals(UserTDG.delete(uid, version), 1);
			assertFalse(UserFinder.find(uid).next());
		}
		catch (SQLException e) {
			fail("Exception failure:" + e);
		}
	}
}
