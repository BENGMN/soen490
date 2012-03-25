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

import java.math.BigInteger;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import foundation.finder.MessageFinder;
import foundation.tdg.MessageTDG;

import junit.framework.TestCase;

public class MessageFinderTest extends TestCase {

	// Test data to load the Database with
	private BigInteger mid1 = new BigInteger("158749857935");
	private BigInteger mid2 = new BigInteger("168749857935");
	private BigInteger mid3 = new BigInteger("178749857935");
	private BigInteger uid = new BigInteger("158749857934");
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
	
	
	public void testFind() {
		try {
			MessageTDG.create();
			MessageTDG.insert(mid1, uid, array, speed, latitude1, longitude1, createdDate, userRating);

			ResultSet rs = MessageFinder.find(mid1);
			
			assertTrue(rs.next());

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
		
	public void testFindAll() {
		try {
			MessageTDG.create();
			
			// add 3 rows
			MessageTDG.insert(mid1, uid, array, speed, latitude1, longitude1, createdDate, userRating);
			MessageTDG.insert(mid2, uid, array, speed, latitude2, longitude3, createdDate, userRating);
			MessageTDG.insert(mid3, uid, array, speed, latitude3, longitude2, createdDate, userRating);
			
			ResultSet rs = MessageFinder.findAll();
			
			assertTrue(rs.next());
			assertTrue(rs.next());
			assertTrue(rs.next());
			assertFalse(rs.next());
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
	
	public void testFindByUser() {
		try {
			MessageTDG.create();
			MessageTDG.insert(mid1, uid, array, speed, latitude1, longitude1, createdDate, userRating);

			// should find one
			ResultSet rs = MessageFinder.findByUser(uid);
			
			assertTrue(rs.next());
			assertFalse(rs.next());
			
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
	
	
	public void testDeleteByTimeAnd() throws SQLException {

		/**
		 * Figure out what today is and when xDaysAgo was 
		 * which should correlate to one more than the days of grace
		 * a message has to live before considered for deletion.
		 */
	
		int days_ago = 8;  // place a positive number here
		
		Date today, xDaysAgo;
		Calendar calendar;
		today = new Date();
		calendar = Calendar.getInstance();
		calendar.setTime(today);
		calendar.add(Calendar.DATE, -days_ago);
		xDaysAgo = calendar.getTime();

		// Format the dates we just found as strings so we can get a time-stamp from them
	    SimpleDateFormat year = new SimpleDateFormat("yyyy");
	    SimpleDateFormat month = new SimpleDateFormat("MM");
	    SimpleDateFormat day = new SimpleDateFormat("dd");
	    
	    int year_from_x_days_ago = Integer.parseInt(year.format(xDaysAgo));
	    int month_from_x_days_ago = Integer.parseInt(month.format(xDaysAgo));
	    int day_from_x_days_ago = Integer.parseInt(day.format(xDaysAgo));

		// Create the time-stamps		
		Timestamp createdDate1 = new Timestamp(new GregorianCalendar(year_from_x_days_ago, month_from_x_days_ago - 1, day_from_x_days_ago ).getTimeInMillis()); // months go from 0-11
		Timestamp createdDate2 = new Timestamp(new GregorianCalendar(year_from_x_days_ago, month_from_x_days_ago - 1, day_from_x_days_ago ).getTimeInMillis()); // months go from 0-11
		
		// Create a low rating which should cause a single message to be deleted
		int lowUserRating = (int)Math.pow(2, (double)days_ago-(days_ago-1)) - 1;
		// Create a high rating which should cause the message to persist
		int highUserRating = (int)Math.pow(2, (double)days_ago-(days_ago-1)) + 1;  // We use 1 less than days ago since it's been adjusted to be 1 more than days of grace
																				   // and we add 1 at the end so that we ensure the message lives on

		MessageTDG.create();
		
		MessageTDG.insert(mid1, uid, array, speed, latitude1, longitude1, createdDate1, lowUserRating);
		MessageTDG.insert(mid2, uid, array, speed, latitude1, longitude1, createdDate2, highUserRating);
		
		ResultSet rs = MessageFinder.findByTimeAndRatingToPurge();
		
		assertTrue(rs.next());
		assertFalse(rs.next());
		
		MessageTDG.drop();
	}
}
