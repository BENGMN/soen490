package ericsson.thinClient.domain;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.sql.Timestamp;

import org.msgpack.MessagePack;
import org.msgpack.unpacker.Unpacker;


public class Message {
	private BigInteger mid;
	private String owner;
	private byte[] message;
	private float speed;
	private double latitude;
	private double longitude;
	private Timestamp createdAt;
	private int userRating;
		
		
	static Message createFromResponse(InputStream in) throws IOException
	{
		Message message = new Message();
		Unpacker unpacker = (new MessagePack()).createUnpacker(in);
		message.mid = new BigInteger(unpacker.readString());
		message.owner = unpacker.readString();
		message.message = unpacker.readByteArray();
		message.speed = unpacker.readFloat();
		message.createdAt = new Timestamp(unpacker.readLong());
		message.latitude = unpacker.readDouble();
		message.longitude = unpacker.readDouble();
		message.userRating = unpacker.readInt();
		return message;
	}
	
	public BigInteger getMid()
	{
		return mid;
	}
}
