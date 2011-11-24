/**
 * SOEN 490
 * Capstone 2011
 * Test for UserIdentityMap.
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

import domain.user.User;
import domain.user.UserIdentityMap;
import junit.framework.TestCase;

public class UserIdentityMapTest extends TestCase {
	
	public void testMap()
	{
		UserIdentityMap map = UserIdentityMap.getInstance();
		final long uid = 2L;
		User user = new User(uid, "", "", User.UserType.USER_NORMAL, 0);
		assertNull(map.get(uid));
		map.put(uid, user);
		assertNotNull(map.get(uid));
	}
}
