/**
 * SOEN 490
 * Capstone 2011
 * Test for MessageMapper.
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

package tests.domain.message;

import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.GregorianCalendar;
import java.util.List;


import domain.message.Message;
import domain.message.MessageFactory;
import domain.message.mappers.MessageInputMapper;
import domain.message.mappers.MessageOutputMapper;
import domain.user.IUser;
import domain.user.User;
import domain.user.UserFactory;
import domain.user.UserType;
import domain.user.mappers.UserOutputMapper;
import exceptions.MapperException;
import foundation.DbRegistry;
import foundation.tdg.MessageTDG;
import foundation.tdg.UserTDG;

import junit.framework.TestCase;

public class MessageInputMapperTest extends TestCase {

	// Data members for a Message
	private BigInteger mid = new BigInteger("159949857935");
	private final byte[] message = {0,1,2,3,4,5};
	private final float speed = 10.0f;
	private final double latitude = 20.0;
	private final double longitude = 10.0;
	private Timestamp createdDate = new Timestamp(new GregorianCalendar(2011, 9, 10).getTimeInMillis());
	private final int userRating = -1;
	
	// Data members for a User
	private final BigInteger uid = new BigInteger("3425635465657");
	private final String email = "example@example.com";
	private final String password = "password";
	private final UserType userType = UserType.USER_NORMAL;
	private final int version = 1;
	
	public void testFind() {
		// Make sure that the message does not already exist in the database
		try {
			
			if(!DbRegistry.hasTable(MessageTDG.TABLE))
				MessageTDG.create();
			
			MessageInputMapper.find(mid);
			fail("Mapper Exception should have been thrown");
			
			// Create a new message using the factory createClean method, ensure it does not do the insertion into the database
			Message newMessage = MessageFactory.createClean(mid, uid, message, speed, latitude, longitude, createdDate, userRating);
			
			// Insert the message into the database
			MessageOutputMapper.insert(newMessage);
			
			Message oldMessage = null;
			
			try {
				// Find the message in the database and compare it to the original
				oldMessage = MessageInputMapper.find(mid);
				assertTrue(newMessage.equals(oldMessage));
			} 
			catch(MapperException e) {
				fail("MapperException thrown when it should not have");
			}
			
			// Remove the message from the database
			assertEquals(MessageOutputMapper.delete(oldMessage), 1);
			
		} catch(MapperException e) {
			// If we make it here the test passes
		} catch (SQLException e) {
			e.printStackTrace();
			fail();
		} finally {
			try {
				MessageTDG.drop();
			} catch (SQLException e) {
			}
		}		
	}
	

	public void testFindByUser() {
		try {
			if(!DbRegistry.hasTable(MessageTDG.TABLE))
				MessageTDG.create();
			// Create some custom data for the messages
			final byte[] message1 = {0,1,2,3,4};
			final byte[] message2 = {5,6,7,8,9};
	
			// Alter the time created, otherwise duplicated keys are created
			Timestamp createdDate1 = new Timestamp(new GregorianCalendar(2011, 9, 10).getTimeInMillis());
			Timestamp createdDate2 = new Timestamp(new GregorianCalendar(2011, 10, 10).getTimeInMillis());
			
			// Create a user object to associate the messages we are about to create to
			IUser user = UserFactory.createClean(uid, email, password, userType, version);
			
			// Create some messages
			Message m1 = MessageFactory.createNew(uid, message1, speed, latitude, longitude, createdDate1, userRating);
			Message m2 = MessageFactory.createNew(uid, message2, speed, latitude, longitude, createdDate2, userRating);
			
			//  Save them to the database so we can find them
			MessageOutputMapper.insert(m1);
			MessageOutputMapper.insert(m2);
			
			// Find the messages from the database and make sure they match the ones we created
			List<Message> messages = MessageInputMapper.findByUser(user);
			
			// Two message should be returned
			assertTrue(messages.size() == 2);
			
			// Make sure that the message were persisted by deleting them
			assertEquals(MessageOutputMapper.delete(m1), 1);
			assertEquals(MessageOutputMapper.delete(m2), 1);
		} catch (SQLException e) {
			e.printStackTrace();
			fail();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			fail();
		} finally {
			try {
				MessageTDG.drop();
			} catch (SQLException e) {	
			}
		}		
	}
	
	public void testFindAll() {
		try {
			
			if(!DbRegistry.hasTable(MessageTDG.TABLE))
				MessageTDG.create();
			
			// Create some custom data for the messages
			final byte[] message1 = {0,1,2,3,4};
			final byte[] message2 = {5,6,7,8,9};

			// Alter the time created, otherwise duplicated keys are created
			Timestamp createdDate1 = new Timestamp(new GregorianCalendar(2011, 9, 10).getTimeInMillis());
			Timestamp createdDate2 = new Timestamp(new GregorianCalendar(2011, 10, 10).getTimeInMillis());
			
			// Create some messages and save them to the database so we can find them
			Message m1 = MessageFactory.createNew(uid, message1, speed, latitude, longitude, createdDate1, userRating);
			Message m2 = MessageFactory.createNew(uid, message2, speed, latitude, longitude, createdDate2, userRating);
			
			// Insert both
			MessageOutputMapper.insert(m1);
			MessageOutputMapper.insert(m2);
			
			// Find all of the messages from the database and make sure they match the ones we created
			List<Message> messages = MessageInputMapper.findAll();
			
			// Two message should be returned
			assertEquals(messages.size(), 2);
			
			// Make sure that the message were persisted by deleting them
			assertEquals(MessageOutputMapper.delete(m1), 1);
			assertEquals(MessageOutputMapper.delete(m2), 1);
			
		} catch (SQLException e) {
			e.printStackTrace();
			fail();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			fail();
		} finally {
			try {
				MessageTDG.drop();
			} catch (SQLException e) {
			}
		}		
	}
	
	public void testFindIdsInProximityOrderRatingLimit() {
		try {			
			MessageTDG.create();
			UserTDG.create();
			
			// Create some custom data for the messages
			final byte[] message1 = {0,1,2,3,4};
			final byte[] message2 = {5,6,7,8,9};

			// Alter the time created, otherwise duplicated keys are created
			Timestamp createdDate1 = new Timestamp(new GregorianCalendar(2011, 9, 10).getTimeInMillis());
			Timestamp createdDate2 = new Timestamp(new GregorianCalendar(2011, 10, 10).getTimeInMillis());
			Timestamp createdDate3 = new Timestamp(new GregorianCalendar(2011, 11, 10).getTimeInMillis());
			
			// Create some users
			User u1 = UserFactory.createNew("email2@camsda.com", "Asdasda", UserType.USER_ADVERTISER);
			User u2 = UserFactory.createNew("email@131.com", "password", UserType.USER_NORMAL);
			
			UserOutputMapper.insert(u1);
			UserOutputMapper.insert(u2);
			
			// Create some messages and save them to the database so we can find them
			Message m1 = MessageFactory.createNew(u1.getUid(), message1, speed, latitude, longitude, createdDate1, 12);
			Message m2 = MessageFactory.createNew(u1.getUid(), message2, speed, latitude, longitude, createdDate2, 6);
			Message m3 = MessageFactory.createNew(u2.getUid(), message2, speed, latitude, longitude, createdDate3, 3);
		
			// Insert both
			MessageOutputMapper.insert(m1);
			MessageOutputMapper.insert(m2);
			MessageOutputMapper.insert(m3);
			
			int radius = 50;
			int limit = 100;
			// Find all of the messages from the database and make sure they match the ones we created
			List<BigInteger> messages = MessageInputMapper.findIdsInProximityOrderRatingLimit(longitude, latitude, radius, limit);
			
			// Two message should be returned
			assertEquals(messages.size(), 3);
			assertTrue(messages.get(0).equals(m1.getMid()));
			assertTrue(messages.get(1).equals(m2.getMid()));
			assertTrue(messages.get(2).equals(m3.getMid()));

		} catch (SQLException e) {
			e.printStackTrace();
			fail();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			fail();
		} finally {
			try {
				MessageTDG.drop();
				UserTDG.drop();
			} catch (SQLException e) {
			}
		}		
	}
	
	public void testFindIdsInProximityOrderRandLimit() {
		try {			

			MessageTDG.create();
			UserTDG.create();
			// Create some custom data for the messages
			final byte[] message1 = {0,1,2,3,4};
			final byte[] message2 = {5,6,7,8,9};

			// Alter the time created, otherwise duplicated keys are created
			Timestamp createdDate1 = new Timestamp(new GregorianCalendar(2011, 9, 10).getTimeInMillis());
			Timestamp createdDate2 = new Timestamp(new GregorianCalendar(2011, 10, 10).getTimeInMillis());
			Timestamp createdDate3 = new Timestamp(new GregorianCalendar(2011, 11, 10).getTimeInMillis());
			
			// Create some users
			User u1 = UserFactory.createNew("email2@camsda.com", "Asdasda", UserType.USER_ADVERTISER);
			User u2 = UserFactory.createNew("email@131.com", "password", UserType.USER_NORMAL);
			
			UserOutputMapper.insert(u1);
			UserOutputMapper.insert(u2);
			
			// Create some messages and save them to the database so we can find them
			Message m1 = MessageFactory.createNew(u1.getUid(), message1, speed, latitude, longitude, createdDate1, 12);
			Message m2 = MessageFactory.createNew(u1.getUid(), message2, speed, latitude, longitude, createdDate2, 6);
			Message m3 = MessageFactory.createNew(u2.getUid(), message2, speed, latitude, longitude, createdDate3, 3);
		
			// Insert both
			MessageOutputMapper.insert(m1);
			MessageOutputMapper.insert(m2);
			MessageOutputMapper.insert(m3);
			
			int radius = 50;
			int limit = 100;
			// Find all of the messages from the database and make sure they match the ones we created
			List<BigInteger> messages = MessageInputMapper.findIdsInProximityOrderRandLimit(longitude, latitude, radius, limit);
			
			// Two message should be returned
			assertEquals(messages.size(), 3);
			
		} catch (SQLException e) {
			e.printStackTrace();
			fail();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			fail();
		} finally {
			try {
				MessageTDG.drop();
				UserTDG.drop();
			} catch (SQLException e) {
			}
		}		
	}
	
	public void testFindIdsInProximityOrderRand() {
		try {			
			MessageTDG.create();
			UserTDG.create();
			
			// Create some custom data for the messages
			final byte[] message1 = {0,1,2,3,4};
			final byte[] message2 = {5,6,7,8,9};

			// Alter the time created, otherwise duplicated keys are created
			Timestamp createdDate1 = new Timestamp(new GregorianCalendar(2011, 9, 10).getTimeInMillis());
			Timestamp createdDate2 = new Timestamp(new GregorianCalendar(2011, 10, 10).getTimeInMillis());
			Timestamp createdDate3 = new Timestamp(new GregorianCalendar(2011, 11, 10).getTimeInMillis());
			
			// Create some users
			User u1 = UserFactory.createNew("email2@camsda.com", "Asdasda", UserType.USER_ADVERTISER);
			User u2 = UserFactory.createNew("email@131.com", "password", UserType.USER_NORMAL);
			
			UserOutputMapper.insert(u1);
			UserOutputMapper.insert(u2);
			
			// Create some messages and save them to the database so we can find them
			Message m1 = MessageFactory.createNew(u1.getUid(), message1, speed, latitude, longitude, createdDate1, 12);
			Message m2 = MessageFactory.createNew(u1.getUid(), message2, speed, latitude, longitude, createdDate2, 6);
			Message m3 = MessageFactory.createNew(u2.getUid(), message2, speed, latitude, longitude, createdDate3, 3);
		
			// Insert both
			MessageOutputMapper.insert(m1);
			MessageOutputMapper.insert(m2);
			MessageOutputMapper.insert(m3);
			
			int radius = 50;
			
			// Find all of the messages from the database and make sure they match the ones we created
			List<BigInteger> messages = MessageInputMapper.findIdsInProximityOrderRand(longitude, latitude, radius);
			
			// Two message should be returned
			assertEquals(messages.size(), 3);
			
		} catch (SQLException e) {
			e.printStackTrace();
			fail();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			fail();
		} finally {
			try {
				MessageTDG.drop();
				UserTDG.drop();
			} catch (SQLException e) {
			}
		}		
	}
	
	public void testFindIdsInProximityOrderDate() {
		try {			
			MessageTDG.create();
			UserTDG.create();
			
			// Create some custom data for the messages
			final byte[] message1 = {0,1,2,3,4};
			final byte[] message2 = {5,6,7,8,9};

			// Alter the time created, otherwise duplicated keys are created
			Timestamp createdDate1 = new Timestamp(new GregorianCalendar(2011, 9, 10).getTimeInMillis());
			Timestamp createdDate2 = new Timestamp(new GregorianCalendar(2011, 10, 10).getTimeInMillis());
			Timestamp createdDate3 = new Timestamp(new GregorianCalendar(2011, 11, 10).getTimeInMillis());
			
			// Create some users
			User u1 = UserFactory.createNew("email2@camsda.com", "Asdasda", UserType.USER_ADVERTISER);
			User u2 = UserFactory.createNew("email@131.com", "password", UserType.USER_NORMAL);
			
			UserOutputMapper.insert(u1);
			UserOutputMapper.insert(u2);
			
			// Create some messages and save them to the database so we can find them
			Message m1 = MessageFactory.createNew(u1.getUid(), message1, speed, latitude, longitude, createdDate1, 12);
			Message m2 = MessageFactory.createNew(u1.getUid(), message2, speed, latitude, longitude, createdDate2, 6);
			Message m3 = MessageFactory.createNew(u2.getUid(), message2, speed, latitude, longitude, createdDate3, 3);
		
			// Insert both
			MessageOutputMapper.insert(m1);
			MessageOutputMapper.insert(m2);
			MessageOutputMapper.insert(m3);
			
			int radius = 50;
			int limit = 100;
			// Find all of the messages from the database and make sure they match the ones we created
			List<BigInteger> messages = MessageInputMapper.findIdsInProximityOrderDate(longitude, latitude, radius, limit);
			
			// Two message should be returned
			assertEquals(messages.size(), 3);
			assertTrue(messages.get(0).equals(m3.getMid()));			
			assertTrue(messages.get(1).equals(m2.getMid()));	
			assertTrue(messages.get(2).equals(m1.getMid()));			
		} catch (SQLException e) {
			e.printStackTrace();
			fail();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			fail();
		} finally {
			try {
				MessageTDG.drop();
				UserTDG.drop();
			} catch (SQLException e) {
			}
		}		
	}
	
	public void testFindIdsInProximityOnlyAdvertisersOrderRand() {
		try {			
			MessageTDG.create();
			UserTDG.create();
			
			// Create some custom data for the messages
			final byte[] message1 = {0,1,2,3,4};
			final byte[] message2 = {5,6,7,8,9};

			// Alter the time created, otherwise duplicated keys are created
			Timestamp createdDate1 = new Timestamp(new GregorianCalendar(2011, 9, 10).getTimeInMillis());
			Timestamp createdDate2 = new Timestamp(new GregorianCalendar(2011, 10, 10).getTimeInMillis());
			
			// Create some users
			User u1 = UserFactory.createNew("email2@camsda.com", "Asdasda", UserType.USER_ADVERTISER);
			User u2 = UserFactory.createNew("email@131.com", "password", UserType.USER_NORMAL);
			
			UserOutputMapper.insert(u1);
			UserOutputMapper.insert(u2);
			// Create some messages and save them to the database so we can find them
			Message m1 = MessageFactory.createNew(u1.getUid(), message1, speed, latitude, longitude, createdDate1, 12);
			Message m2 = MessageFactory.createNew(u1.getUid(), message2, speed, latitude, longitude, createdDate2, 6);
			Message m3 = MessageFactory.createNew(u2.getUid(), message2, speed, latitude, longitude, createdDate2, 3);
		
			// Insert both
			MessageOutputMapper.insert(m1);
			MessageOutputMapper.insert(m2);
			MessageOutputMapper.insert(m3);
			
			int radius = 50;
			
			// Find all of the messages from the database and make sure they match the ones we created
			List<BigInteger> messages = MessageInputMapper.findIdsInProximityOnlyAdvertisersOrderRand(longitude, latitude, radius);
			
			// Two message should be returned
			assertEquals(messages.size(), 2);			
		} catch (SQLException e) {
			e.printStackTrace();
			fail();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			fail();
		} finally {
			try {
				MessageTDG.drop();
				UserTDG.drop();
			} catch (SQLException e) {
			}
		}		
	}
	
	public void testFindIdsInProximityNoAdvertisersOrderRating() {
		try {			
			MessageTDG.create();
			UserTDG.create();
			
			// Create some custom data for the messages
			final byte[] message1 = {0,1,2,3,4};
			final byte[] message2 = {5,6,7,8,9};

			// Alter the time created, otherwise duplicated keys are created
			Timestamp createdDate1 = new Timestamp(new GregorianCalendar(2011, 9, 10).getTimeInMillis());
			Timestamp createdDate2 = new Timestamp(new GregorianCalendar(2011, 10, 10).getTimeInMillis());
			Timestamp createdDate3 = new Timestamp(new GregorianCalendar(2011, 11, 10).getTimeInMillis());
			
			// Create some users
			User u1 = UserFactory.createNew("email2@camsda.com", "Asdasda", UserType.USER_ADVERTISER);
			User u2 = UserFactory.createNew("email@131.com", "password", UserType.USER_NORMAL);
			
			UserOutputMapper.insert(u1);
			UserOutputMapper.insert(u2);
			
			// Create some messages and save them to the database so we can find them
			Message m1 = MessageFactory.createNew(u1.getUid(), message1, speed, latitude, longitude, createdDate1, 12);
			Message m2 = MessageFactory.createNew(u2.getUid(), message2, speed, latitude, longitude, createdDate2, 6);
			Message m3 = MessageFactory.createNew(u2.getUid(), message2, speed, latitude, longitude, createdDate3, 3);
		
			// Insert both
			MessageOutputMapper.insert(m1);
			MessageOutputMapper.insert(m2);
			MessageOutputMapper.insert(m3);
			
			int radius = 50;
			int limit = 100;
			
			// Find all of the messages from the database and make sure they match the ones we created
			List<BigInteger> messages = MessageInputMapper.findIdsInProximityNoAdvertisersOrderRating(longitude, latitude, radius, limit);
			
			// Two message should be returned
			assertEquals(messages.size(), 2);
			assertTrue(messages.get(0).equals(m2.getMid()));			
			assertTrue(messages.get(1).equals(m3.getMid()));	
			
		} catch (SQLException e) {
			e.printStackTrace();
			fail();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			fail();
		} finally {
			try {
				MessageTDG.drop();
				UserTDG.drop();
			} catch (SQLException e) {
			}
		}		
	}
	
	public void testFindIdsInProximityNoAdvertisersOrderDate() {
		try {			
			MessageTDG.create();
			UserTDG.create();
			
			// Create some custom data for the messages
			final byte[] message1 = {0,1,2,3,4};
			final byte[] message2 = {5,6,7,8,9};

			// Alter the time created, otherwise duplicated keys are created
			Timestamp createdDate1 = new Timestamp(new GregorianCalendar(2011, 9, 10).getTimeInMillis());
			Timestamp createdDate2 = new Timestamp(new GregorianCalendar(2011, 10, 10).getTimeInMillis());
			
			// Create some users
			User u1 = UserFactory.createNew("email2@camsda.com", "Asdasda", UserType.USER_ADVERTISER);
			User u2 = UserFactory.createNew("email@131.com", "password", UserType.USER_NORMAL);
			
			UserOutputMapper.insert(u1);
			UserOutputMapper.insert(u2);
			
			// Create some messages and save them to the database so we can find them
			Message m1 = MessageFactory.createNew(u1.getUid(), message1, speed, latitude, longitude, createdDate1, 12);
			Message m2 = MessageFactory.createNew(u2.getUid(), message2, speed, latitude, longitude, createdDate1, 6);
			Message m3 = MessageFactory.createNew(u2.getUid(), message2, speed, latitude, longitude, createdDate2, 3);
		
			// Insert both
			MessageOutputMapper.insert(m1);
			MessageOutputMapper.insert(m2);
			MessageOutputMapper.insert(m3);
			
			int radius = 50;
			int limit = 100;
			
			// Find all of the messages from the database and make sure they match the ones we created
			List<BigInteger> messages = MessageInputMapper.findIdsInProximityNoAdvertisersOrderDate(longitude, latitude, radius, limit);
			
			// Two message should be returned
			assertEquals(messages.size(), 2);
			assertTrue(messages.get(0).equals(m3.getMid()));			
			assertTrue(messages.get(1).equals(m2.getMid()));	
			
		} catch (SQLException e) {
			e.printStackTrace();
			fail();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			fail();
		} finally {
			try {
				MessageTDG.drop();
				UserTDG.drop();
			} catch (SQLException e) {
			}
		}		
	}
	
}
