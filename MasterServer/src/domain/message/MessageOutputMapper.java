/**
 * SOEN 490
 * Capstone 2011
 * Message output mapper for message domain object.
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

import java.sql.SQLException;
import foundation.MessageTDG;

/**
 * Message output mapper
 * @author Moving Target
 */
public class MessageOutputMapper {
	/**
	 * Calls the foundation method for updating a message.
	 * @param message Message
	 * @return the number of rows updated (1 for success, 0 for failure)
	 * @throws SQLException
	 */
	public static int update(Message message) throws SQLException {
		int updated = MessageTDG.update(message.getMid(), message.getUserRating(), message.getVersion());
		if (updated > 0)
			message.setVersion(message.getVersion()+1);
		return updated;
	}
	
	/**
	 * Calls the foundation method for deleting a message
	 * @param message Message
	 * @return the number of rows updated (1 for success, 0 for failure) 
	 * @throws SQLException
	 */
	public static int delete(Message message) throws SQLException {
		MessageIdentityMap.getUniqueInstance().put(message.getMid(), null);
		return MessageTDG.delete(message.getMid(), message.getVersion());
	}
	
	/**
	 * Calls the foundation method for inserting a message
	 * @param message Message
	 * @throws SQLException
	 */
	public static void insert(Message message) throws SQLException {
		MessageTDG.insert(message.getMid(), message.getOwner().getUid(), message.getVersion(), message.getMessage(), message.getSpeed(), message.getLatitude(), message.getLongitude(), message.getCreatedAt(), message.getUserRating());
	}
}
