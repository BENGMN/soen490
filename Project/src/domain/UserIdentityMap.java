/**
 * SOEN 490
 * Capstone 2011
 * User identity map for user by UserMapper.
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

import java.util.HashMap;

import domain.User;

public class UserIdentityMap {
	private HashMap<Long, User> userMap;
	private static UserIdentityMap singleton = null;
	
	private UserIdentityMap()
	{
		userMap = new HashMap<Long, User>();
	}
	
	public User get(long uid)
	{
		return userMap.get(uid);
	}
	
	public void put(long uid, User user)
	{
		userMap.put(uid, user);
	}
	
	public static UserIdentityMap getInstance()
	{
		if (singleton == null)
			singleton = new UserIdentityMap();
		return singleton;
	}
}
