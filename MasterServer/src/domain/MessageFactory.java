package domain;

import java.util.Calendar;

public class MessageFactory {

	public static Message createNew(long mid, long uid, byte[] message, float speed, double latitude, 
			double longitude, Calendar createdDate, int userRating, int version) {
		
		// Create a user proxy
		IUser user = new UserProxy(uid);
		
		// Create a message object, passing the proxy as the owner
		Message msg = new Message(mid, user,message, speed, latitude, longitude, createdDate, userRating, version);
		
		// Put the new message in the identity map
		MessageIdentityMap.getInstance().put(mid, msg);
		
		return msg;
	}

	public static Message createClean(long mid, long uid, byte[] message, float speed, double latitude, 
			double longitude, Calendar createdDate, int userRating, int version) {
		
		// Create a user proxy
		IUser user = new UserProxy(uid);
		
		// Create a message object, passing the proxy as the owner
		Message msg = new Message(mid, user,message, speed, latitude, longitude, createdDate, userRating, version);
		
		// Put the loaded message in the identity map
		MessageIdentityMap.getInstance().put(mid, msg);
		
		return msg;
	}
}
