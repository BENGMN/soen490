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

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.Date;
import java.util.Calendar;

import domain.user.IUser;

import technical.ISendable;

public class Message implements ISendable {
	private static final long serialVersionUID = -7430504657407557608L;
	
	private long mid;
	private IUser owner;
	private byte[] message;
	private double speed;
	private double latitude;
	private double longitude;
	private Calendar createdAt;
	private int userRating;
	private int version;
	
	public Message(long mid, IUser owner, byte[] message, double speed, double latitude, double longitude, Calendar createdAt, int userRating, int version)
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
	
	public double getSpeed() {
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
	
	/* Temporarily out
	
	public void writeObject(ObjectOutputStream out) throws IOException
	{
		out.writeObject(mid);
		out.writeObject(owner.getEmail());
		
	}
	
	public void readObject(ObjectInputStream in)
	{
		byte[] response;
		int index;
		Integer.
	}
	*/
}
