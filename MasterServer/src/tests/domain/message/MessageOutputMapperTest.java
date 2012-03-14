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
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.GregorianCalendar;

import domain.message.Message;
import domain.message.MessageFactory;
import domain.message.mappers.MessageInputMapper;
import domain.message.mappers.MessageOutputMapper;
import exceptions.MapperException;
import junit.framework.TestCase;

public class MessageOutputMapperTest extends TestCase {
	
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
	
	public void testInsertAndDeleteMessage() throws SQLException {
		
		// Create a new message using the factory createClean method, ensure it does not do the insertion into the database
		Message newMessage = MessageFactory.createClean(mid, uid, message, speed, latitude, longitude, createdDate, userRating);
		
		// Insert the message into the database
		MessageOutputMapper.insert(newMessage);
		
		Message oldMessage = null;
		
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

	}
	
	public void testUpdateMessage() throws SQLException {
		// Create a new message using the factory createClean method, ensure it does not do the insertion into the database
		Message newMessage = MessageFactory.createClean(mid, uid, message, speed, latitude, longitude, createdDate, userRating);
		
		// Insert the message into the database
		MessageOutputMapper.insert(newMessage);
		
		// Update the message object
		newMessage.setUserRating(10);
		
		// Send the object to the database to be updated, make sure 1 record is affected
		assertEquals(MessageOutputMapper.update(newMessage), 1);
		
		Message oldMessage = null;
		
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

	}
	
	public void testDeleteMessageByID() throws SQLException {

		// Create a new message using the factory createClean method, ensure it does not do the insertion into the database
		Message newMessage = MessageFactory.createClean(mid, uid, message, speed, latitude, longitude, createdDate, userRating);
		
		// Insert the message into the database
		MessageOutputMapper.insert(newMessage);
		
		Message oldMessage = null;
		
		try {
			// Find the message in the database and compare it to the original
			oldMessage = MessageInputMapper.find(mid);
			assertEquals(newMessage.equals(oldMessage), true);
		} 
		catch(MapperException e) {
			fail("MapperException thrown when it should not have");
		}
		
		// Remove the message from the database
		assertEquals(MessageOutputMapper.delete(oldMessage.getMid()), 1);	
	}

}
