/**
 * SOEN 490
 * Capstone 2011
 * Message identity map for user by MessageMapper.
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

import java.util.HashMap;

/**
 * Singleton domain class for tracking all loaded message objects.
 * @author Moving Target
 */
public class MessageIdentityMap {
	/**
	 * Key: message id
	 * Value: message
	 */
	private HashMap<Long, Message> messageMap;
	
	/**
	 * Single unique instance 
	 */
	private static MessageIdentityMap uniqueInstance = null;
	
	/**
	 * Private constructor for singleton class
	 */
	private MessageIdentityMap() {
		messageMap = new HashMap<Long, Message>();
	}
	
	/**
	 * Returns the value for which the specified key is mapped, or null if there is no mapping.
	 * @param mid Message id
	 * @return the value (message) for which the specified key (mid) is mapped, or null if the map contains no mapping for the key
	 */
	public Message get(long mid) {
		return messageMap.get(mid);
	}
	
	/**
	 * Associates the specified value with the specified key in this map.
	 * Key: mid
	 * Value: message
	 * @param mid Message id
	 * @param message Message
	 */
	public void put(long mid, Message message) {
		messageMap.put(mid, message);
	}
	
	/**
	 * Get the singleton unique instance
	 * @return the unique instance
	 */
	public static MessageIdentityMap getUniqueInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new MessageIdentityMap();
		return uniqueInstance;
	}
}
