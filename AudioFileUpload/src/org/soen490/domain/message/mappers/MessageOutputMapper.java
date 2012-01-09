package org.soen490.domain.message.mappers;

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

import java.sql.SQLException;

import org.soen490.domain.Message;
import org.soen490.foundation.tdg.MessageTDG;

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
		return MessageTDG.update(message.getMessageID(), message.getUserRating());
	}
	
	/**
	 * Calls the foundation method for deleting a message
	 * @param message Message
	 * @return the number of rows updated (1 for success, 0 for failure) 
	 * @throws SQLException
	 */
	public static int delete(Message message) throws SQLException {
		return MessageTDG.delete(message.getMessageID());
	}
	
	/**
	 * Calls the foundation method for inserting a message
	 * @param message Message
	 * @throws SQLException
	 */
	public static void insert(Message message) throws SQLException {
		MessageTDG.insert(message.getMessageID(), message.getLatitude(), message.getLongitude(), message.getSpeed(),  message.getCreatedAt(), message.getUserRating(), message.getOwner().getUserID());
	}
}
