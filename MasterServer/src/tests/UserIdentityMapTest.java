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

import static org.junit.Assert.*;

import org.junit.Test;

import domain.user.User;
import domain.user.UserIdentityMap;
import domain.user.UserType;

public class UserIdentityMapTest {
	
	@Test
	public void testMap()
	{
		UserIdentityMap map = UserIdentityMap.getUniqueInstance();
		final long uid = 2L;
		User user = new User(uid, "", "", UserType.USER_NORMAL, 0);
		assertNull(map.get(uid));
		map.put(uid, user);
		assertNotNull(map.get(uid));
	}
}
