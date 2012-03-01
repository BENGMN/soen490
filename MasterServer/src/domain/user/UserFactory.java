package domain.user;

import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.sql.Timestamp;

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
	 * @throws NoSuchAlgorithmException 
	 */
	public static User createNew(String email, String password, UserType type) throws IOException, SQLException, NoSuchAlgorithmException {
		
		// Create a new user with a unique ID
		User usr = new User(createUniqueID(email,password), email, password, type, 1);
		
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
	public static User createClean(BigInteger uid, String email, String password, UserType type, int version) throws IOException {
		// Create a message object, passing the proxy as the owner
		User usr = new User(uid, email, password, type, version);

		// Put the loaded message in the identity map
		UserIdentityMap.getUniqueInstance().put(uid, usr);
		
		return usr;
	}
	
	/**
	 * Generates a unique ID by creating an md5 hash with the email and password and then
	 * converting the hex into decimal
	 * @param email
	 * @param password 
	 * @return Returns a unique ID that is not shared by any user in the database.
	 * @throws NoSuchAlgorithmException
	 */
	private static BigInteger createUniqueID(String email, String password) throws NoSuchAlgorithmException{ 
		//The seed is basically a concatenation of the email and password. It is unique to a user
		String seed = email + password;
		byte[] defaultBytes = seed.getBytes();
		
		//This is where you pick your algorithm
		MessageDigest algorithm = MessageDigest.getInstance("MD5");
		algorithm.reset();
		algorithm.update(defaultBytes);
		
		//This is where the hashing happens
		byte messageDigest[] = algorithm.digest();
		
	    //This is where you convert the hex code to decimal
		StringBuffer hexString = new StringBuffer();
		for (int i = 0; i < messageDigest.length; i++) {
			hexString.append(Integer.toHexString(0xFF & messageDigest[i]));
		}
		
		return new BigInteger(hexString.toString(), 16);
	}
}
