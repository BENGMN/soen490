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

package tests.unittests;

import static org.junit.Assert.*;

import org.junit.Test;

import domain.user.User;
import domain.user.UserType;

public class UserTest {
	final long uid = 3425635465657L;
	
	@Test
	public void testGetters()
	{
		final String email = "example@example.com";
		final String password = "password";
		final UserType userType = UserType.USER_NORMAL;
		final int version = 1;
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
		final String email = "example@example.com";
		final String password = "password";
		final UserType userType = UserType.USER_NORMAL;
		final int version = 1;
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
}
