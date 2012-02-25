/**
 * SOEN 490
 * Capstone 2011
 * Message input mapper for message domain object.
 * Team members: Sotirios Delimanolis
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
	
	public List<BigInteger> findIdsInProximity(double longitude, double latitude, double radius) throws SQLException, IOException {
		ResultSet rs = MessageFinder.findIdsInProximity(longitude, latitude, radius);
		List<BigInteger> messages = new LinkedList<BigInteger>();
		while (rs.next()) {
			BigInteger mid = rs.getBigDecimal("m.mid").toBigInteger();
			messages.add(mid);
		}
		return messages;
	}
	
	/**
	 * Internal use of getMessage; Does NOT check the map.
	 * Object relational mapping for the Message occurs here.
	 * @param rs Result set containing a message
	 * @return Message Message object created based on the result set
	 * @throws SQLException
	 */
	private static Message getMessage(ResultSet rs) throws SQLException {
		Timestamp date = rs.getTimestamp("m.created_at");
		
		BigInteger mid = rs.getBigDecimal("m.mid").toBigInteger();
		Message message;
		
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
	public static Message find(BigInteger mid) throws IOException, SQLException {
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
	 * Find expired messages using time to live
	 * @param timeToLive
	 * @return List of message ids
	 * @throws IOException
	 * @throws SQLException
	 */
	public static List<BigInteger> findExpiredMessages(int timeToLive) throws IOException, SQLException {
		ResultSet rs = MessageFinder.findExpired(timeToLive);
		List<BigInteger> messageIds = new LinkedList<BigInteger>();
		
		while(rs.next()) {
			messageIds.add(rs.getBigDecimal("m.mid").toBigInteger());
		}
			
		return messageIds;
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
