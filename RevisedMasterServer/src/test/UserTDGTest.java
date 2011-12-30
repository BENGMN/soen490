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

package test;

import java.sql.ResultSet;
import java.sql.SQLException;

import foundation.UserFinder;
import foundation.UserTDG;


import junit.framework.TestCase;

public class UserTDGTest extends TestCase {
	
	static long uid = 158749857934L;
	
	public void testFunctionality()
	{
		testInsert();
		testUpdate();
		testDelete();
	}

	private void testInsert()
	{
		try {
			final String email = "example@example.com";
			final String password = "password";
			final int type = 1;
			final int version = 1;
			assertNull(UserFinder.find(uid));
			assertEquals(UserTDG.insert(uid, version, email, password, type), 1);
			ResultSet rs = UserFinder.find(uid);
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
	
	private void testUpdate()
	{
		try {
			final String email = "example2@example.com";
			final String password = "password2";
			final int type = 0;
			final int version = 2;
			assertNotNull(UserFinder.find(uid));
			assertEquals(UserTDG.update(uid, version, email, password, type), 1);
			ResultSet rs = UserFinder.find(uid);
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
	
	private void testDelete()
	{
		try {
			final int version = 1;
			assertNotNull(UserFinder.find(uid));
			assertEquals(UserTDG.delete(uid, version), 1);
			assertNull(UserFinder.find(uid));
		}
		catch (SQLException e) {
			fail("Exception failure:" + e);
		}
	}
}
