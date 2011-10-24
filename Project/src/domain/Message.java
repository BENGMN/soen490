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

public class Message {
	private long mid;
	private IUser owner;
	private String text;
	private float speed;
	private double latitude;
	private double longitude;
	private String createdAt;
	private int userRating;
	
	public Message(long mid, IUser owner, String text, float speed, double latitude, double longitude, String createdAt, int userRating)
	{
		this.mid = mid;
		this.owner = owner;
		this.text = text;
		this.speed = speed;
		this.latitude = latitude;
		this.longitude = longitude;
		this.createdAt = createdAt;
		this.userRating = userRating;
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
	
	public String getCreatedAt()
	{
		return createdAt;
	}
	
	public void setCreatedAt(String createdAt)
	{
		this.createdAt = createdAt;
	}
	
	public int getUserRating()
	{
		return userRating;
	}
	
	public void setUserRating(int userRating) 
	
		this.userRating = userRating;
	}
}
