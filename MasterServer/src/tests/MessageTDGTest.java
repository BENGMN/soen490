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

package tests;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.sql.Blob;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.rowset.serial.SerialBlob;

import foundation.MessageTDG;
import foundation.UserTDG;
import junit.framework.TestCase;

public class MessageTDGTest extends TestCase {

	static long mid = 158749857935L;
	static long uid = 158749857934L;
	
	public void testFunctionality()
	{
		testInsert();
		testUpdate();
		testDelete();
	}

	private void testInsert()
	{
		try {
			final byte [] message = {1,2,3,4,5,6};
			final float speed = 5.5f;
			final double latitude = 29.221;
			final double longitude = 35.134;
			final Calendar created_at = new GregorianCalendar(2011, 10, 31, 16, 19, 51);
			final int user_rating = 6;
			final int version = 1;
			assertNull(MessageTDG.find(mid));
			assertNull(UserTDG.find(uid));
			assertEquals(MessageTDG.insert(version, mid, uid, message, speed, latitude, longitude, created_at, user_rating), 1);
			ResultSet rs = MessageTDG.find(mid);
			assertEquals(rs.getLong("m.mid"), mid);
			assertEquals(rs.getLong("m.uid"), uid);
			assertEquals(rs.getBytes("m.message"), new SerialBlob(message));
			assertEquals(rs.getFloat("m.speed"), speed);
			assertEquals(rs.getDouble("m.latitude"), latitude);
			assertEquals(rs.getDouble("m.longitude"), longitude);
			assertEquals(rs.getDate("m.created_at"), new java.sql.Date(created_at.getTime().getTime()));
			assertEquals(rs.getInt("m.user_rating"), user_rating);
		}
		catch (SQLException e) {
			fail("Exception failure:" + e);
		}
	}
	
	private void testUpdate()
	{
		try {
			final byte [] message = {6,5,4,3,2,1};
			final float speed = 6.5f;
			final double latitude = 39.221;
			final double longitude = 25.134;
			final Calendar created_at = new GregorianCalendar(2011, 11, 29, 14, 51, 11);
			final int user_rating = 4;
			final int version = 2;
			assertNull(MessageTDG.find(mid));
			assertNull(UserTDG.find(uid));
			assertEquals(MessageTDG.update(version, mid, uid, message, speed, latitude, longitude, created_at, user_rating), 1);
			ResultSet rs = MessageTDG.find(mid);
			assertEquals(rs.getLong("m.version"), version+1);
			assertEquals(rs.getLong("m.mid"), mid);
			assertEquals(rs.getLong("m.uid"), uid);
			assertEquals(rs.getBytes("m.message"), new SerialBlob(message));
			assertEquals(rs.getFloat("m.speed"), speed);
			assertEquals(rs.getDouble("m.latitude"), latitude);
			assertEquals(rs.getDouble("m.longitude"), longitude);
			assertEquals(rs.getDate("m.created_at"), created_at.getTime().getTime());
			assertEquals(rs.getInt("m.user_rating"), user_rating);
		}
		catch (SQLException e) {
			fail("Exception failure:" + e);
		}
	}
	
	private void testDelete()
	{
		try {
			final int version = 1;
			assertNotNull(MessageTDG.find(mid));
			assertEquals(MessageTDG.delete(mid, version), 1);
			assertNull(MessageTDG.find(mid));
		}
		catch (SQLException e) {
			fail("Exception failure:" + e);
		}
	}

}
