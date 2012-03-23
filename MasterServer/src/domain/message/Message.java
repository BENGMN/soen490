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

import java.math.BigInteger;
import java.sql.Timestamp;
import domain.user.IUser;

public class Message {	
	private BigInteger mid;
	private IUser owner;
	private byte[] message;
	private float speed;
	private double latitude;
	private double longitude;
	private Timestamp createdAt;
	private int userRating;
	
	public Message(BigInteger mid, IUser owner, byte[] message, float speed, double latitude, double longitude, Timestamp createdAt, int userRating)
	{
		this.mid = mid;
		this.owner = owner;
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
		
	@Override
	public boolean equals(Object o) {
		// If the object is not null and is of the type User
		if ((o != null) && (o.getClass().equals(this.getClass()))) {
			// Test all of the particulars
			return (
		       		((Message)o).getMid().equals(this.getMid()) &&
		       		java.util.Arrays.equals(((Message)o).getMessage(),(this.getMessage())) &&
		       		((Message)o).getSpeed() == this.getSpeed() &&
		       		((Message)o).getLatitude() == this.getLatitude() &&
		       		((Message)o).getLongitude() == this.getLongitude() &&
		       		((Message)o).getCreatedAt().equals(this.getCreatedAt()) &&
		       		((Message)o).getUserRating() == this.getUserRating() &&
		       		((Message)o).getOwner().getUid().equals(this.getOwner().getUid())
	            	);
	        }
			return false; // if we made it here we failed above
	}
	
	@Override
	public String toString() {
		return "MessageID: "+getMid()+
				" Message: "+java.util.Arrays.toString(getMessage())+
				" Speed: "+getSpeed()+
				" Latitude: "+getLatitude()+
				" Longitude: "+getLongitude()+
				" UserRating: "+getUserRating()+
				" Owner: "+getOwner().getUid()+
				" CreatedAt: "+getCreatedAt()
				;
	}
}