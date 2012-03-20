package tests.foundation;

import java.math.BigInteger;
import java.sql.ResultSet;
import java.sql.SQLException;

import foundation.finder.UserFinder;
import foundation.tdg.UserTDG;

import junit.framework.TestCase;

public class UserFinderTest extends TestCase {
	private BigInteger uid1 = new BigInteger("158749857934");
	private BigInteger uid2 = new BigInteger("158712312349857934");
	private final String email = "example@example.com";
	private final String password = "password";
	private int version = 0;
	private int type = 0;
	
	public void testFind() {
		try {
			UserTDG.create();
			
			UserTDG.insert(uid1, version, email, password, type);
			
			ResultSet rs = UserFinder.find(uid1);
			
			assertTrue(rs.next());
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
	
	public void testFindAll() {
		try {
			UserTDG.create();
			
			UserTDG.insert(uid1, version, email, password, type);
			UserTDG.insert(uid2, version, email, password, type);

			ResultSet rs = UserFinder.findAll();
			
			assertTrue(rs.next());
			assertTrue(rs.next());
			assertFalse(rs.next());
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
}
