package org.soen490.domain;

import java.util.Calendar;

public class Message {
	// Message identifier
	private long messageID;
	
	// Message owner
	private User owner;

	// Speed at which the message was posted
	private float speed;
	
	// Latitude at which message was posted
	private double latitude;
	
	// Longitude at which message was posted
	private double longitude;
	
	// Date at which the message was posted
	private Calendar createdAt;
	
	// Message rating
	private int userRating;

	
	/*
	 * Getters and Setters
	 */
	
	public User getOwner() {
		return owner;
	}

	public void setOwner(User owner) {
		this.owner = owner;
	}

	public float getSpeed() {
		return speed;
	}

	public void setSpeed(float speed) {
		this.speed = speed;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public Calendar getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Calendar createdAt) {
		this.createdAt = createdAt;
	}

	public int getUserRating() {
		return userRating;
	}

	public void setUserRating(int userRating) {
		this.userRating = userRating;
	}

	public long getMessageID() {
		return messageID;
	}
	
	
}
