/**
 * SOEN 490
 * Capstone 2011
 * Test for UserMapperTest.
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
import domain.UserMapper;
import junit.framework.TestCase;

public class UserMapperTest extends TestCase {
	final long uid = 3425635465657L;
	public void testFind()
	{
		final String email = "example@example.com";
		final String password = "password";
		final User.UserType userType = User.UserType.USER_NORMAL;
		final int version = 1;
		User user = new User(uid, email, password, userType, version);
		UserMapper.insert(user);
		User dUser = UserMapper.find(uid);
		assertEquals(dUser.getUid(), uid);
		assertEquals(dUser.getEmail(), email);
		assertEquals(dUser.getPassword(), password);
		assertEquals(dUser.getVersion(), version);
		assertEquals(dUser.getType(), userType);
	}
	
	public void testInsert()
	{
		final String email = "example@example.com";
		final String password = "password";
		final User.UserType userType = User.UserType.USER_NORMAL;
		final int version = 1;
		User user = new User(uid, email, password, userType, version);
		UserMapper.insert(user);
		User dUser = UserMapper.find(uid);
		assertEquals(dUser.getUid(), uid);
		assertEquals(dUser.getEmail(), email);
		assertEquals(dUser.getPassword(), password);
		assertEquals(dUser.getVersion(), version);
		assertEquals(dUser.getType(), userType);
	}
	
	public void testUpdate()
	{
		final String email = "example@example.com";
		final String password = "password";
		final User.UserType userType = User.UserType.USER_NORMAL;
		final int version = 1;
		User user = new User(uid, "", "", null, 0);
		User updatedUser = new User(uid, email, password, userType, version);
		UserMapper.insert(user);
		UserMapper.update(updatedUser);
		assertEquals(updatedUser.getUid(), uid);
		assertEquals(updatedUser.getEmail(), email);
		assertEquals(updatedUser.getPassword(), password);
		assertEquals(updatedUser.getVersion(), version);
		assertEquals(updatedUser.getType(), userType);

	}
	
	public void testDelete()
	{
		User user = new User(uid, "", "", null, 0);
		UserMapper.insert(user);
		assertNotNull(UserMapper.find(uid));
		UserMapper.delete(user);
		assertNull(UserMapper.find(uid));
	}
}
