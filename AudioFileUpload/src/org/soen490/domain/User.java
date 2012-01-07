package org.soen490.domain;

public class User {
	
	// User identifier
	private long userID;
	
	// User email
	private String email;
	
	// User password
	private String password;
	
	// User type: normal or advertiser
	private UserType type;

	/*
	 * Getters and setters
	 */
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public UserType getType() {
		return type;
	}

	public void setType(UserType type) {
		this.type = type;
	}

	public long getUserID() {
		return userID;
	}

	public String getPassword() {
		return password;
	}
}

