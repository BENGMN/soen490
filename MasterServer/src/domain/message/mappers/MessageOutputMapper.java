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
import domain.message.MessageIdentityMap;
import foundation.tdg.MessageTDG;

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
		int updated = MessageTDG.update(message.getMid(), message.getUserRating());
		return updated;
	}
	
	/**
	 * Calls the foundation method for deleting a message
	 * @param message Message
	 * @return the number of rows updated (1 for success, 0 for failure) 
	 * @throws SQLException
	 */
	public static int delete(Message message) throws SQLException {
		MessageIdentityMap.remove(message.getMid());
		return MessageTDG.delete(message.getMid());
	}
	
	public static int delete(BigInteger messageID) throws SQLException {
		MessageIdentityMap.remove(messageID);
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
}
