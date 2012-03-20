/**
 * SOEN 490
 * Capstone 2011
 * Message output mapper test class
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

import domain.message.Message;
import domain.message.MessageFactory;
import domain.message.mappers.MessageInputMapper;
import domain.message.mappers.MessageOutputMapper;
import exceptions.MapperException;
import foundation.tdg.MessageTDG;
import junit.framework.TestCase;

public class MessageOutputMapperTest extends TestCase {
	
	// Data members for a Message
	private final byte[] message = {0,1,2,3,4,5};
	private final float speed = 10.0f;
	private final double latitude = 20.0;
	private final double longitude = 10.0;
	private Timestamp createdDate = new Timestamp(new GregorianCalendar(2011, 9, 10).getTimeInMillis());
	private final int userRating = -1;
	
	// Data members for a User
	private final BigInteger uid = new BigInteger("3425635465657");
	
	public void testInsert() {
		try {
			MessageTDG.create();
			
			// Create a new message using the factory createClean method, ensure it does not do the insertion into the database
			Message newMessage = MessageFactory.createNew(uid, message, speed, latitude, longitude, createdDate, userRating);
			
			// Insert the message into the database
			MessageOutputMapper.insert(newMessage);
			
			Message oldMessage = null;
			BigInteger mid = newMessage.getMid();
			
			try {
				// Find the message in the database and compare it to the original
				oldMessage = MessageInputMapper.find(mid);
				assertTrue(newMessage.equals(oldMessage));
			} 
			catch(MapperException e) {
				fail("MapperException thrown when it should not have");
			}
			
			try {
				MessageOutputMapper.insert(oldMessage);
				fail();
			} catch (SQLException e ) {
				//should reach this
			}
			
			// Remove the message from the database
			assertEquals(MessageOutputMapper.delete(oldMessage), 1);
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
	
	public void testUpdate() {
		try {
			MessageTDG.create();
			// Create a new message using the factory createNew method
			Message newMessage = MessageFactory.createNew(uid, message, speed, latitude, longitude, createdDate, userRating);
			
			// Insert the message into the database
			MessageOutputMapper.insert(newMessage);
			
			// Update the message object
			newMessage.setUserRating(10);
			
			// Send the object to the database to be updated, make sure 1 record is affected
			assertEquals(MessageOutputMapper.update(newMessage), 1);
			
			Message oldMessage = null;
			BigInteger mid = newMessage.getMid();
			
			try {
				// Find the message in the database and compare it to the original
				oldMessage = MessageInputMapper.find(mid);
				assertEquals(newMessage.equals(oldMessage), true);
			} 
			catch(MapperException e) {
				fail("MapperException thrown when it should not have");
			}
			
			// Remove the message from the database
			assertEquals(MessageOutputMapper.delete(oldMessage), 1);
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
	
	public void testIncrement() {
		try {
			MessageTDG.create();
			// Create a new message using the factory createNew method
			Message newMessage = MessageFactory.createNew(uid, message, speed, latitude, longitude, createdDate, userRating);
			
			// Insert the message into the database
			MessageOutputMapper.insert(newMessage);
			
			// The update should return rows affected count of 1
			assertEquals(MessageOutputMapper.incrementRating(newMessage), 1);
		
			Message oldMessage = null;
			
			BigInteger mid = newMessage.getMid();
			newMessage.setUserRating(newMessage.getUserRating() + 1);
			
			try {
				// Find the message in the database and compare it to the original
				oldMessage = MessageInputMapper.find(mid);
				assertEquals(newMessage.equals(oldMessage), true);
			} 
			catch(MapperException e) {
				fail("MapperException thrown when it should not have");
			}
			
			// Remove the message from the database
			assertEquals(MessageOutputMapper.delete(oldMessage), 1);
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
	
	public void testDecrement() {
		try {
			MessageTDG.create();
			// Create a new message using the factory createNew method
			Message newMessage = MessageFactory.createNew(uid, message, speed, latitude, longitude, createdDate, userRating);
			
			// Insert the message into the database
			MessageOutputMapper.insert(newMessage);
			
			// The update should return rows affected count of 1
			assertEquals(MessageOutputMapper.decrementRating(newMessage), 1);
		
			Message oldMessage = null;
			
			BigInteger mid = newMessage.getMid();
			newMessage.setUserRating(newMessage.getUserRating() - 1);
			
			try {
				// Find the message in the database and compare it to the original
				oldMessage = MessageInputMapper.find(mid);
				assertEquals(newMessage.equals(oldMessage), true);
			} 
			catch(MapperException e) {
				fail("MapperException thrown when it should not have");
			}
			
			// Remove the message from the database
			assertEquals(MessageOutputMapper.delete(oldMessage), 1);
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
	
	public void testDelete() {
		try {
			MessageTDG.create();
			// Create a new message using the factory createNew method
			Message newMessage = MessageFactory.createNew(uid, message, speed, latitude, longitude, createdDate, userRating);
			
			// Insert the message into the database
			MessageOutputMapper.insert(newMessage);
						
			// Remove the message from the database
			assertEquals(MessageOutputMapper.delete(newMessage), 1);
			assertEquals(MessageOutputMapper.delete(newMessage), 0);
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
	
	public void testDeleteById() {
		try {
			MessageTDG.create();
			// Create a new message using the factory createNew method
			Message newMessage = MessageFactory.createNew(uid, message, speed, latitude, longitude, createdDate, userRating);
			
			// Insert the message into the database
			MessageOutputMapper.insert(newMessage);
		
			// Remove the message from the database
			assertEquals(MessageOutputMapper.delete(newMessage.getMid()), 1);
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
}
