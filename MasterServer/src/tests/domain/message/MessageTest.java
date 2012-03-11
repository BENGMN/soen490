/**
 * SOEN 490
 * Capstone 2011
 * Test for Messsage domain object.
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

package tests.domain.message;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.GregorianCalendar;

import domain.message.Message;
import domain.user.IUser;
import domain.user.User;
import domain.user.UserType;
import junit.framework.TestCase;


public class MessageTest extends TestCase {
	
	// Attributes for a User
	private final BigInteger uid = new BigInteger("3425635465657");
	private final String email = "example@example.com";
	private final String password = "password";
	private final UserType userType = UserType.USER_NORMAL;
	private final int userVersion = 1;
	

	// Attributes for a Message
	private BigInteger mid = new BigInteger("158749857935");
	private IUser owner = new User(uid, email, password, userType, userVersion);
	private byte[] message1 = { 1, 2, 3, 4, 5, 6 };
	private byte[] message2 = { 1, 2, 3, 4, 5, 6 };
	private float speed = 5.5f;
	final double latitude = 29.221;
	final double longitude = 35.134;
	private Timestamp createdAt = new Timestamp(new GregorianCalendar(2011, 9, 10).getTimeInMillis());
	private int userRating = 7;
	
	public void testGetters() {
		Message msg = new Message(mid, owner, message1, speed, latitude, longitude, createdAt, userRating);
		assertEquals(msg.getMid(), mid);
		assertEquals(msg.getOwner(), owner);
		assertEquals(msg.getMessage(), message1);
		assertEquals(msg.getSpeed(), speed);
		assertEquals(msg.getLatitude(), latitude);
		assertEquals(msg.getLongitude(),  longitude);
		assertEquals(msg.getCreatedAt(), createdAt);
		assertEquals(msg.getUserRating(), userRating);
	}
	
	public void testSetters() {
		Message msg = new Message(mid, owner, message1, speed, latitude, longitude, createdAt, userRating);
		msg.setUserRating(10);
		
		assertEquals(msg.getUserRating(), 10);
	}
	
	public void testEquals() {
		Message m1 = new Message(mid, owner, message1, speed, latitude, longitude, createdAt, userRating);
		Message m2 = new Message(mid, owner, message1, 10.0f, latitude, longitude, createdAt, userRating);
		Message m3 = new Message(mid, owner, message2, speed, latitude, longitude, createdAt, userRating);
		
		assertEquals("Should return false when compared to a null",m1.equals(null), false);
		assertEquals("Should return false when compared to a different object", m1.equals(m2), false);
		assertEquals("Should return true when compared with the same object", m1.equals(m1), true);
		assertEquals("Should return true when compared with another object with the same attributes", m1.equals(m3), true);
	}
}
