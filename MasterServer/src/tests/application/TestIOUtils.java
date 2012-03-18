package tests.application;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

import org.msgpack.MessagePack;
import org.msgpack.packer.Packer;

import application.IOUtils;

import domain.message.Message;
import domain.message.MessageFactory;
import domain.user.IUser;
import domain.user.UserProxy;
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

	// Testing for a single BigInteger
	public void testWriteAndReadMessageID() {
		File file = new File("TestMessageUtils");
		
		try {
			Message msg = MessageFactory.createNew(uid, message, speed, latitude, longitude, createdAt, userRating);

			BigInteger mid = msg.getMid();
			
			DataOutputStream out = new DataOutputStream(new FileOutputStream(file));
			
			IOUtils.writeMessageID(msg.getMid(), out);
			
			out.close();

			DataInputStream in = new DataInputStream(new FileInputStream(file));
			
			BigInteger sameMid = IOUtils.readMessageID(in);
			
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
			
			IOUtils.writeListMessageIDs(ids, out);
			
			out.close();

			DataInputStream in = new DataInputStream(new FileInputStream(file));
			
			List<BigInteger> sameIDs = null;
			
			// read the message ids from in stream
			sameIDs = IOUtils.readListMessageIDs(in);
			
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
		} finally {
			if (file.exists())
				file.delete();
		}
	}

	public void testWriteAndReadMessage() {
		File file = new File("TestMessageUtils");
		
		try {
			Message msg = MessageFactory.createNew(uid, message, speed, latitude, longitude, createdAt, userRating);

			BigInteger mid = msg.getMid();
			
			DataOutputStream out = new DataOutputStream(new FileOutputStream(file));
			
			IOUtils.writeMessageID(msg.getMid(), out);
			
			out.close();

			DataInputStream in = new DataInputStream(new FileInputStream(file));
			
			BigInteger sameMid = IOUtils.readMessageID(in);
			
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
}
