/**
 * SOEN 490
 * Capstone 2011
 * Test for UserProxy.
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

import domain.User;
import domain.UserProxy;
import domain.UserMapper;
import junit.framework.TestCase;

public class UserProxyTest extends TestCase {
	final long uid = 3425635465657L;
	
	public void testGetters()
	{
		final String email = "example@example.com";
		final String password = "password";
		final User.UserType userType = User.UserType.USER_NORMAL;
		final int version = 1;
		User user = new User(uid, email, password, userType, version);
		UserMapper.insert(user);
		UserProxy proxy = new UserProxy(uid);
		assertEquals(proxy.getUid(), uid);
		assertEquals(proxy.getEmail(), email);
		assertEquals(proxy.getPassword(), password);
		assertEquals(proxy.getVersion(), version);
		assertEquals(proxy.getType(), userType);
		UserMapper.delete(user);
	}
	
	public void testSetters()
	{
		final String email = "example@example.com";
		final String password = "password";
		final User.UserType userType = User.UserType.USER_NORMAL;
		final int version = 1;
		User user = new User(uid, "", "", null, 0);
		UserMapper.insert(user);
		UserProxy proxy = new UserProxy(uid);
		proxy.setEmail(email);
		proxy.setPassword(password);
		proxy.setVersion(version);
		proxy.setType(userType);
		assertEquals(proxy.getEmail(), email);
		assertEquals(proxy.getPassword(), password);
		assertEquals(proxy.getVersion(), version);
		assertEquals(proxy.getType(), userType);
	}

}
