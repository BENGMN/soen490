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
package domain.message.mappers;

import java.math.BigInteger;
import java.sql.SQLException;

import domain.message.Message;

import foundation.tdg.MessageTDG;

/**
 * Message output mapper
 * @author Moving Target
 */
public class MessageOutputMapper {
	/**
	 * Calls the persistence method for updating a message.
	 * @param message Message
	 * @return the number of rows updated (1 for success, 0 for failure)
	 * @throws SQLException
	 */
	public static int update(Message message) throws SQLException {
		int updated = MessageTDG.update(message.getMid(), message.getUserRating());
		return updated;
	}
	
	/**
	 * Calls the persistence method for deleting a message
	 * @param message Message
	 * @return the number of rows updated (1 for success, 0 for failure) 
	 * @throws SQLException
	 */
	public static int delete(Message message) throws SQLException {
		return MessageTDG.delete(message.getMid());
	}
	
	/**
	 * Calls the persistence method for deleting a message from a BigInteger
	 * @param messageID
	 * @return
	 * @throws SQLException
	 */
	public static int delete(BigInteger messageID) throws SQLException {
		return MessageTDG.delete(messageID);
	}
	
	/**
	 * Calls the foundation method for inserting a message
	 * @param message Message
	 * @throws SQLException
	 */
	public static int insert(Message message) throws SQLException {
		return MessageTDG.insert(message.getMid(), message.getOwner().getUid(), message.getMessage(), message.getSpeed(), message.getLatitude(), message.getLongitude(), message.getCreatedAt(), message.getUserRating());
	}

	/*
	 * The below methods are update methods for the Message's user rating
	 */
	public static int incrementRating(Message message) throws SQLException {
		return MessageTDG.incrementRating(message.getMid());
	}
	
	public static int decrementRating(Message message) throws SQLException {
		return MessageTDG.decrementRating(message.getMid());
	}
}
