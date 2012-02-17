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

import java.io.IOException;

import domain.user.User;
import domain.user.UserFactory;
import domain.user.UserProxy;
import domain.user.UserType;
import junit.framework.TestCase;


public class UserProxyTest extends TestCase {
	
	private final long uid = 3425635465657L;
	private final String email = "example@example.com";
	private final String password = "password";
	private final UserType userType = UserType.USER_NORMAL;
	private final int version = 1;
	
	private User realUser = null;						// Create a real user object
	private UserProxy userProxy = null;	// Create a proxy for the real object
	
	
	public void testGetters() throws IOException {
		// First we create an object via the factory so it get's sent to the IdentityMap as well
		realUser = UserFactory.createClean(uid, email, password, userType, version);
		// Create a proxy for the real object
		userProxy = new UserProxy(uid);
		
		assertEquals(userProxy.getUid(), realUser.getUid());
		assertEquals(userProxy.getEmail(), realUser.getEmail());
		assertEquals(userProxy.getPassword(), realUser.getPassword());
		assertEquals(userProxy.getVersion(), realUser.getVersion());
		assertEquals(userProxy.getType(), realUser.getType());
	}
	
	public void testSetters() throws IOException {
		realUser = UserFactory.createClean(uid, "test@example.com", "pass", UserType.USER_ADVERTISER, 0);
		userProxy = new UserProxy(uid);
		userProxy.setEmail(email);
		userProxy.setPassword(password);
		userProxy.setType(userType);
		userProxy.setVersion(version);
		
		assertEquals(userProxy.getEmail(), email);
		assertEquals(userProxy.getUid(), uid);
		assertEquals(userProxy.getEmail(), email);
		assertEquals(userProxy.getPassword(), password);
		assertEquals(userProxy.getVersion(), version);
		assertEquals(userProxy.getType(), userType);
	}
	
	
	
}
