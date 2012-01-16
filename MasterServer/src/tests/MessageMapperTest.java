/**
 * SOEN 490
 * Capstone 2011
 * Test for MessageMapper.
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

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.GregorianCalendar;

import org.junit.Test;

import domain.message.Message;
import domain.message.MessageFactory;
import domain.message.MessageInputMapper;
import domain.message.MessageOutputMapper;

import foundation.Database;
import static org.junit.Assert.*;

public class MessageMapperTest {
	
	static long mid = 158749857935L;
	static long uid = 158749857934L;
	
	@Test
	public void testFunctionality() throws SQLException, IOException
	{
		boolean previousDatabase = Database.getInstance().isDatabaseCreated();
		if (!previousDatabase)
			Database.getInstance().createDatabase();
		create();
		update();
		delete();
		if (!previousDatabase)
			Database.getInstance().dropDatabase();
	}
	
	private void create() throws SQLException, IOException
	{
		final byte array[] = {0,1,2,3,4,5};
		final float speed = 10.0f;
		final double latitude = 20.0;
		final double longitude = 10.0;
		Timestamp createdDate = new Timestamp(new GregorianCalendar(2011, 9, 10).getTimeInMillis());
		final int userRating = -1;
		assertNull(MessageInputMapper.find(mid));
		Message newMessage = MessageFactory.createClean(mid, uid, array, speed, latitude, longitude, createdDate, userRating, 1);
		MessageOutputMapper.insert(newMessage);
		Message oldMessage = MessageInputMapper.find(mid);
		assertEquals(newMessage, oldMessage);
	}
	
	private void update() throws SQLException, IOException
	{
		Message oldMessage = MessageInputMapper.find(mid);
		assertNotNull(oldMessage);
		final byte array[] = {0,1,2,3,4,5};
		final float speed = 10.0f;
		final double latitude = 20.0;
		final double longitude = 10.0;
		Timestamp createdDate = new Timestamp(new GregorianCalendar(2011, 9, 10).getTimeInMillis());
		final int userRating = -1;
		assertArrayEquals(array, oldMessage.getMessage());
		assertEquals(speed, oldMessage.getSpeed(), 0.00001);
		assertEquals(latitude, oldMessage.getLatitude(), 0.00001);
		assertEquals(longitude, oldMessage.getLongitude(), 0.00001);
		assertEquals(createdDate, oldMessage.getCreatedAt());
		assertEquals(userRating, oldMessage.getUserRating());
		assertEquals(1, oldMessage.getVersion());
		final int newUserRating = 1;
		oldMessage.setUserRating(newUserRating);
		MessageOutputMapper.update(oldMessage);
		Message newMessage = MessageInputMapper.find(mid);
		assertNotNull(newMessage);
		assertArrayEquals(array, oldMessage.getMessage());
		assertEquals(speed, oldMessage.getSpeed(), 0.00001);
		assertEquals(latitude, oldMessage.getLatitude(), 0.00001);
		assertEquals(longitude, oldMessage.getLongitude(), 0.00001);
		assertEquals(createdDate, oldMessage.getCreatedAt());
		assertEquals(newUserRating, oldMessage.getUserRating());
		//assertEquals(2, oldMessage.getVersion());
	}
	
	private void delete() throws SQLException, IOException
	{
		Message oldMessage = MessageInputMapper.find(mid);
		assertNotNull(oldMessage);
		MessageOutputMapper.delete(oldMessage);
		assertNull(MessageInputMapper.find(mid));
	}
	
}
