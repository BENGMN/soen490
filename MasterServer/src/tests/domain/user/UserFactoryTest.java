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

import java.io.IOException;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

import domain.user.User;
import domain.user.UserFactory;
import domain.user.UserIdentityMap;
import domain.user.UserType;
import domain.user.mappers.UserOutputMapper;
import junit.framework.TestCase;

public class UserFactoryTest extends TestCase {

	private final BigInteger uid = new BigInteger("3425635465657");
	private final String email = "example@example.com";
	private final String password = "password";
	private final UserType userType = UserType.USER_NORMAL;
	private final int version = 1;
	
	/**
	 * Test the factory's create clean method here.
	 * It should return a user object which should be cached
	 * within the UserIdentityMap and the object should also be able to be located within the database.
	 * @throws IOException
	 * @throws SQLException 
	 */
	public void testCreateClean() throws IOException, SQLException {
		User user = UserFactory.createClean(uid, email, password, userType, version);
		
		// Make sure all of the attributes were properly assigned
		assertEquals(user.getUid(), uid);
		assertEquals(user.getEmail(), email);
		assertEquals(user.getPassword(), password);
		assertEquals(user.getVersion(), version);
		assertEquals(user.getType(), userType);
		
		// Make sure the User object was created properly and placed into the cache
		assertEquals(user, UserIdentityMap.getUniqueInstance().get(uid));
		
		// make sure the user is not in the database
		assertEquals(UserOutputMapper.delete(user), 0); 
	}
	
	/**
	 * Test the create new method which should assign a new Uid to object and place it in the identity map
	 * We can't test the Uid since it can't be known before running the test each time without first checking the database.
	 * The version however should be returned as 1.
	 * @throws IOException
	 * @throws SQLException
	 * @throws NoSuchAlgorithmException 
	 */
	public void testCreateNew() throws IOException, SQLException, NoSuchAlgorithmException {
		User user = UserFactory.createNew(email, password, userType);
		
		// Make sure all of the attributes were properly assigned
		assertEquals(user.getEmail(), email);
		assertEquals(user.getPassword(), password);
		assertEquals(user.getVersion(), version);
		assertEquals(user.getType(), userType);
		
		// Make sure the User object was created properly and placed into the cache
		assertEquals(user, UserIdentityMap.getUniqueInstance().get(uid));
		
		// As a final test make sure the user is deleted from the database
		assertEquals(UserOutputMapper.delete(user), 1);
	}
	
}
