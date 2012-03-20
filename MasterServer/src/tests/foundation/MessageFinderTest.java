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
}
