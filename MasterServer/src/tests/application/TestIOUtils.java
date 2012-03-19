package tests.application;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import application.IOUtils;

import domain.message.Message;
import domain.message.MessageFactory;
import domain.user.IUser;
import domain.user.User;
import domain.user.UserFactory;
import domain.user.UserProxy;
import domain.user.UserType;
import exceptions.CorruptStreamException;
import junit.framework.TestCase;

public class TestIOUtils extends TestCase {
	// Attributes for a User
	private final BigInteger uid = new BigInteger("3425635465657");	

	// Attributes for a Message
	private BigInteger mid = new BigInteger("158749857935");
	private IUser owner = new UserProxy(uid);
	private byte[] message = { 1, 2, 3, 4, 5, 6 };
	private float speed = 5.5f;
	final double latitude = 29.221;
	final double longitude = 35.134;
	private Timestamp createdAt = new Timestamp(System.currentTimeMillis());
	private int userRating = 7;
	private String email = "test@test.com";
	private UserType usertype = UserType.USER_ADVERTISER;
	private int version = 0;
	private String password = "password";
	
	// Testing for a single BigInteger
	public void testWriteAndReadMessageID() {
		File file = new File("TestMessageUtils");
		
		try {
			Message msg = MessageFactory.createNew(uid, message, speed, latitude, longitude, createdAt, userRating);

			BigInteger mid = msg.getMid();
			
			DataOutputStream out = new DataOutputStream(new FileOutputStream(file));
			
			IOUtils.writeMessageIDtoStream(msg.getMid(), out);
			
			out.close();

			DataInputStream in = new DataInputStream(new FileInputStream(file));
			
			BigInteger sameMid = IOUtils.readMessageIDfromStream(in);
			
			assertTrue(mid.equals(sameMid));
			
			in.close();
		} catch (NoSuchAlgorithmException e) {
			fail();
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			fail();
			e.printStackTrace();
		} catch (IOException e) {
			fail();
			e.printStackTrace();
		} finally {
			if (file.exists())
				file.delete();
		}
	}
	
	// Testing for a list of BigInteger
	public void testWriteAndReadMessageIDs() {
		File file = new File("TestMessageUtils");
		
		try {
			BigInteger mid1 = new BigInteger("1231241251342312");
			BigInteger mid2 = new BigInteger("1325435123124312451235134");
			BigInteger mid3 = new BigInteger("123124231253414321231231232131");
			
			List<BigInteger> ids = new ArrayList<BigInteger>(3);
			
			ids.add(mid1);
			ids.add(mid2);
			ids.add(mid3);
			
			DataOutputStream out = new DataOutputStream(new FileOutputStream(file));
			
			IOUtils.writeListMessageIDsToStream(ids, out);
			
			out.close();

			DataInputStream in = new DataInputStream(new FileInputStream(file));
			
			List<BigInteger> sameIDs = null;
			
			// read the message ids from in stream
			sameIDs = IOUtils.readListMessageIDsFromStream(in);
			
			assertEquals(ids.size(), sameIDs.size());
			assertTrue(ids.get(0).equals(sameIDs.get(0)));
			assertTrue(ids.get(1).equals(sameIDs.get(1)));
			assertTrue(ids.get(2).equals(sameIDs.get(2)));

			in.close();
		} catch (FileNotFoundException e) {
			fail();
			e.printStackTrace();
		} catch (IOException e) {
			fail();
			e.printStackTrace();
		} catch (CorruptStreamException e) {
			fail();
			e.printStackTrace();
		} finally {
			if (file.exists())
				file.delete();
		}
	}

	// Testing a single Message
	public void testWriteAndReadMessage() {
		File file = new File("TestMessageUtils");
		
		try {
			Message msg = MessageFactory.createNew(uid, message, speed, latitude, longitude, createdAt, userRating);

			DataOutputStream out = new DataOutputStream(new FileOutputStream(file));
			
			IOUtils.writeMessageToStream(msg, out);
			
			out.close();

			DataInputStream in = new DataInputStream(new FileInputStream(file));
			
			Message sameMsg = IOUtils.readMessageFromStream(in);
			
			assertTrue(msg.equals(sameMsg));
			
			in.close();
		} catch (NoSuchAlgorithmException e) {
			fail();
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			fail();
			e.printStackTrace();
		} catch (IOException e) {
			fail();
			e.printStackTrace();
		} catch (CorruptStreamException e) {
			fail();
			e.printStackTrace();
		} finally {
			if (file.exists())
				file.delete();
		}
				
	}

