package tests.application.strategy;

import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.GregorianCalendar;
import java.util.List;

import application.strategy.AllOrderRandomlyRetrievalStrategy;
import domain.message.Message;
import domain.message.MessageFactory;
import domain.message.mappers.MessageOutputMapper;
import domain.user.User;
import domain.user.UserFactory;
import domain.user.UserType;
import domain.user.mappers.UserOutputMapper;
import foundation.DbRegistry;

public class AllOrderRandomlyRetrievalStrategyTest extends RetrievalStrategyTest {

	public void testRetrieve() {
		try {
			DbRegistry.createDatabaseTables();
			
			// Create some custom data for the messages
			final byte[] message1 = {0,1,2,3,4};
			final byte[] message2 = {5,6,7,8,9};

			// Alter the time created, otherwise duplicated keys are created
			Timestamp createdDate1 = new Timestamp(new GregorianCalendar(2011, 9, 10).getTimeInMillis());
			Timestamp createdDate2 = new Timestamp(new GregorianCalendar(2011, 10, 10).getTimeInMillis());
			Timestamp createdDate3 = new Timestamp(new GregorianCalendar(2011, 11, 10).getTimeInMillis());

			// Create some users
			User u1 = UserFactory.createNew("email2@camsda.com", "Asdasda", UserType.USER_ADVERTISER);
			User u2 = UserFactory.createNew("email@131.com", "password", UserType.USER_NORMAL);
			
			UserOutputMapper.insert(u1);
			UserOutputMapper.insert(u2);
			// Create some messages and save them to the database so we can find them
			Message m1 = MessageFactory.createNew(u1.getUid(), message1, speed, latitude, longitude, createdDate1, 12);
			Message m2 = MessageFactory.createNew(u1.getUid(), message2, speed, latitude, longitude, createdDate2, 6);
			Message m3 = MessageFactory.createNew(u2.getUid(), message2, speed, latitude, longitude, createdDate3, 3);
		
			// Insert both
			MessageOutputMapper.insert(m1);
			MessageOutputMapper.insert(m2);
			MessageOutputMapper.insert(m3);
			
			AllOrderRandomlyRetrievalStrategy s = new AllOrderRandomlyRetrievalStrategy();
			
			// Find all of the messages from the database and make sure they match the ones we created
			List<BigInteger> messages = s.retrieve(longitude, latitude, speed, limit);
			
			// Two message should be returned
			assertEquals(messages.size(), 3);
		} catch (SQLException e) {
			e.printStackTrace();
			fail();
		} catch (NoSuchAlgorithmException e) {
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
