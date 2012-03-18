package application;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.msgpack.MessagePack;
import org.msgpack.packer.Packer;
import org.msgpack.unpacker.Unpacker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import domain.message.Message;
import domain.user.UserProxy;
import exceptions.MapperException;

public class IOUtils {

	/**
	 * Takes the given DataOutputStream and writes the give BigInteger to it using the msgpack library.
	 * @param mid BigInteger message id
	 * @param out A DataOutputStream to write to
	 * @throws IOException
	 */
	public static void writeMessageID(BigInteger mid, DataOutputStream out) throws IOException {
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
	 */
	public static BigInteger readMessageID(DataInputStream in) throws IOException {
		BigInteger mid = null;
		
		MessagePack messagePack = new MessagePack();
		Unpacker unpacker = messagePack.createUnpacker(in);
		
		try {
			mid = new BigInteger(unpacker.readString());
		} // TODO may want to have this catch outside 
		catch (NumberFormatException e) {
			Logger logger = (Logger)LoggerFactory.getLogger("application");
			logger.error("NumberFormatException occurred when trying to read a Message id from the inputstream: {}", e);
		}
		return mid;
	}
	
	/**
	 * Takes the given DataOutputStream and writes the ids contained in the given List of BigInteger.
	 * @param ids A List of BigInteger
	 * @param out A DataOutputStream to write to
	 * @throws IOException
	 */
	public static void writeListMessageIDs(List<BigInteger> ids, DataOutputStream out) throws IOException {
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
	 */
	public static List<BigInteger> readListMessageIDs(DataInputStream in) throws IOException {
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
		}
		
		return ids;
	}

	public static List<Message> readMessageList(DataInputStream in) throws IOException {
		List<Message> messages = null;
		
		MessagePack messagePack = new MessagePack();
		Unpacker unpacker = messagePack.createUnpacker(in);
		
		int numberOfMessages = unpacker.readInt();
		messages = new ArrayList<Message>(numberOfMessages);
		
		return messages;
	}
	
	/**
	 * Takes the given DataOutputStream and writes the List of Message to it.
	 * @param messages A List of Message to write
	 * @param out A DataOutputStream to write to
	 * @throws IOException
	 */
	public static void writeMessageList(List<Message> messages, DataOutputStream out) throws IOException {
		Packer packer = (new MessagePack()).createPacker(out);
		// Write the size
		packer.write(messages.size());
		for (Message message : messages)
			IOUtils.writeMessage(message,out);
	}

	/**
	 * Takes the given DataOutputStream and writes the given Message to it.
	 * @param msg A Message to write
	 * @param out A DataOutputStream to write to
	 * @throws IOException
	 */
	public static void writeMessage(Message msg, DataOutputStream out) throws IOException {
		MessagePack pack = new MessagePack();
		Packer packer = pack.createPacker(out);
		packer.write(msg.getMid().toString());
		try {
			packer.write(msg.getOwner().getEmail());
		} catch (MapperException e) {
			Logger logger = (Logger)LoggerFactory.getLogger("application");
			logger.error("MapperException occurred when writing to client: {}", e);
		}
		packer.write(msg.getMessage());
		packer.write(msg.getSpeed());
		packer.write(msg.getCreatedAt().getTime());
		packer.write(msg.getLongitude());
		packer.write(msg.getLatitude());
		packer.write(msg.getUserRating());
	}

}
