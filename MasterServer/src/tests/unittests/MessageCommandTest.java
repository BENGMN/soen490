/**
 * SOEN 490
 * Capstone 2011
 * Test for RateMessagesCommand.
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

import static org.junit.Assert.*;

import java.sql.SQLException;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.servlet.http.HttpServletResponse;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import tests.MockHttpServletRequest;
import tests.MockHttpServletResponse;

import application.DownvoteMessageCommand;
import application.UpvoteMessageCommand;

import domain.message.Message;
import domain.message.MessageFactory;
import foundation.MessageTDG;
import foundation.UserTDG;

public class MessageCommandTest {

	@Before
	public void createTables()
	{
		try {
			MessageTDG.create();
			UserTDG.create();
		}
		catch (SQLException E) {
			E.printStackTrace();
		}
	}
	
	@After
	public void dropTables()
	{
		try {
			MessageTDG.drop();
			UserTDG.drop();
		}
		catch (SQLException E) {
			E.printStackTrace();
		}
	}
	
	@Test
	public void getCommand()
	{
		
	}
	
	@Test
	public void putCommand()
	{
		
	}
	
	@Test
	public void upvoteCommand()
	{
		final byte[] bytes = new byte[10];
		final float speed = 10.0f;
		final double latitude = 20.0;
		final double longitude = 20.0;
		Calendar createdDate = new GregorianCalendar(2011, 10, 10);
		final int userRating = 0;
		Message message = MessageFactory.createNew(0, bytes, speed, latitude, longitude, createdDate, userRating);
		UpvoteMessageCommand rateMessageCommand = new UpvoteMessageCommand();
		MockHttpServletRequest request = new MockHttpServletRequest();
		MockHttpServletResponse response = new MockHttpServletResponse();
		request.setParameter("mid", Long.toString(message.getMid()));
		request.setParameter("longitude", Double.toString(longitude));
		request.setParameter("latitude", Double.toString(latitude));
		rateMessageCommand.execute(request, response);
		assertEquals(HttpServletResponse.SC_ACCEPTED, response.getStatus());
		assertEquals(userRating + 1, message.getUserRating());
	}
	
	@Test
	public void downvoteCommand()
	{
		final byte[] bytes = new byte[10];
		final float speed = 10.0f;
		final double latitude = 20.0;
		final double longitude = 20.0;
		Calendar createdDate = new GregorianCalendar(2011, 10, 10);
		final int userRating = 0;
		Message message = MessageFactory.createNew(0, bytes, speed, latitude, longitude, createdDate, userRating);
		DownvoteMessageCommand rateMessageCommand = new DownvoteMessageCommand();
		MockHttpServletRequest request = new MockHttpServletRequest();
		MockHttpServletResponse response = new MockHttpServletResponse();
		request.setParameter("mid", Long.toString(message.getMid()));
		request.setParameter("longitude", Double.toString(longitude));
		request.setParameter("latitude", Double.toString(latitude));
		rateMessageCommand.execute(request, response);
		assertEquals(HttpServletResponse.SC_ACCEPTED, response.getStatus());
		assertEquals(userRating - 1, message.getUserRating());
	}
}
