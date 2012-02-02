/**
 * SOEN 490
 * Capstone 2011
 * Message input mapper for message domain object.
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
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.LinkedList;

import foundation.MessageFinder;
import java.util.List;

import domain.user.IUser;

/**
 * Message input mapper
 * @author Moving Target
 */
public class MessageInputMapper {
	
	/**
	 * Finds all messages within a particular proximity, then maps them to objects.
	 * @param longitude
	 * @param latitude
	 * @param radius
	 * @return a list of message objects approximately within the radius.
	 * @throws SQLException
	 */
	public static List<Message> findInProximity(double longitude, double latitude, double radius) throws IOException, SQLException {
		ResultSet rs = MessageFinder.findInProximity(longitude, latitude, radius);
		List<Message> messages = new LinkedList<Message>();
		while (rs.next()) {
			messages.add(getMessage(rs));
		}
		return messages;
	}
	
	/**
	 * Internal use of getMessage; checks map.
	 * @param rs
	 * @return Message
	 * @throws SQLException
	 */
	private static Message getMessage(ResultSet rs) throws SQLException {
		Timestamp date = rs.getTimestamp("m.created_at");
		
		long mid = rs.getLong("m.mid");
		Message message = MessageIdentityMap.getUniqueInstance().get(mid);
		if (message != null)
			return message;
		
		message = MessageFactory.createClean(mid,
								 rs.getLong("m.uid"),
								 rs.getBytes("m.message"),
								 rs.getFloat("m.speed"),
								 rs.getDouble("m.latitude"),
								 rs.getDouble("m.longitude"),
								 date,
								 rs.getInt("m.user_rating"),
								 rs.getInt("m.version"));
		return message;
	}
	
	/**
	 * Find by id method for message 
	 * @param mid Message id
	 * @return a message with the specified id
	 * @throws SQLException
	 */
	public static Message find(long mid) throws IOException, SQLException {
		// Check if the Identity Map contains a message with the specified id
		Message message = MessageIdentityMap.getUniqueInstance().get(mid);
		if (message == null) {
			ResultSet rs = MessageFinder.find(mid);
			if (rs.next()) {
				message = getMessage(rs);
			}
		}
		
		return message;
		
	}
	
	public static List<Message> findByUser(IUser user) throws IOException, SQLException {
		List<Message> messages = new LinkedList<Message>();
		ResultSet rs = MessageFinder.findByUser(user.getUid());
		while(rs.next()) {
			Message m = getMessage(rs);
			messages.add(m);
		}
		return messages;
	}

	/**
	 * Finds all. Obviously.
	 * @return list
	 * @throws SQLException
	 */
	public static List<Message> findAll() throws IOException, SQLException {
		List<Message> messages = new LinkedList<Message>();
		
		ResultSet rs = MessageFinder.findAll();
		
		while(rs.next()) {
			Message m = getMessage(rs);
			messages.add(m);
		}
		

		return messages;
	}
	
}
