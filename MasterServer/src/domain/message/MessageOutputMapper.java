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

import java.io.IOException;
import java.math.BigInteger;
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
	public static int update(Message message) throws IOException, SQLException {
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
	public static int delete(Message message) throws IOException, SQLException {
		MessageIdentityMap.getUniqueInstance().put(message.getMid(), null);
		return MessageTDG.delete(message.getMid());
	}
	
	/**
	 * Calls the foundation method for deleting a message using its id
	 * @param mid
	 * @return
	 * @throws SQLException
	 * @throws IOException
	 */
	public static int deleteExpiredMessage(BigInteger mid) throws SQLException, IOException {
		MessageIdentityMap.getUniqueInstance().remove(mid);
		return MessageTDG.delete(mid);
	}	
	
	/**
	 * Calls the foundation method for inserting a message
	 * @param message Message
	 * @throws SQLException
	 */
	public static void insert(Message message) throws IOException, SQLException {
		MessageTDG.insert(message.getMid(), message.getOwner().getUid(), message.getVersion(), message.getMessage(), message.getSpeed(), message.getLatitude(), message.getLongitude(), message.getCreatedAt(), message.getUserRating());
	}
}
