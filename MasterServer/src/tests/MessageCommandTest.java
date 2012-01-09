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

package tests;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockMultipartHttpServletRequest;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;


import application.DownvoteMessageCommand;
import application.PutMessageCommand;
import application.UpvoteMessageCommand;

import domain.message.Message;
import domain.message.MessageFactory;
import domain.user.UserFactory;
import domain.user.UserType;
import foundation.Database;

public class MessageCommandTest {
	
	boolean previousDatabase = false;
	
	@Before
	public void createTables() throws SQLException
	{
		previousDatabase = Database.getInstance().isDatabaseCreated();
		if (!previousDatabase)
			Database.getInstance().createDatabase();
	}
	
	@After
	public void dropTables() throws SQLException
	{
		if (!previousDatabase)
			Database.getInstance().dropDatabase();
	}
	
	@Test
	public void getCommand()
	{
		
	}
	
	@Test
	public void putCommand() throws IOException
	{		
		String fileName = "test.amr";
		File file = new File(fileName);
		int fileSize = (int)file.length();
		byte[] fileBytes = new byte[fileSize];;
		try {
			assertEquals(fileSize, new FileInputStream(fileName).read(fileBytes));
		}
		catch (Exception e) {
			e.printStackTrace();
			fail();
		}
		String email = "example@example.com";
		String password = "capstone";
		UserFactory.createNew(email, password, UserType.USER_NORMAL);
		MockHttpServletResponse response = new MockHttpServletResponse();
		MockHttpServletRequest request = new MockMultipartHttpServletRequest();
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("longitude", "20.0");
		parameters.put("latitude", "20.0");
		parameters.put("speed", "20.0");
		parameters.put("email", email);
		String contentArray = createContent("bin", fileBytes, parameters);
		request.setContentType("multipart/form-data; boundary=" + BOUNDARY);
		request.setContent(contentArray.getBytes());
		PutMessageCommand putMessageCommand = new PutMessageCommand();
		putMessageCommand.execute(request, response);
		assertEquals(HttpServletResponse.SC_ACCEPTED, response.getStatus());
	}
	
	private static final String BOUNDARY = "AaB03x";
	private static final String ENDLINE = "\r\n";
	
	private String createContent(String fileName, byte[] fileBytes, Map<String, String> parameters) throws IOException
	{
		StringBuilder writer = new StringBuilder();
		for(Map.Entry<String, String> e : parameters.entrySet()) {
			writer.append("--" + BOUNDARY + ENDLINE);
			writer.append("Content-Disposition: form-data; name=\"" + e.getKey() + "\"" + ENDLINE);
			writer.append(ENDLINE);
			writer.append(e.getValue() + ENDLINE);
		}
        writer.append("--" + BOUNDARY + ENDLINE);
        writer.append("Content-Disposition: form-data; ");
        writer.append("name=\"bin\"; filename=\"" + fileName + "\"" + ENDLINE);
        writer.append("Content-Type: application/octet-stream" + ENDLINE);
        writer.append("Content-Transfer-Encoding: binary" + ENDLINE);
        writer.append(ENDLINE);
        writer.append(fileBytes + ENDLINE);
        writer.append("--" + BOUNDARY + "--");
        return writer.toString();
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
