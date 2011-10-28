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

package domain;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.Date;

import technical.ISendable;

public class Message implements ISendable {
	private static final long serialVersionUID = -7430504657407557608L;
	
	private long mid;
	private IUser owner;
	private String text;
	private float speed;
	private double latitude;
	private double longitude;
	private Date createdAt;
	private int userRating;
	private int version;
	
	public Message(long mid, IUser owner, String text, float speed, double latitude, double longitude, Date createdAt, int userRating, int version)
	{
		this.mid = mid;
		this.owner = owner;
		this.text = text;
		this.speed = speed;
		this.latitude = latitude;
		this.longitude = longitude;
		this.createdAt = createdAt;
		this.userRating = userRating;
		this.version = version;
	}
	
	public long getMid()
	{
		return mid;
	}
	
	public void setMid(long mid)
	{
		this.mid = mid;
	}
	
	public IUser getOwner()
	{
		return owner;
	}
	
	public void setOwner(IUser owner)
	{
		this.owner = owner;
	}
	
	public String getText()
	{
		return text;
	}
	
	public void setText(String text)
	{
		this.text = text;
	}
	
	public float getSpeed()
	{
		return speed;
	}
	
	public void setSpeed(float speed)
	{
		this.speed = speed;
	}
	
	public double getLatitude()
	{
		return latitude;
	}
	
	public void setLatitude(double latitude)
	{
		this.latitude = latitude;
	}
	
	public double getLongitude()
	{
		return longitude;
	}
	
	public void setLongitude(double longitude)
	{
		this.longitude = longitude;
	}
	
	public Date getCreatedAt()
	{
		return createdAt;
	}
	
	public void setCreatedAt(Date createdAt)
	{
		this.createdAt = createdAt;
	}
	
	public int getUserRating()
	{
		return userRating;
	}

	public void setUserRating(int userRating)
	{
		this.userRating = userRating;
	}
	
	public void setVersion(int version)
	{
		this.version = version;
	}
	
	public int getVersion()
	{
		return version;
	}
	
	public void writeObject(ObjectOutputStream out)
	{
		
	}
	
	public void readObject(ObjectInputStream in)
	{
		
	}
}
