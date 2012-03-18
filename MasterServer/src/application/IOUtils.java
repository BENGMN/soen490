package application;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.msgpack.MessagePack;
import org.msgpack.packer.Packer;
import org.msgpack.unpacker.Unpacker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import domain.message.Message;
import domain.message.MessageFactory;
import domain.user.User;
import domain.user.UserFactory;
import domain.user.UserProxy;
import domain.user.UserType;
import exceptions.CorruptStreamException;
import exceptions.MapperException;

public class IOUtils {

	/**
	 * Takes the given DataOutputStream and writes the give BigInteger to it using the msgpack library.
	 * @param mid BigInteger message id
	 * @param out A DataOutputStream to write to
	 * @throws IOException
	 */
	public static void writeMessageIDtoStream(BigInteger mid, DataOutputStream out) throws IOException {
		MessagePack messagePack = new MessagePack();
		Packer packer = messagePack.createPacker(out);
		packer.write(mid.toString());	
	}
	
	/**
	 * Takes the given DataInputStream, reads a String from it, and converts it to a BigInteger.
	 * This method should only be called on an Stream on which the corresponding 'set' method was called.
	 * @param in A DataInputStream to read from
	 * @return Returns a BigInteger, the Message id, read from the DataInputStream 
	 * @throws IOException
	 * @throws CorruptStreamException 
	 */
	public static BigInteger readMessageIDfromStream(DataInputStream in) throws IOException, CorruptStreamException {
		BigInteger mid = null;
		
		MessagePack messagePack = new MessagePack();
		Unpacker unpacker = messagePack.createUnpacker(in);
		
		try {
			mid = new BigInteger(unpacker.readString());
		} // TODO may want to have this catch outside 
		catch (NumberFormatException e) {
			Logger logger = (Logger)LoggerFactory.getLogger("application");
			logger.error("NumberFormatException occurred when trying to read a Message id from the inputstream: {}", e);
			throw new CorruptStreamException("A NumberFormatException occurred when trying to read a message id from the input stream.");
		}
		return mid;
	}
	
	/**
	 * Takes the given DataOutputStream and writes the ids contained in the given List of BigInteger.
	 * @param ids A List of BigInteger
	 * @param out A DataOutputStream to write to
	 * @throws IOException
	 */
	public static void writeListMessageIDsToStream(List<BigInteger> ids, DataOutputStream out) throws IOException {
		MessagePack messagePack = new MessagePack();
		Packer packer = messagePack.createPacker(out);
		// write the size
		packer.write(ids.size());
		for(BigInteger id: ids) {
			packer.write(id.toString());
		}
	}
	
	/**
	 * Takes the given DataInputStream and reads a List of BigInteger from it.
	 * @param in A DataInputStream to read from
	 * @return Returns a List of BigInteger read from the DataInputStream
	 * @throws IOException
	 * @throws CorruptStreamException 
	 */
	public static List<BigInteger> readListMessageIDsFromStream(DataInputStream in) throws IOException, CorruptStreamException {
		BigInteger mid = null;
		List<BigInteger> ids = null;
		
		MessagePack messagePack = new MessagePack();
		Unpacker unpacker = messagePack.createUnpacker(in);
		
		int numberOfIDs = unpacker.readInt();
		ids = new ArrayList<BigInteger>(numberOfIDs);
		
		try {
			for (int i = 0; i < numberOfIDs; i++) {
				mid = new BigInteger(unpacker.readString());
				ids.add(mid);
			}
		} catch (NumberFormatException e) {
			Logger logger = (Logger)LoggerFactory.getLogger("application");
			logger.error("NumberFormatException occurred when trying to read a Message id from the inputstream: {}", e);
			throw new CorruptStreamException("A NumberFormatException occurred when trying to read a message id from the input stream.");
		}
		
		return ids;
	}

	/**
	 * Takes the given DataOutputStream and writes the given Message to it.
	 * @param msg A Message to write
	 * @param out A DataOutputStream to write to
	 * @throws IOException
	 */
	public static void writeMessageToStream(Message msg, DataOutputStream out) throws IOException {
		MessagePack pack = new MessagePack();
		Packer packer = pack.createPacker(out);
		
		packer.write(msg.getMid().toString()); // write mid
		packer.write(msg.getOwner().getUid().toString()); // write uid of owner
		packer.write(msg.getMessage()); // write message contents (audio file)
		packer.write(msg.getSpeed()); // write speed
		packer.write(msg.getCreatedAt().getTime()); // write time created
		packer.write(msg.getLongitude()); // write longitude
		packer.write(msg.getLatitude()); // write latitude
		packer.write(msg.getUserRating()); // write user rating
	}
	
