package tests.application;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.GregorianCalendar;

import domain.message.Message;
import domain.message.MessageFactory;
import domain.message.mappers.MessageInputMapper;
import domain.message.mappers.MessageOutputMapper;
import domain.user.User;
import domain.user.UserFactory;
import domain.user.UserType;
import domain.user.mappers.UserOutputMapper;

import foundation.DbRegistry;

import application.PurgeMonitor;
import junit.framework.TestCase;

public class PurgeMonitorTest extends TestCase {
	// Data members for a Message
	private final float speed = 10.0f;
	private final double latitude = 20.0;
	private final double longitude = 10.0;
	
	private int initialDelay = 0;
	private int delay = 1;
		
	public void testExecute() {
		try {
			DbRegistry.createDatabaseTables();

			// Create some custom data for the messages
			final byte[] message1 = {0,1,2,3,4};
			final byte[] message2 = {5,6,7,8,9};

			// These are all old
			Timestamp createdDate1 = new Timestamp(new GregorianCalendar(2011, 9, 10).getTimeInMillis());
			Timestamp createdDate2 = new Timestamp(new GregorianCalendar(2011, 10, 10).getTimeInMillis());
			Timestamp createdDate3 = new Timestamp(new GregorianCalendar(2011, 11, 10).getTimeInMillis());
			
			// This one is recent 
			Timestamp createdDateRecent = new Timestamp(System.currentTimeMillis())
			;
			// Create some users
			User u1 = UserFactory.createNew("email2@camsda.com", "Asdasda", UserType.USER_ADVERTISER);
			User u2 = UserFactory.createNew("email@131.com", "password", UserType.USER_NORMAL);
			
			UserOutputMapper.insert(u1);
			UserOutputMapper.insert(u2);
			
			// Create some messages and save them to the database so we can find them
			Message m1 = MessageFactory.createNew(u1.getUid(), message1, speed, latitude, longitude, createdDate1, 12);
			Message m2 = MessageFactory.createNew(u1.getUid(), message2, speed, latitude, longitude, createdDate2, 6);
			Message m3 = MessageFactory.createNew(u2.getUid(), message2, speed, latitude, longitude, createdDate3, 3);
			Message m4 = MessageFactory.createNew(u1.getUid(), message2, speed, latitude, longitude, createdDateRecent, 0);
			
			MessageOutputMapper.insert(m1);
			MessageOutputMapper.insert(m2);
			MessageOutputMapper.insert(m3);
			MessageOutputMapper.insert(m4);
			
			assertTrue(MessageInputMapper.findAll().size() == 4);
			
			PurgeMonitor monitor = new PurgeMonitor(initialDelay, delay);
			monitor.execute();
			
			// wait for it to run at least once
			Thread.sleep(1000);
			
			// get all messages after, all but 1 should be deleted
			assertTrue(MessageInputMapper.findAll().size() == 1);
			
			// re-insert the ones that were deleted
			MessageOutputMapper.insert(m1);
			MessageOutputMapper.insert(m2);
			MessageOutputMapper.insert(m3);
			
			// wait some more
			Thread.sleep(1500);

			// get all messages after, all but 1 should be deleted
			assertTrue(MessageInputMapper.findAll().size() == 1);
			
		} catch (SQLException e) {
			e.printStackTrace();
			fail();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			fail();
		} catch (InterruptedException e) {
			e.printStackTrace();
			fail();
		} finally {
			try {
				DbRegistry.dropDatabaseTables();
			} catch (SQLException e) {
			}
		}
	}
}
