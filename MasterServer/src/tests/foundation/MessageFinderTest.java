/**
 * SOEN 490
 * Capstone 2011
 * Test for MessageFinder.
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

package tests.foundation;

import java.io.IOException;
import java.math.BigInteger;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.List;

import domain.message.Message;
import domain.message.MessageFactory;
import domain.message.MessageOutputMapper;
import foundation.MessageFinder;

import junit.framework.TestCase;

public class MessageFinderTest extends TestCase {

	// Test data to load the Database with
	private BigInteger mid1 = new BigInteger("158749857935");
	private BigInteger mid2 = new BigInteger("168749857935");
	private BigInteger mid3 = new BigInteger("178749857935");
	private long uid = 158749857934L;
	private byte array[] = {0,1,2,3,4,5};
	private float speed = 10.0f;
	private double latitude1 = 45.4562;	// Brossard 
	private double longitude1 = -73.4700;
	private double latitude2 = 45.523;	// Montreal
	private double longitude2 = -73.556;
	private double latitude3 = 40.726;	// New York
	private double longitude3 = -74.004;
	private Timestamp createdDate = new Timestamp(new GregorianCalendar(2011, 9, 10).getTimeInMillis());
	private int userRating = -1;
	
	private Message newMessage1 = MessageFactory.createClean(mid1, uid, array, speed, latitude1, longitude1, createdDate, userRating, 1);
	private Message newMessage2 = MessageFactory.createClean(mid2, uid, array, speed, latitude2, longitude2, createdDate, userRating, 1);
	private Message newMessage3 = MessageFactory.createClean(mid3, uid, array, speed, latitude3, longitude3, createdDate, userRating, 1);
	
	private void createTestMessages() throws IOException, SQLException {

		MessageOutputMapper.insert(newMessage1);
		MessageOutputMapper.insert(newMessage2);
		MessageOutputMapper.insert(newMessage3);
	}
	
	public void deleteTestMessages() throws IOException, SQLException {
		MessageOutputMapper.delete(newMessage1);
		MessageOutputMapper.delete(newMessage2);
		MessageOutputMapper.delete(newMessage3);
	}
	
	
	public void testFindAll() throws SQLException, IOException {
		createTestMessages();
		List<Message> messages = new LinkedList<Message>();
		ResultSet rs = MessageFinder.findAll();
		while(rs.next()) {
			Message m = getMessage(rs);
			messages.add(m);
		}
		
		assertEquals(messages.size() == 3, true);
		deleteTestMessages();
	}
	
	public void testFind() throws SQLException, IOException {
		createTestMessages();
		ResultSet rs = MessageFinder.find(mid1);
		List<Message> message = new LinkedList<Message>();
		while(rs.next()) {
			message.add(getMessage(rs));
		}
		System.out.println("Messages returned = "+message.size());
		assertEquals(message.size(), 1);
		deleteTestMessages();
	}
	
	private static Message getMessage(ResultSet rs) throws SQLException {
		Timestamp date = rs.getTimestamp("m.created_at");
		
		BigInteger mid = rs.getBigDecimal("m.mid").toBigInteger();
		Message message;
		
		message = MessageFactory.createClean(mid,
								 rs.getLong("m.uid"),
								 rs.getBytes("m.message"),
								 rs.getFloat("m.speed"),
								 rs.getDouble("m.latitude"),
								 rs.getDouble("m.longitude"),
								 date,
								 rs.getInt("m.user_rating"),
								 rs.getInt("m.version"));
		return message;
	}
}
