/**
 * SOEN 490
 * Capstone 2011
 * Test for User.
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

package tests.domain.user;

import static org.junit.Assert.*;

import org.junit.Test;

import domain.user.User;
import domain.user.UserType;

public class UserTest {
	
	private final long uid = 3425635465657L;
	private final String email = "example@example.com";
	private final String password = "password";
	private final UserType userType = UserType.USER_NORMAL;
	private final int version = 1;
	
	@Test
	public void testGetters()
	{
		User user = new User(uid, email, password, userType, version);
		assertEquals(user.getUid(), uid);
		assertEquals(user.getEmail(), email);
		assertEquals(user.getPassword(), password);
		assertEquals(user.getVersion(), version);
		assertEquals(user.getType(), userType);
	}
	
	@Test
	public void testSetters()
	{
		User user = new User(uid, "", "", null, 0);
		user.setEmail(email);
		user.setPassword(password);
		user.setVersion(version);
		user.setType(userType);
		assertEquals(user.getEmail(), email);
		assertEquals(user.getPassword(), password);
		assertEquals(user.getVersion(), version);
		assertEquals(user.getType(), userType);
	}
	
	@Test
	public void testEquals()
	{	
		User user1 = new User(uid, email, password, userType, 0);
		User user2 = new User(uid, email, password, userType, 1);
		
		assertEquals(user1.equals(null), false);
		assertEquals(user1.equals(user2), false);
		assertEquals(user1.equals(user1), true);
	}
}
