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

package tests.domain.user;

import static org.junit.Assert.*;

import org.junit.Test;

import domain.user.User;
import domain.user.UserIdentityMap;
import domain.user.UserType;

public class UserIdentityMapTest {

	@Test
	public void testMap()
	{
		// Get a unique instance of the map
		UserIdentityMap map = UserIdentityMap.getUniqueInstance();
		final long uid = 2L;
		// Create a new user
		User user = new User(uid, "", "", UserType.USER_NORMAL, 0);
		
		// Make sure the user is not already in the map
		assertNull(map.get(uid));
		
		// Place the user in the map
		map.put(uid, user);
		
		// Make sure the map returns the user upon request
		assertNotNull(map.get(uid));
	}
}
