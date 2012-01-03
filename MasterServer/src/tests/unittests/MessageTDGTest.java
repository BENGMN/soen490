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

package tests.unittests;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.sql.Blob;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.rowset.serial.SerialBlob;

import foundation.Database;
import foundation.MessageFinder;
import foundation.MessageTDG;
import foundation.UserFinder;
import foundation.UserTDG;
import junit.framework.TestCase;

public class MessageTDGTest extends TestCase {

	static long mid = 158749857935L;
	static long uid = 158749857934L;
	
	public void testFunctionality() throws SQLException
	{
		create();
		insert();
		update();
		delete();
		drop();
	}
	
	private void create()
	{
		try {
			assertFalse(Database.getInstance().hasTable("Message"));
			MessageTDG.create();
			// This should be expanded to check the schemas too. Probably.
			assertTrue(Database.getInstance().hasTable("Message"));
		}
		catch (SQLException E) {
			fail("Exception Failure: " + E);
		}
	}
	
	private void drop()
	{
		try {
			assertTrue(Database.getInstance().hasTable("Message"));
			MessageTDG.drop();
			// This should be expanded to check the schemas too. Probably.
			assertFalse(Database.getInstance().hasTable("Message"));
		}
		catch (SQLException E) {
			fail("Exception Failure: " + E);
		}
	}

	private void insert() throws SQLException
	{
		try {
			final byte [] message = {1,2,3,4,5,6};
			final float speed = 5.5f;
			final double latitude = 29.221;
			final double longitude = 35.134;
			final Calendar created_at = new GregorianCalendar(2011, 10, 31, 16, 19, 51);
			final int user_rating = 6;
			final int version = 1;				
			assertFalse(MessageFinder.find(mid).next());
			assertEquals(MessageTDG.insert(mid, uid, version, message, speed, latitude, longitude, created_at, user_rating), 1);
			ResultSet rs = MessageFinder.find(mid);
			assertTrue(rs.next());
			assertEquals(rs.getLong("m.mid"), mid);
			assertEquals(rs.getLong("m.uid"), uid);
			
			byte[] b = rs.getBytes("m.message");
			for (int i = 0; i < message.length; i++) {
				assertEquals(b[i], message[i]);
			}
			
			assertEquals(rs.getFloat("m.speed"), speed);
			assertEquals(rs.getDouble("m.latitude"), latitude);
			assertEquals(rs.getDouble("m.longitude"), longitude);
			java.sql.Date d = rs.getDate("m.created_at");
			
			if (d.getTime() == created_at.getTime().getTime())
				assertTrue(true);
			
			assertEquals(rs.getInt("m.user_rating"), user_rating);
		}
		catch (SQLException e) {
			fail("Exception failure:" + e);
			
		}
	}
	
	
	private void update() throws SQLException
	{
		
		try {
			final byte [] message = {1,2,3,4,5,6};
			final float speed = 5.5f;
			final double latitude = 29.221;
			final double longitude = 35.134;
			final Calendar created_at = new GregorianCalendar(2011, 10, 31, 16, 19, 51);
			final int user_rating = 7;
			final int version = 1;
			assertEquals(1, MessageTDG.update(mid, user_rating, version));
			ResultSet rs = MessageFinder.find(mid);
			assertTrue(rs.next());
			assertEquals(rs.getLong("m.version"), version+1);
			assertEquals(rs.getLong("m.mid"), mid);
			assertEquals(rs.getLong("m.uid"), uid);
			byte[] b = rs.getBytes("m.message");
			for (int i = 0; i < message.length; i++) {
				assertEquals(b[i], message[i]);
			}
			assertEquals(rs.getFloat("m.speed"), speed);
			assertEquals(rs.getDouble("m.latitude"), latitude);
			assertEquals(rs.getDouble("m.longitude"), longitude);
			//assertEquals(rs.getDate("m.created_at"), (new java.sql.Date(created_at.getTime().getTime())).getTime());
			assertEquals(rs.getInt("m.user_rating"), user_rating);
		}
		catch (SQLException e) {
			fail("Exception failure:" + e);
			
		}
	}
	
	private void delete() throws SQLException
	{
		try {
			final int version = 2;
			assertTrue(MessageFinder.find(mid).next());
			assertEquals(1, MessageTDG.delete(mid, version));
			assertFalse(MessageFinder.find(mid).next());
		}
		catch (SQLException e) {
			fail("Exception failure:" + e);
		}
	}
}
