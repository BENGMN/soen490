package ericsson.thinClient.domain;

public class User {
	public static User localUser = null;
	
	public static User getLocalUser()
	{
		return localUser;
	}
	
	private String email;
	
	public User()
	{
		
	}
	
	public String getEmail()
	{
		return email;
	}
}
