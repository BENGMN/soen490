/**
 * SOEN 490
 * Capstone 2011
 * Test for MessageTDG.
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

package tests.foundation;

import java.util.GregorianCalendar;
import java.math.BigInteger;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import junit.framework.TestCase;

import foundation.finder.MessageFinder;
import foundation.tdg.MessageTDG;

public class MessageTDGTest extends TestCase {

	private BigInteger mid1 = new BigInteger("158339857935");
	private BigInteger mid2 = new BigInteger("13124123131231231");
	private  BigInteger uid = new BigInteger("158749857934");

	private byte[] message = { 1, 2, 3, 4, 5, 6 };
	private float speed = 5.5f;
	private double latitude = 29.221;
	private double longitude = 35.134;
	private Timestamp createdDate = new Timestamp(new GregorianCalendar(2011, 9, 10).getTimeInMillis());
	private int user_rating = 6;
	
	public void testInsert() {
		try {
			MessageTDG.create();
			MessageTDG.insert(mid1, uid, message, speed, latitude, longitude, createdDate, user_rating);
			try {
				//add with same id
				MessageTDG.insert(mid1, uid, message, speed, latitude, longitude, new Timestamp(System.currentTimeMillis()), user_rating);
				fail();
			} catch (SQLException e) {
				//should reach here
			}
			
			// add a second one
			MessageTDG.insert(mid2, uid, message, speed, latitude, longitude, createdDate, user_rating);
		} catch (SQLException e){
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
			MessageTDG.insert(mid1, uid, message, speed, latitude, longitude, createdDate, user_rating);

			int newRating = user_rating + 1;
			MessageTDG.update(mid1, newRating);
		
			//check that update happenned
			ResultSet rs = MessageFinder.find(mid1);
			
			rs.next();
			
			int rating = rs.getInt("m.user_rating");
			
			assertEquals(rating, newRating);
			
			// try to update some row that doesn't exist
			assertEquals(0, MessageTDG.update(mid2, rating));
			rs.close();
		} catch (SQLException e){
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
			MessageTDG.insert(mid1, uid, message, speed, latitude, longitude, createdDate, user_rating);
		
			assertEquals(1, MessageTDG.delete(mid1));
			// second should not find anything, it's been deleted
			assertEquals(0, MessageTDG.delete(mid1)); 
			
		} catch (SQLException e){
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
			MessageTDG.insert(mid1, uid, message, speed, latitude, longitude, createdDate, user_rating);

			int newRating = user_rating + 1;
			MessageTDG.incrementRating(mid1);
		
			//check that update happenned
			ResultSet rs = MessageFinder.find(mid1);
			
			rs.next();
			
			int rating = rs.getInt("m.user_rating");
			
			assertEquals(rating, newRating);
			rs.close();
		} catch (SQLException e){
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
			MessageTDG.insert(mid1, uid, message, speed, latitude, longitude, createdDate, user_rating);

			int newRating = user_rating - 1;
			MessageTDG.decrementRating(mid1);
		
			//check that update happenned
			ResultSet rs = MessageFinder.find(mid1);
			
			rs.next();
			
			int rating = rs.getInt("m.user_rating");
			
			assertEquals(rating, newRating);
			rs.close();
		} catch (SQLException e){
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
