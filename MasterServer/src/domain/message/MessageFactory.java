package domain.message;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;

import domain.user.IUser;
import domain.user.UserProxy;
import foundation.MessageFinder;

public class MessageFactory {

	public static Message createNew(long uid, byte[] message, float speed, double latitude, 
			double longitude, Timestamp createdDate, int userRating) throws IOException, SQLException {
		
		// Create a user proxy
		IUser user = new UserProxy(uid);
		// Create a message object, passing the proxy as the owner
		Message msg = null;
		msg = new Message(MessageFinder.findUniqueId(), user, message, speed, latitude, longitude, createdDate, userRating, 1);
		
		// Put the new message in the identity map
		MessageIdentityMap.getUniqueInstance().put(msg.getMid(), msg);
		MessageOutputMapper.insert(msg);
		
		return msg;
	}

	public static Message createClean(long mid, long uid, byte[] message, float speed, double latitude, 
			double longitude, Timestamp createdDate, int userRating, int version) {
		
		// Create a user proxy
		IUser user = new UserProxy(uid);
		
		// Create a message object, passing the proxy as the owner
		Message msg = new Message(mid, user, message, speed, latitude, longitude, createdDate, userRating, version);
		
		// Put the loaded message in the identity map
		MessageIdentityMap.getUniqueInstance().put(mid, msg);
		
		return msg;
	}
}
