/**
 * SOEN 490
 * Capstone 2011
 * Message identity map for user by MessageMapper.
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

package domain;

import java.util.HashMap;

public class MessageIdentityMap {
	private HashMap<Long, Message> messageMap;
	private static MessageIdentityMap singleton = null;
	
	private MessageIdentityMap()
	{
		messageMap = new HashMap<Long, Message>();
	}
	
	public Message get(long mid)
	{
		return messageMap.get(mid);
	}
	
	public void put(long mid, Message message)
	{
		messageMap.put(mid, message);
	}
	
	public static MessageIdentityMap getInstance()
	{
		if (singleton == null)
			singleton = new MessageIdentityMap();
		return singleton;
	}
}
