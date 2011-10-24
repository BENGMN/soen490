/**
 * SOEN 490
 * Capstone 2011
 * User Domain Object
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

public class User implements IUser {
	
	enum UserType {
		USER_NORMAL,
		USER_ADVERTISER;
		
		// I honestly cannot believe Java doesn't let you cast from ints to enums automatically. Wat.
		public static int convertEnum(UserType type)
		{
			switch (type) {
			case USER_NORMAL:
				return 0;
			case USER_ADVERTISER:
				return 1;
			}
			return -1;
		}
		
		public static UserType convertInt(int type)
		{
			switch (type) {
			case 0:
				return USER_NORMAL;
			case 1:
				return USER_ADVERTISER;
			}
			return USER_NORMAL;
		}
	};
	
	private long uid;
	private String email;
	private String password;
	private UserType type;
	private int version;
	
	public User(long uid, String email, String password, UserType type, int version)
	{
		this.uid = uid;
		this.email = email;
		this.type = type;
		this.version = version;
	}
	
	public long getUid()
	{
		return uid;
	}
	
	public void setUid(long uid)
	{
		this.uid = uid;
	}
	
	public String getPassword()
	{
		return email;
	}
	
	public void setPassword(String password) 
	{
		this.password = password;
	}
	
	public String getEmail()
	{
		return email;
	}
	
	public void setEmail(String email) 
	{
		this.email = email;
	}
	
	public UserType getType()
	{
		return type;
	}
	
	public void setType(UserType type)
	{
		this.type = type;
	}
	
	public int getVersion()
	{
		return version;
	}
	
	public void setVersion(int version)
	{
		this.version = version;
	}
	
	
}
