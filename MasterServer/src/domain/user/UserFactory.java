package domain.user;

import java.sql.SQLException;
import foundation.UserFinder;

public class UserFactory {
	public static User createNew(String email, String password, UserType type) {
		
		User usr = null;
		try {
			usr = new User(UserFinder.findUniqueId(), email, password, type, 1);
		}
		catch (SQLException E) {
			E.printStackTrace();
		}
		
		// Put the new message in the identity map
		UserIdentityMap.getUniqueInstance().put(usr.getUid(), usr);
		UserMapper.insert(usr);
		
		return usr;
	}

	public static User createClean(long uid, String email, String password, UserType type, int version) {
		// Create a message object, passing the proxy as the owner
		User usr = new User(uid, email, password, type, version);
		
		// Put the loaded message in the identity map
		UserIdentityMap.getUniqueInstance().put(uid, usr);
		
		return usr;
	}
}
