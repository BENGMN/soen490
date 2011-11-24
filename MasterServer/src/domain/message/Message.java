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
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.Date;
import java.util.Calendar;

import domain.user.IUser;
import domain.user.User;
import domain.user.UserMapper;
import foundation.UserFinder;

import technical.IClientSendable;
import technical.IServerSendable;
import technical.UnrecognizedUserException;

public class Message implements IServerSendable, IClientSendable {
	private static final long serialVersionUID = -7430504657407557608L;
	
	private long mid;
	private IUser owner;
	private byte[] message;
	private float speed;
	private double latitude;
	private double longitude;
	private Calendar createdAt;
	private int userRating;
	private int version;
	
	public Message(long mid, IUser owner, byte[] message, float speed, double latitude, double longitude, Calendar createdAt, int userRating, int version)
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
	
	public Calendar getCreatedAt() {
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
	
	public void writeServer(ObjectOutputStream out) throws IOException
	{
		// Not needed for now, as we only have one server.
	}
	
	public void readServer(ObjectInputStream in) throws IOException
	{
		// Not needed for now, as we only have one server.
	}
	
	public void writeClient(DataOutputStream out) throws IOException
	{
		out.writeLong(mid);
		out.writeInt(getOwner().getEmail().length());
		out.write(getOwner().getEmail().getBytes());
		for (int C = 0; C < Calendar.FIELD_COUNT; ++C)
			out.writeInt(getCreatedAt().get(C));
		out.writeDouble(getLongitude());
		out.writeDouble(getLatitude());
		out.writeInt(getUserRating());
	}
}
