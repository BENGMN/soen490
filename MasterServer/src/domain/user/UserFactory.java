package domain.user;

import java.io.IOException;
import java.sql.SQLException;
import foundation.UserFinder;

public class UserFactory {
	public static User createNew(String email, String password, UserType type) throws IOException, SQLException {
		
		User usr = new User(UserFinder.findUniqueId(), email, password, type, 1);
		
		// Put the new message in the identity map
		UserIdentityMap.getUniqueInstance().put(usr.getUid(), usr);
		UserOutputMapper.insert(usr);
		
		return usr;
	}

	public static User createClean(long uid, String email, String password, UserType type, int version) throws IOException {
		// Create a message object, passing the proxy as the owner
		User usr = new User(uid, email, password, type, version);
		
		// Put the loaded message in the identity map
		UserIdentityMap.getUniqueInstance().put(uid, usr);
		UserOutputMapper.insert(usr);
		
		return usr;
	}
}
