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

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.sql.Timestamp;
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
import org.msgpack.MessagePack;
import org.msgpack.unpacker.Unpacker;

import technical.UnrecognizedUserException;


import application.DownvoteMessageCommand;
import application.GetMessagesCommand;
import application.ParameterException;
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
	public void createTables() throws SQLException, IOException
	{
		previousDatabase = Database.getInstance().isDatabaseCreated();
		if (!previousDatabase)
			Database.getInstance().createDatabase();
	}
	
	@After
	public void dropTables() throws SQLException, IOException
	{
		if (!previousDatabase)
			Database.getInstance().dropDatabase();
	}
	
	@Test
	public void getCommand() throws SQLException, IOException
	{
		final double longitude = 10.0;
		final double latitude = 30.0;
		final float speed = 20.0f;
		final Timestamp createdDate = new Timestamp(GregorianCalendar.getInstance().getTimeInMillis());
		final int userRating = 2;
		final byte[] bytes = {0,1,2,3,4,5,6,7,8,9};
		final String email = "example@example.com";
		final String password = "capstone";
		final UserType type = UserType.USER_NORMAL;
		User user = UserFactory.createNew(email, password, type);
		Message message = MessageFactory.createNew(user.getUid(), bytes, speed, latitude, longitude, createdDate, userRating);
		MockHttpServletRequest request = new MockHttpServletRequest();
		MockHttpServletResponse response = new MockHttpServletResponse();
		request.setParameter("longitude", Double.toString(longitude));
		request.setParameter("latitude", Double.toString(latitude));
		request.setParameter("speed", Float.toString(speed));
		GetMessagesCommand getMessageCommand = new GetMessagesCommand();
		getMessageCommand.execute(request, response);
		assertEquals(HttpServletResponse.SC_OK, response.getStatus());
		byte[] responseBytes = response.getContentAsByteArray();
		MessagePack pack = new MessagePack();
		Unpacker unpacker = pack.createUnpacker(new ByteArrayInputStream(responseBytes));
		int messageCount = unpacker.readInt();
		assertEquals(1, messageCount);
		assertEquals(message.getMid(), unpacker.readLong());
		assertEquals(message.getOwner().getEmail(), unpacker.readString());
		assertArrayEquals(message.getMessage(), unpacker.readByteArray());
		assertEquals(message.getSpeed(), unpacker.readFloat(), 0.0001);
		assertEquals(message.getCreatedAt().getTime(), unpacker.readLong());
		assertEquals(message.getLongitude(), unpacker.readDouble(), 0.000001);
		assertEquals(message.getLatitude(), unpacker.readDouble(), 0.000001);
		assertEquals(message.getUserRating(), unpacker.readInt());

		UserOutputMapper.delete(user);
		MessageOutputMapper.delete(message);		
	}
	
	@Test
	public void putCommand() throws SQLException, IOException, UnrecognizedUserException, ParameterException
	{		
		String fileName = "test.amr";
		File file = new File(fileName);
		int fileSize = (int)file.length();
		byte[] fileBytes = new byte[fileSize];
		assertEquals(fileSize, new FileInputStream(fileName).read(fileBytes));
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
		List<Message> messages = MessageInputMapper.findByUser(user);
		assertEquals(1, messages.size());
		Message message = messages.get(0);
		assertArrayEquals(message.getMessage(), fileBytes);
		assertEquals(1, MessageOutputMapper.delete(message));
		assertEquals(1, UserOutputMapper.delete(user));
	}
	
	private static final String BOUNDARY = "AaB03x";
	private static final String ENDLINE = "\r\n";
	
	private byte[] createContent(String fileName, byte[] fileBytes, Map<String, String> parameters) throws IOException
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
        byteArrayOutputStream.write(fileBytes);
        writer.append(ENDLINE);
        writer.append("--" + BOUNDARY + "--");
        writer.flush();
        return byteArrayOutputStream.toByteArray();
	}
	
	@Test
	public void upvoteCommand() throws SQLException, IOException
	{
		final byte[] bytes = new byte[10];
		final float speed = 10.0f;
		final double latitude = 20.0;
		final double longitude = 20.0;
		Timestamp createdDate = new Timestamp(GregorianCalendar.getInstance().getTimeInMillis());
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
		assertEquals(1, MessageOutputMapper.delete(message));
	}
	
	@Test
	public void downvoteCommand() throws SQLException, IOException, ParameterException
	{
		final byte[] bytes = new byte[10];
		final float speed = 10.0f;
		final double latitude = 20.0;
		final double longitude = 20.0;
		Timestamp createdDate = new Timestamp(GregorianCalendar.getInstance().getTimeInMillis());
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
		assertEquals(1, MessageOutputMapper.delete(message));
	}
}
