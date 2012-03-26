/**
 * SOEN 490
 * Capstone 2011
 * Test for UserMapperTest.
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
import java.sql.SQLException;
import java.util.List;

import domain.user.User;

import domain.user.UserFactory;

import domain.user.mappers.UserInputMapper;
import domain.user.mappers.UserOutputMapper;
import domain.user.UserType;
import exceptions.LostUpdateException;
import exceptions.MapperException;
import foundation.DbRegistry;
import foundation.tdg.UserTDG;
import junit.framework.TestCase;

public class UserInputMapperTest extends TestCase {

	private final String email = "unique@example.com";
	private final String password = "password";
	private final UserType userType = UserType.USER_NORMAL;
	private final int version = 1;
	private final BigInteger uid = new BigInteger("3785635465657");
	
	public void testFindByEmail() {
		try {
			
			if(!DbRegistry.hasTable(UserTDG.TABLE))
				UserTDG.create();
			
			User user = UserFactory.createNew(email, password, userType);
			
			// Insert the object into the datastore
			UserOutputMapper.insert(user);
			
			// Get a copy of the newly created object from the datastore
			User userCopy = UserInputMapper.findByEmail(user.getEmail());
			
			// Make sure the copy is equivalent to the original
			assertTrue(user.equals(userCopy));
			
			// Remove the test record from the database, which also confirms it was persisted.
			assertEquals(UserOutputMapper.delete(user), 1);
		} catch (SQLException e) {
			e.printStackTrace();
			fail();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			fail();
		} catch (MapperException e) {
			e.printStackTrace();
			fail();
		} catch (LostUpdateException e) {
			e.printStackTrace();
			fail();
		} finally {
			try {
				UserTDG.drop();
			} catch (SQLException e) {
			}
		}
	}
	
	public void testFindAll() throws MapperException {
		try {
			if(!DbRegistry.hasTable(UserTDG.TABLE))
				UserTDG.create();
		
			User user1 = UserFactory.createNew(email, password, userType);
			User user2 = UserFactory.createNew("user2" + email, password, UserType.USER_ADVERTISER);
			
			// Insert the objects into the datastore
			UserOutputMapper.insert(user1);
			UserOutputMapper.insert(user2);
			
			// Get a copy of the newly created object from the datastore
			List<User> users = UserInputMapper.findAll();
			
			// Make sure the copy is equivalent to the original
			assertEquals(users.size(), 2);
			
			// One of the above users created should match the returned
			assertTrue(users.get(0).equals(user1) || users.get(0).equals(user1));
			
		} catch (SQLException e) {
			e.printStackTrace();
			fail();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			fail();
		} finally {
			try {
				UserTDG.drop();
			} catch (SQLException e) {
			}
		}
	}
	
	public void testFind() {
		try {
			if(!DbRegistry.hasTable(UserTDG.TABLE))
				UserTDG.create();
		
			User user = UserFactory.createClean(uid, email, password, userType, version);
			
			// Insert the object into the datastore
			UserOutputMapper.insert(user);
			
			// Get a copy of the newly created object from the datastore
			User userCopy = UserInputMapper.find(uid);
			
			// Make sure the copy is equivalent to the original
			assertTrue(user.equals(userCopy));
			
			// Remove the test record from the database, which also confirms it was persisted.
			assertEquals(UserOutputMapper.delete(user), 1);
		} catch (SQLException e) {
			e.printStackTrace();
			fail();
		} catch (MapperException e) {
			e.printStackTrace();
			fail();
		} catch (LostUpdateException e) {
			e.printStackTrace();
			fail();
		} finally {
			try {
				UserTDG.drop();
			} catch (SQLException e) {
			}
		}
	}
}
