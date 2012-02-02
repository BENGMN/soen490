package domain.message;

import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.sql.Timestamp;

import domain.user.IUser;
import domain.user.UserProxy;

public class MessageFactory {

	public static Message createNew(long uid, byte[] message, float speed, double latitude, 
			double longitude, Timestamp createdDate, int userRating) throws IOException, SQLException, NoSuchAlgorithmException {
		
		// Create a user proxy
		IUser user = new UserProxy(uid);
		// Create a message object, passing the proxy as the owner
		Message msg = null;
		msg = new Message(createUniqueID(uid,createdDate), user, message, speed, latitude, longitude, createdDate, userRating, 1);
		
		// Put the new message in the identity map
		MessageIdentityMap.getUniqueInstance().put(msg.getMid(), msg);
		MessageOutputMapper.insert(msg);
		
		return msg;
	}

	public static Message createClean(BigInteger mid, long uid, byte[] message, float speed, double latitude, 
			double longitude, Timestamp createdDate, int userRating, int version) {
		
		// Create a user proxy
		IUser user = new UserProxy(uid);
		
		// Create a message object, passing the proxy as the owner
		Message msg = new Message(mid, user, message, speed, latitude, longitude, createdDate, userRating, version);
		
		// Put the loaded message in the identity map
		MessageIdentityMap.getUniqueInstance().put(mid, msg);
		
		return msg;
	}
	
	/**
	 * Generates a unique ID by creating an md5 hash with the username and timestamp and then
	 * converting the hex into decimal
	 * @return Returns a unique ID that is not shared by any user in the database.
	 * @throws NoSuchAlgorithmException
	 */
	private static BigInteger createUniqueID(long uid,Timestamp createdDate) throws NoSuchAlgorithmException{ 

		String seed=uid+createdDate.toString();
		byte[] defaultBytes = seed.getBytes();
		
		MessageDigest algorithm = MessageDigest.getInstance("MD5");
		algorithm.reset();
		algorithm.update(defaultBytes);
		byte messageDigest[] = algorithm.digest();
	            
		StringBuffer hexString = new StringBuffer();
		for (int i=0;i<messageDigest.length;i++) {
			hexString.append(Integer.toHexString(0xFF & messageDigest[i]));
		}
		
		return new BigInteger(hexString.toString(),16);
		
	}
	
}
