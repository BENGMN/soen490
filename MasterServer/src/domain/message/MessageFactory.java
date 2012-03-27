/**
 * SOEN 490
 * Capstone 2011
 * Team members: 	
 * 			Sotirios Delimanolis
 * 			Filipe Martinho
 * 			Adam Harrison
 * 			Vahe Chahinian
 * 			Ben Crudo
 * 			Anthony Boyer
 * 
 * @author Capstone 490 Team Moving Target
 *
 */
package domain.message;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;

import domain.user.IUser;
import domain.user.UserProxy;


public class MessageFactory {

	public static Message createNew(BigInteger uid, byte[] message, float speed, double latitude, double longitude, Timestamp createdDate, int userRating) throws NoSuchAlgorithmException {
		
		// Create a user proxy
		IUser user = new UserProxy(uid);
		
		// Create a message object, passing the proxy as the owner
		Message msg = null;

		// Correct problems arising from rounding
		createdDate.setNanos(0);
		
		msg = new Message(createMessageUniqueID(uid, createdDate), user, message, speed, latitude, longitude, createdDate, userRating);
		
		return msg;
	}

	public static Message createClean(BigInteger mid, BigInteger uid, byte[] message, float speed, double latitude, double longitude, Timestamp createdDate, int userRating) {
		
		// Create a user proxy
		IUser user = new UserProxy(uid);
		
		// Correct problems arising from rounding
		createdDate.setNanos(0);
		
		// Create a message object, passing the proxy as the owner
		Message msg = new Message(mid, user, message, speed, latitude, longitude, createdDate, userRating);
				
		return msg;
	}
	
	/**
	 * Generates a unique ID by creating an md5 hash with the username and timestamp and then
	 * converting the hex into decimal
	 * @param uid
	 * @param createdDate 
	 * @return Returns a unique ID that is not shared by any user in the database.
	 * @throws NoSuchAlgorithmException
	 */
	private static BigInteger createMessageUniqueID(BigInteger uid, Timestamp createdDate) throws NoSuchAlgorithmException{ 

		//The seed is basically a concatenation of the uid and createdDate. It is unique to a message
		String seed = uid.toString() + createdDate.toString();
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
