/**
 * SOEN 490
 * Capstone 2011
 * Proxy for user.
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

import domain.User.UserType;

public class UserProxy implements IUser {
	
	private long uid;
	private User realUser;
	
	UserProxy(long uid)
	{
		this.uid = uid;
		realUser = null;
	}
	
	private User getRealUser()
	{
		if (realUser != null)
			realUser = UserMapper.find(uid);
		return realUser;
	}
	
	public String getEmail()
	{
		return getRealUser().getEmail();
	}

	public String getPassword()
	{
		return getRealUser().getPassword();
	}

	public UserType getType()
	{
		return getRealUser().getType();
	}

	public long getUid()
	{
		return getRealUser().getUid();
	}

	public int getVersion()
	{
		return getRealUser().getVersion();
	}

	public void setEmail(String email)
	{
		getRealUser().setEmail(email);
	}

	public void setPassword(String password)
	{
		getRealUser().setPassword(password);
	}

	public void setType(UserType type) 
	{
		getRealUser().setType(type);
	}

	public void setUid(long uid)
	{
		getRealUser().setUid(uid);
	}

	public void setVersion(int version)
	{
		getRealUser().setVersion(version);
	}

}
