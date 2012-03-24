package tests.domain.user;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

import application.IOUtils;

import domain.user.User;
import domain.user.UserFactory;

import domain.user.mappers.UserInputMapper;
import domain.user.mappers.UserOutputMapper;
import domain.user.UserType;
import exceptions.LostUpdateException;
import exceptions.MapperException;
import foundation.tdg.UserTDG;
import junit.framework.TestCase;

public class UserOutputMapperTest extends TestCase {

	private final String email = "example@example.com";
	private final String password = "password";
	private final UserType userType = UserType.USER_NORMAL;
	private final int version = 1;
	private final BigInteger uid = new BigInteger("3325635465657");
	private User user = null;
	
	public void testInsert() {
		try {
			UserTDG.create();
			
			user = UserFactory.createClean(uid, email, password, userType, version);
			
			UserOutputMapper.insert(user);
			
			User userCopy = UserInputMapper.find(uid);
			assertEquals(user.equals(userCopy), true);
			try {
				UserOutputMapper.insert(userCopy);
				fail();
			} catch (SQLException e) {
				// should reach here
			}
						
		} catch (SQLException e) {
			e.printStackTrace();
			fail();
		} catch (MapperException e) {
			e.printStackTrace();
			fail();
		} finally {
			try {
				UserTDG.drop();
			} catch (SQLException e) {				
			}
		}
	
	}
	
	public void testDelete() {
		try {
			UserTDG.create();
			
			user = UserFactory.createClean(uid, email, password, userType, version);
			
			UserOutputMapper.insert(user);
			
			User userCopy = UserInputMapper.find(uid);
			assertEquals(user.equals(userCopy), true);
			
			assertEquals(UserOutputMapper.delete(userCopy), 1);
						
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
	
	public void testUpdate()  {
		try {
			UserTDG.create();
			
			user = UserFactory.createClean(uid, email, password, userType, version);
			
			UserOutputMapper.insert(user);
	
			User userCopy = UserInputMapper.find(uid);
			assertEquals(user.equals(userCopy), true);
			
			// after update
			user.setEmail(email + " new email ");
			UserOutputMapper.update(user);
			// increment version to simulate what the update does
			user.setVersion(user.getVersion() + 1);
			
			userCopy = UserInputMapper.find(uid);
			
			// We can't compare objects because the ones returned from finder does not contain password
			assertTrue(user.getEmail().equals(userCopy.getEmail()));
			assertTrue(user.getUid().equals(userCopy.getUid()));
			assertTrue(user.getVersion() == userCopy.getVersion());
			assertTrue(user.getType() == userCopy.getType());
			
			assertEquals(UserOutputMapper.delete(userCopy), 1);
						
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
	
	public void testUpdateHashPassword() {
		try {
			UserTDG.create();
			
			user = UserFactory.createClean(uid, email, IOUtils.hashPassword(password), userType, version);
			
			int inserted = UserOutputMapper.insert(user);
						
			assertEquals(1, inserted);
		} catch (SQLException e) {
			e.printStackTrace();
			fail();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				UserTDG.drop();
			} catch (SQLException e) {				
			}
		}
	}
}