	// Testing a list of Message
	public void testWriteAndReadMessageList() {
		File file = new File("TestMessageUtils");
		
		try {
			// create 3 messages
			BigInteger mid1 = new BigInteger("1231241251342312");
			Message msg1 = MessageFactory.createClean(mid1, uid, message, speed, latitude, longitude, createdAt, userRating);
			BigInteger mid2 = new BigInteger("1325435123124312451235134");
			Message msg2 = MessageFactory.createClean(mid2, uid, message, speed, latitude, longitude, createdAt, userRating);
			BigInteger mid3 = new BigInteger("123124231253414321231231232131");
			Message msg3 = MessageFactory.createClean(mid3, uid, message, speed, latitude, longitude, createdAt, userRating);

			
			List<Message> list = new ArrayList<Message>(3);
			
			list.add(msg1);
			list.add(msg2);
			list.add(msg3);
			
			DataOutputStream out = new DataOutputStream(new FileOutputStream(file));
			
			IOUtils.writeMessageListToStream(list, out);
			
			out.close();

			DataInputStream in = new DataInputStream(new FileInputStream(file));
			
			List<Message> sameList = null;
			
			// read the message ids from in stream
			sameList = IOUtils.readMessageListFromStream(in);
			
			assertEquals(list.size(), sameList.size());
			assertEquals(list.size(), 3);
			assertTrue(list.get(0).equals(sameList.get(0)));
			assertTrue(list.get(1).equals(sameList.get(1)));
			assertTrue(list.get(2).equals(sameList.get(2)));

			assertFalse(list.get(1).equals(sameList.get(2)));
			in.close();
		} catch (FileNotFoundException e) {
			fail();
			e.printStackTrace();
		} catch (IOException e) {
			fail();
			e.printStackTrace();
		} catch (CorruptStreamException e) {
			fail();
			e.printStackTrace();
		} finally {
			if (file.exists())
				file.delete();
		}
	}
	
	// Testing a single user
	public void testWriteAndReadUser() {
		File file = new File("TestMessageUtils");
		
		try {
			User user = UserFactory.createNew("email@email.com", "this is a password", UserType.USER_ADVERTISER);
			
			DataOutputStream out = new DataOutputStream(new FileOutputStream(file));
			
			// write user to out stream
			IOUtils.writeUserToStream(user, out);
			
			out.close();

			DataInputStream in = new DataInputStream(new FileInputStream(file));
			
			User sameUser = null;
			
			// read the user from in stream
			sameUser = IOUtils.readUserFromStream(in);
			
			assertTrue(sameUser.equals(user));
			
			in.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			fail();
		} catch (IOException e) {			
			e.printStackTrace();
			fail();
		} catch (CorruptStreamException e) {			
			e.printStackTrace();
			fail();
		} catch (NoSuchAlgorithmException e) {			
			e.printStackTrace();
			fail();
		} finally {
			if (file.exists())
				file.delete();
		} 
	}
	
	public void testWriteAndReadMessageToXML() {
		File file = new File("TestMessageUtils");
		
		try {
			Message msg = MessageFactory.createClean(mid, uid, message, speed, latitude, longitude, createdAt, userRating);
			
			DataOutputStream out = new DataOutputStream(new FileOutputStream(file));
			
			IOUtils.writeMessageToXML(msg, out);
			
			out.close();

			DataInputStream in = new DataInputStream(new FileInputStream(file));
			
			List<Message> messages = IOUtils.readMessageListFromXML(in);
			
			// only one message
			assertTrue(msg.equals(messages.get(0)));
			
			in.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			fail();
		} catch (IOException e) {
			e.printStackTrace();
			fail();
		} finally {
			if (file.exists())
				file.delete();
		}
	}
	
	public void testWriteAndReadMessageListXML() {
		File file = new File("TestMessageUtils");
		
		try {
			// create 3 messages
			BigInteger mid1 = new BigInteger("1231241251342312");
			Message msg1 = MessageFactory.createClean(mid1, uid, message, speed, latitude, longitude, createdAt, userRating);
			BigInteger mid2 = new BigInteger("1325435123124312451235134");
			Message msg2 = MessageFactory.createClean(mid2, uid, message, speed, latitude, longitude, createdAt, userRating);
			BigInteger mid3 = new BigInteger("123124231253414321231231232131");
			Message msg3 = MessageFactory.createClean(mid3, uid, message, speed, latitude, longitude, createdAt, userRating);

			List<Message> list = new ArrayList<Message>(3);
			
			list.add(msg1);
			list.add(msg2);
			list.add(msg3);
					
			DataOutputStream out = new DataOutputStream(new FileOutputStream(file));
			
			IOUtils.writeMessageListToXML(list, out);
			
			out.close();

			DataInputStream in = new DataInputStream(new FileInputStream(file));
			
			List<Message> messages = IOUtils.readMessageListFromXML(in);
			
			// only one message
			assertEquals(list.size(), messages.size());
			assertTrue(msg1.equals(messages.get(0)));
			assertTrue(msg2.equals(messages.get(1)));
			assertTrue(msg3.equals(messages.get(2)));
			
			
			in.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			fail();
		} catch (IOException e) {
			e.printStackTrace();
			fail();
		}  finally {
			if (file.exists())
				file.delete();
		}
	}
	
	// Testing a single user
	public void testWriteAndReadUserXML() {
		File file = new File("TestMessageUtils");
		
		try {
			User user = UserFactory.createClean(uid, email, password, usertype, version);
					
			DataOutputStream out = new DataOutputStream(new FileOutputStream(file));
			
			IOUtils.writeUserToXML(user, out);
			
			out.close();

			DataInputStream in = new DataInputStream(new FileInputStream(file));
		
			List<User> users = IOUtils.readUserFromXML(in);
			
			assertEquals(users.size(), 1);
			assertTrue(user.equals(users.get(0)));
			
			in.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			fail();
		} catch (IOException e) {
			e.printStackTrace();
			fail();
		} finally {
			if (file.exists())
				file.delete();
		}		
	}
}
