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

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
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
import domain.message.MessageInputMapper;
import domain.message.MessageOutputMapper;
import domain.user.User;
import domain.user.UserFactory;
import domain.user.UserOutputMapper;
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
	public void putCommand()
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
		User user = UserFactory.createNew(email, password, UserType.USER_NORMAL);
		MockHttpServletResponse response = new MockHttpServletResponse();
		MockHttpServletRequest request = new MockMultipartHttpServletRequest();
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("longitude", "20.0");
		parameters.put("latitude", "20.0");
		parameters.put("speed", "20.0");
		parameters.put("email", email);
		byte[] contentArray = createContent("bin", fileBytes, parameters);
		request.setContentType("multipart/mixed; boundary=" + BOUNDARY);
		request.setContent(contentArray);
		PutMessageCommand putMessageCommand = new PutMessageCommand();
		putMessageCommand.execute(request, response);
		assertEquals(HttpServletResponse.SC_ACCEPTED, response.getStatus());
		List<Message> messages = null;
		try {
			messages = MessageInputMapper.findByUser(user);
		}
		catch (SQLException e) {
			fail("Exception failure: " + e);
		}
		assertEquals(1, messages.size());
		Message message = messages.get(0);
		assertArrayEquals(message.getMessage(), fileBytes);
		try {
			MessageOutputMapper.delete(message);
			UserOutputMapper.delete(user);
		}
		catch (SQLException e) {
			fail("Exception failure: " + e);
		}
	}
	
	private static final String BOUNDARY = "AaB03x";
	private static final String ENDLINE = "\r\n";
	
	private byte[] createContent(String fileName, byte[] fileBytes, Map<String, String> parameters)
	{
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		PrintWriter writer = new PrintWriter(byteArrayOutputStream);
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
        writer.flush();
        try {
        	byteArrayOutputStream.write(fileBytes);
        }
        catch (Exception e) {
        	fail("Exception failure: " + e);
        }
        writer.append(ENDLINE);
        writer.append("--" + BOUNDARY + "--");
        writer.flush();
        return byteArrayOutputStream.toByteArray();
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
