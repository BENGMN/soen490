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

package domain.message;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;

import org.msgpack.MessagePack;
import org.msgpack.packer.Packer;
import org.msgpack.unpacker.Unpacker;

import domain.user.IUser;
import domain.user.UserProxy;

import technical.IClientSendable;
import technical.IServerSendable;

public class Message implements IServerSendable, IClientSendable {	
	private long mid;
	private IUser owner;
	private byte[] message;
	private float speed;
	private double latitude;
	private double longitude;
	private Timestamp createdAt;
	private int userRating;
	private int version;
	
	public Message(long mid, IUser owner, byte[] message, float speed, double latitude, double longitude, Timestamp createdAt, int userRating, int version)
	{
		this.mid = mid;
		this.owner = owner;
		this.message = message;
		this.speed = speed;
		this.latitude = latitude;
		this.longitude = longitude;
		this.createdAt = createdAt;
		this.userRating = userRating;
		this.version = version;
	}
	
	// Getters and Setters
	public long getMid() {
		return mid;
	}
	
	public IUser getOwner() {
		return owner;
	}
	
	public byte[] getMessage() {
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
	
	public void setVersion(int version)	{
		this.version = version;
	}
	
	public int getVersion() {
		return version;
	}
	
	public void writeServer(DataOutputStream out) throws IOException
	{
		Packer packer = (new MessagePack()).createPacker(out);
		packer.write(mid);
		packer.write(getOwner().getUid());
		packer.write(message);
		packer.write(speed);
		packer.write(getCreatedAt());
		packer.write(getLongitude());
		packer.write(getLatitude());
		packer.write(getUserRating());
		packer.write(version);
	}
	
	public void readServer(DataInputStream in) throws IOException
	{
		Unpacker unpacker = (new MessagePack()).createUnpacker(in);
		mid = unpacker.readLong();
		owner = new UserProxy(unpacker.readLong());
		message = unpacker.readByteArray();
		speed = unpacker.readFloat();
		createdAt = unpacker.read(Timestamp.class);
		longitude = unpacker.readDouble();
		latitude = unpacker.readDouble();
		userRating = unpacker.readInt();
		version = unpacker.readInt();
	}
	
	// Allowing us to use MessagePack in our domain layer, outside of our application layer.
	public static void writeListClient(List<Message> messages, DataOutputStream out) throws IOException
	{
		Packer packer = (new MessagePack()).createPacker(out);
		packer.write(messages.size());
		for (Message message : messages)
			message.writeClient(out);
	}
	
	public void writeClient(DataOutputStream out) throws IOException
	{
		MessagePack pack = new MessagePack();
		Packer packer = pack.createPacker(out);
		packer.write(mid);
		packer.write(getOwner().getEmail());
		//packer.write(message);
		packer.write(speed);
		packer.write(getCreatedAt().getTime());
		packer.write(getLongitude());
		packer.write(getLatitude());
		packer.write(getUserRating());
	}
}
