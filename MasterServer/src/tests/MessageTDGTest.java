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

import java.util.GregorianCalendar;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import org.junit.Test;

import foundation.Database;
import foundation.MessageFinder;
import foundation.MessageTDG;
import static org.junit.Assert.*;

public class MessageTDGTest {

	static long mid = 158749857935L;
	static long uid = 158749857934L;

	@Test
	public void testFunctionality() throws SQLException {
		boolean previousDatabase = Database.getInstance().isDatabaseCreated();
		if (!previousDatabase)
			Database.getInstance().createDatabase();
		insert();
		update();
		delete();
		if (!previousDatabase)
			Database.getInstance().dropDatabase();
	}

	private void insert() throws SQLException {
		try {
			final byte[] message = { 1, 2, 3, 4, 5, 6 };
			final float speed = 5.5f;
			final double latitude = 29.221;
			final double longitude = 35.134;
			Timestamp createdDate = new Timestamp(new GregorianCalendar(2011, 9, 10).getTimeInMillis());
			final int user_rating = 6;
			final int version = 1;
			assertFalse(MessageFinder.find(mid).next());
			assertEquals(MessageTDG.insert(mid, uid, version, message, speed,
					latitude, longitude, createdDate, user_rating), 1);
			ResultSet rs = MessageFinder.find(mid);
			assertTrue(rs.next());
			assertEquals(rs.getLong("m.mid"), mid);
			assertEquals(rs.getLong("m.uid"), uid);

			byte[] b = rs.getBytes("m.message");
			for (int i = 0; i < message.length; i++) {
				assertEquals(b[i], message[i]);
			}

			assertEquals(rs.getFloat("m.speed"), speed, 0.0001f);
			assertEquals(rs.getDouble("m.latitude"), latitude, 0.0000001);
			assertEquals(rs.getDouble("m.longitude"), longitude, 0.0000001);

			assertEquals(createdDate, rs.getTimestamp("m.created_at"));

			assertEquals(rs.getInt("m.user_rating"), user_rating);
		} catch (SQLException e) {
			fail("Exception failure:" + e);

		}
	}

	private void update() throws SQLException {

		try {
			final byte[] message = { 1, 2, 3, 4, 5, 6 };
			final float speed = 5.5f;
			final double latitude = 29.221;
			final double longitude = 35.134;
			final Timestamp createdDate = new Timestamp(new GregorianCalendar(2011, 9, 10).getTimeInMillis());
			final int user_rating = 7;
			final int version = 1;
			assertEquals(1, MessageTDG.update(mid, user_rating, version));
			ResultSet rs = MessageFinder.find(mid);
			assertTrue(rs.next());
			assertEquals(rs.getLong("m.version"), version + 1);
			assertEquals(mid, rs.getLong("m.mid"));
			assertEquals(uid, rs.getLong("m.uid"));
			byte[] b = rs.getBytes("m.message");
			for (int i = 0; i < message.length; i++) {
				assertEquals(b[i], message[i]);
			}
			assertEquals(speed, rs.getFloat("m.speed"), 0.001f);
			assertEquals(latitude, rs.getDouble("m.latitude"), 0.0000001);
			assertEquals(longitude, rs.getDouble("m.longitude"), 0.0000001);
			assertEquals(createdDate, rs.getTimestamp("m.created_at"));
			assertEquals(user_rating, rs.getInt("m.user_rating"));
		} catch (SQLException e) {
			fail("Exception failure:" + e);

		}
	}

	private void delete() throws SQLException {
		try {
			final int version = 2;
			assertTrue(MessageFinder.find(mid).next());
			assertEquals(1, MessageTDG.delete(mid, version));
			assertFalse(MessageFinder.find(mid).next());
		} catch (SQLException e) {
			fail("Exception failure:" + e);
		}
	}
}
