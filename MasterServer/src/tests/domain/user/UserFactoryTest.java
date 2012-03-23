/**
 * SOEN 490
 * Capstone 2011
 * Test for UserFactory
 * Team members: 	
 * 			Sotirios Delimanolis
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

import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import domain.user.User;
import domain.user.UserFactory;
import domain.user.UserType;
import junit.framework.TestCase;

public class UserFactoryTest extends TestCase {

	private final BigInteger uid = new BigInteger("3425635465657");
	private final String email = "example@example.com";
	private final String password = "password";
	private final UserType userType = UserType.USER_NORMAL;
	private final int version = 1;
	
	public void testCreateClean() {
		
		User user = UserFactory.createClean(uid, email, password, userType, version);
		
		// Make sure all of the attributes were properly assigned
		assertTrue(user.getUid().equals(uid));
		assertEquals(user.getEmail(), email);
		assertEquals(user.getPassword(), password);
		assertEquals(user.getVersion(), version);
		assertEquals(user.getType(), userType);
	}
	
	public void testCreateNew() {
		try {
			User user = UserFactory.createNew(email, password, userType);
			
			// Make sure all of the attributes were properly assigned
			assertEquals(user.getEmail(), email);
			assertEquals(user.getPassword(), password);
			assertEquals(user.getVersion(), version);
			assertEquals(user.getType(), userType);
			
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			fail();
		}
	}
	
}