	/**
	 * Takes the give DataInputStream and reads a Message from it.
	 * @param in A DataInputStream to read from
	 * @return Returns a Message read from the DataInputStream
	 * @throws IOException
	 * @throws CorruptStreamException
	 */
	public static Message readMessageFromStream(DataInputStream in) throws IOException, CorruptStreamException {
		Message message = null;
		
		MessagePack messagePack = new MessagePack();
		Unpacker unpacker = messagePack .createUnpacker(in);
		
		BigInteger mid = new BigInteger(unpacker.readString()); // read mid
		BigInteger uid = new BigInteger(unpacker.readString()); // read uid
		byte[] audio = unpacker.readByteArray(); // read audio contents
		float speed = unpacker.readFloat(); // read speed
		Timestamp createAt = new Timestamp(unpacker.readLong()); // read time created
		double longitude = unpacker.readDouble(); // read longitude
		double latitude = unpacker.readDouble(); // read latitud
		int userRating = unpacker.readInt(); // read user rating
		
		message = MessageFactory.createClean(mid, uid, audio, speed, latitude, longitude, createAt, userRating);
		
		return message;
	}
	
	/**
	 * Takes the given DataInputStream and reads a List of Message from it
	 * @param in A DataInputStream to read from
	 * @return Returns a List of Message
	 * @throws IOException
	 * @throws CorruptStreamException
	 */
	public static List<Message> readMessageListFromStream(DataInputStream in) throws IOException, CorruptStreamException {
		List<Message> messages = null;
		
		MessagePack messagePack = new MessagePack();
		Unpacker unpacker = messagePack.createUnpacker(in);
		
		int numberOfMessages = unpacker.readInt();
		messages = new ArrayList<Message>(numberOfMessages);
		
		try {
			for (int i = 0; i < numberOfMessages; i++) {
				BigInteger mid = new BigInteger(unpacker.readString()); // read mid
				BigInteger uid = new BigInteger(unpacker.readString()); // read uid
				byte[] audio = unpacker.readByteArray(); // read audio contents
				float speed = unpacker.readFloat(); // read speed
				Timestamp createAt = new Timestamp(unpacker.readLong()); // read time created
				double longitude = unpacker.readDouble(); // read longitude
				double latitude = unpacker.readDouble(); // read latitude
				int userRating = unpacker.readInt(); // read user rating
				
				Message message = MessageFactory.createClean(mid, uid, audio, speed, latitude, longitude, createAt, userRating);
				
				messages.add(message);
			}
		} catch (NumberFormatException e) {
			Logger logger = (Logger)LoggerFactory.getLogger("application");
			logger.error("NumberFormatException occurred when trying to read a Message from the inputstream: {}", e);
			
			throw new CorruptStreamException("A NumberFormatException occurred when trying to read a message from the input stream.");
		}
		
		return messages;
	}
	
	/**
	 * Takes the given DataOutputStream and writes the List of Message to it.
	 * @param messages A List of Message to write
	 * @param out A DataOutputStream to write to
	 * @throws IOException
	 */
	public static void writeMessageListToStream(List<Message> messages, DataOutputStream out) throws IOException {
		MessagePack messagePack = new MessagePack();
		Packer packer = messagePack.createPacker(out);
		// Write the size
		packer.write(messages.size());
		for (Message message : messages)
			IOUtils.writeMessageToStream(message,out);
	}

	/**
	 * Takes the given DataOutputStream and writes the given User to it.
	 * @param user A User to write
	 * @param out A DataOutputStream to write to
	 * @throws IOException
	 */
	public static void writeUserToStream(User user, DataOutputStream out) throws IOException {
		MessagePack messagePack = new MessagePack();
		Packer packer = messagePack.createPacker(out);
		
		packer.write(user.getUid().toString()); // write user id
		packer.write(user.getPassword()); // write password
		packer.write(user.getEmail()); // write email
		packer.write(user.getType().toString()); // write user type
	}
	
	/**
	 * Takes the given DataInputStream and reads a User from it
	 * @param in DataInputStream to read from
	 * @return Returns a User read from the given DataInputStream
	 * @throws IOException
	 * @throws CorruptStreamException
	 */
	public static User readUserFromStream(DataInputStream in) throws IOException, CorruptStreamException {
		User user;
		
		MessagePack messagePack = new MessagePack();
		Unpacker unpacker = messagePack.createUnpacker(in);
		BigInteger uid = null;
		
		try {
			uid = new BigInteger(unpacker.readString()); // read user id
		} catch (NumberFormatException e) {
			Logger logger = (Logger)LoggerFactory.getLogger("application");
			logger.error("NumberFormatException occurred when trying to read a user from the inputstream: {}", e);
			
			throw new CorruptStreamException("A NumberFormatException occurred when trying to read a user from the input stream.");
		}
		String password = unpacker.readString(); // read password
		String email = unpacker.readString(); // read email
		String userType = unpacker.readString(); // read user type
		UserType type = UserType.valueOf(userType);
		
		// TODO get rid of version
		user = UserFactory.createClean(uid, email, password, type, 0);
		return user;
	}
	
	/**
	 * Function used to ensure that a string conforms to the email address syntax.
	 * @param emailAddress String parameter containing the email address to be tested
	 * @return Returns true if the email address has valid syntax, false otherwise.
	 */
	public static boolean validateEmail(String emailAddress) {  
		String email_regex ="^[\\w\\-]([\\.\\w])+[\\w]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";  
		Pattern pattern = Pattern.compile(email_regex, Pattern.CASE_INSENSITIVE);  
		Matcher matcher = pattern.matcher(emailAddress);
		return matcher.matches();
	}

}
