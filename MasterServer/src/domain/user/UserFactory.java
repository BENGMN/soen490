package domain.user;

import java.io.IOException;
import java.sql.SQLException;

import domain.user.mappers.UserOutputMapper;

import foundation.UserFinder;

public class UserFactory {
	
	/**
	 * Method that is used to create new users in the system. An ID and version will automatically be assigned 
	 * to the record in the database. The user object that is created is also placed into the UserIdentityMap for caching. 
	 * @param email
	 * @param password
	 * @param type
	 * @return
	 * @throws IOException
	 * @throws SQLException
	 */
	public static User createNew(String email, String password, UserType type) throws IOException, SQLException {
		
		// Create a new user with a unique ID
		User usr = new User(UserFinder.findUniqueId(), email, password, type, 1);
		
		// Put the new message in the identity map
		UserIdentityMap.put(usr.getUid(), usr);
		
		// Persist the object to the database
		UserOutputMapper.insert(usr);
		
		return usr;
	}

	/**
	 * Method that should be used by the UserInputMapper to create a new User object and place
	 * it into the UserIdentityMap automatically. Note that the user object created here is not persisted
	 * to the database after it is created since the database is where the object came from.
	 * @param uid
	 * @param email
	 * @param password
	 * @param type
	 * @param version
	 * @return User object that has been cached in the UserIdentityMap
	 * @throws IOException
	 */
	public static User createClean(long uid, String email, String password, UserType type, int version) throws IOException {
		// Create a message object, passing the proxy as the owner
		User usr = new User(uid, email, password, type, version);

		// Put the loaded message in the identity map
		UserIdentityMap.getUniqueInstance().put(uid, usr);
		
		return usr;
	}
}
