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
package REFACTORED.domain.message.mappers;

import java.io.IOException;
import java.sql.SQLException;

import REFACTORED.domain.message.Message;
import REFACTORED.domain.message.MessageIdentityMap;
import REFACTORED.foundation.MessageTDG;

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
	public static int update(Message message) throws IOException, SQLException {
		int updated = MessageTDG.update(message.getMid(), message.getUserRating());
		return updated;
	}
	
	/**
	 * Calls the foundation method for deleting a message
	 * @param message Message
	 * @return the number of rows updated (1 for success, 0 for failure) 
	 * @throws SQLException
	 */
	public static int delete(Message message) throws IOException, SQLException {
		MessageIdentityMap.remove(message.getMid());
		return MessageTDG.delete(message.getMid());
	}
	
	/**
	 * Calls the foundation method for inserting a message
	 * @param message Message
	 * @throws SQLException
	 */
	public static void insert(Message message) throws IOException, SQLException {
		MessageTDG.insert(message.getMid(), message.getOwner().getUid(), message.getMessage(), message.getSpeed(), message.getLatitude(), message.getLongitude(), message.getCreatedAt(), message.getUserRating());
	}
}
