/**
 * SOEN 490
 * Capstone 2011
 * User object identity map for use by UserMapper.
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


package domain.user;

import java.math.BigInteger;
import java.util.HashMap;

public class UserIdentityMap {
	private static HashMap<BigInteger, User> userMap;
	private static UserIdentityMap singleton = null;
	
	private UserIdentityMap() {
		userMap = new HashMap<BigInteger, User>();
	}
	
	public User get(BigInteger uid) {
		return userMap.get(uid);
	}
	
	public void put(BigInteger uid, User user) {
			userMap.put(uid, user);
	}
	
	public void remove(BigInteger uid) {
		userMap.remove(uid);
	}
	
	public static UserIdentityMap getUniqueInstance(){
		if (singleton == null)
			singleton = new UserIdentityMap();
		return singleton;
	}
}