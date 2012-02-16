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
	
	
	public void testConstructorAndGetUid() throws IOException {
		// First we create an object via the factory so it get's sent to the IdentityMap as well
		User realUser = UserFactory.createClean(uid, email, password, userType, version);
		
		// Create a proxy for the real object
		UserProxy userProxy = new UserProxy(uid);
		
		// Get the ID from the proxy and match it to the realUsers
		assertEquals(userProxy.getUid(), realUser.getUid());
	}
	
}
