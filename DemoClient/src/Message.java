/**
 * SOEN 490
 * Capstone 2011
 * Message domain object.
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
	private float speed;
	private double latitude;
	private double longitude;
	private Timestamp createdAt;
	private int userRating;
	private File message;
	
	public Message(BigInteger mid, File message, float speed, double latitude, double longitude, Timestamp createdAt, int userRating)
	{
		this.mid = mid;
		this.message = message;
		this.speed = speed;
		this.latitude = latitude;
		this.longitude = longitude;
		this.createdAt = createdAt;
		this.userRating = userRating;
	}
		
	// Getters and Setters
	public BigInteger getMid() {
		return mid;
	}
	
	public File getMessage() {
		return message;
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

	public void setUserRating(int userRating) {
		this.userRating = userRating;
	}
	
	@Override
	public String toString() {
		return "MessageID: "+getMid()+
				"\nSpeed: "+getSpeed()+
				"\nLatitude: "+getLatitude()+
				"\nLongitude: "+getLongitude()+
				"\nUserRating: "+getUserRating()+
				"\nCreatedAt: "+getCreatedAt()
				;
	}
	
	public static Message createMessage(InputStream in, String fileName, String fileType) throws IOException
	{
		Unpacker unpacker = (new MessagePack()).createUnpacker(in);
		BigInteger mid = new BigInteger(unpacker.readString());	
		byte[] byteMessage = unpacker.readByteArray();		
		Float speed = unpacker.readFloat();
		Timestamp createdAt = unpacker.read(Timestamp.class);
		double longitude = unpacker.readDouble();
		double latitude = unpacker.readDouble();
		int userRating = unpacker.readInt();
		
		File message = File.createTempFile(fileName, fileType);
		FileOutputStream output = new FileOutputStream(message);
		output.write(byteMessage);
		output.close();
		
		return new Message(mid, message, speed, latitude, longitude, createdAt, userRating);
	}
}
