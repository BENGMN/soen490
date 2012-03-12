package ericsson.thinClient.domain;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.sql.Timestamp;

import org.msgpack.MessagePack;
import org.msgpack.unpacker.Unpacker;


public class Message {
	private BigInteger mid;
	private String owner;
	private float speed;
	private double latitude;
	private double longitude;
	private Timestamp createdAt;
	private int userRating;
	private File message;
	
	// For internal use only; determines whether or not we've modified the rating.
	private boolean ratingModified;
		
		
	private Message() {
		ratingModified = false;
	}
	
	static Message createFromResponse(InputStream in) throws IOException {
		Message message = new Message();
		Unpacker unpacker = (new MessagePack()).createUnpacker(in);
		message.mid = new BigInteger(unpacker.readString());
		message.owner = unpacker.readString();
		byte[] bytes = unpacker.readByteArray();
		message.message = File.createTempFile("ericssonMessage", null);
		FileOutputStream output = new FileOutputStream(message.message);
		output.write(bytes);
		output.close();
		message.speed = unpacker.readFloat();
		message.createdAt = new Timestamp(unpacker.readLong());
		message.latitude = unpacker.readDouble();
		message.longitude = unpacker.readDouble();
		message.userRating = unpacker.readInt();
		return message;
	}
	
	static Message createFromFile(File file, BigInteger mid, double latitude, double longitude, float speed, Timestamp createdAt, int userRating)
	{
		Message message = new Message();
		message.mid = mid;
		message.latitude = latitude;
		message.longitude = longitude;
		message.speed = speed;
		message.createdAt = createdAt;
		message.userRating = userRating;
		message.message = file;
		return message;
	}
	
	public BigInteger getMid() {
		return mid;
	}
	
	public File getFile() {
		return message;
	}

	public String getOwner() {
		return owner;
	}

	public float getSpeed() {
		return speed;
	}

	public double getLatitude() {
		return latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public Timestamp getCreatedAt() {
		return createdAt;
	}

	public int getUserRating() {
		return userRating;
	}

	public File getMessage() {
		return message;
	}
	
	public boolean getRatingModified() {
		return ratingModified;
	}
}
