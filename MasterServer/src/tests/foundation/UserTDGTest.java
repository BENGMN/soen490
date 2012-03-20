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

package tests.foundation;

import java.math.BigInteger;
import java.sql.ResultSet;
import java.sql.SQLException;

import junit.framework.TestCase;

import foundation.finder.UserFinder;
import foundation.tdg.UserTDG;

public class UserTDGTest extends TestCase {
	
	private BigInteger uid = new BigInteger("158749857934");
	private final String email = "example@example.com";
	private final String password = "password";
	private int version = 0;
	private int type = 0;
	
	public void testInsert() {
		try {
			UserTDG.create();
			
			UserTDG.insert(uid, version, email, password, type);
			
			// try adding second with same id
			try {
				UserTDG.insert(uid, version, email, password, type);
				fail();
			} catch (SQLException e) {
				// should reach here
			}
		} catch (SQLException e){
			e.printStackTrace();
			fail();
		} finally {
			try {
				UserTDG.drop();
			} catch (SQLException e) {				
			}
		}
	}
	
	public void testUpdate() {
		try {
			UserTDG.create();
			String newEmail = email + "some other value";
			UserTDG.insert(uid, version, email, password, type);
			
			assertEquals(1, UserTDG.update(uid, version, newEmail, password, type));
			
			ResultSet rs = UserFinder.find(uid);
			rs.next();
			
			String foundEmail = rs.getString("u.email");
			//version will have changed
			
			int newVersion = rs.getInt("u.version");
			
			assertTrue(foundEmail.equals(newEmail));
			assertFalse(newVersion == version);
			
			rs.close();
		} catch (SQLException e){
			e.printStackTrace();
			fail();
		} finally {
			try {
				UserTDG.drop();
			} catch (SQLException e) {				
			}
		}
	}
	
	public void testDelete() {
		try {
			UserTDG.create();
			UserTDG.insert(uid, version, email, password, type);
			ResultSet rs = UserFinder.find(uid);
			
			assertTrue(rs.next());
			
			// update will increment version by 1
			UserTDG.update(uid, version, email, password + "eqweqweas", type);
			
			// will not delete anything, as version differes from row version
			assertEquals(0, UserTDG.delete(uid, version));
			
			// will delete the row
			assertEquals(1, UserTDG.delete(uid, version + 1));
			
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
			fail();
		} finally {
			try {
				UserTDG.drop();
			} catch (SQLException e) {				
			}
		}
	}
}
