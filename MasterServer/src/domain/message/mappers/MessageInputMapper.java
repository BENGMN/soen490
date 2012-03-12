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
package domain.message.mappers;

import java.io.IOException;
import java.math.BigInteger;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.LinkedList;

import java.util.List;

import exceptions.MapperException;
import domain.message.Message;
import domain.message.MessageFactory;
import domain.message.MessageIdentityMap;
import foundation.finder.MessageFinder;
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
	 * @return Returns a list of message objects approximately within the radius.
	 * @throws SQLException, IOException
	 */
	public static List<Message> findInProximity(double longitude, double latitude, double radius) throws IOException, SQLException {
		// TODO have not fixed this yet
		
		ResultSet rs = MessageFinder.findInProximity(longitude, latitude, radius);
		List<Message> messages = new LinkedList<Message>();
		while (rs.next()) {
			messages.add(getMessage(rs));
		}
		return messages;
	}
	
	
	/**
	 * Find by id method for message .
	 * @param mid Message id
	 * @return Returns a message with the specified id
	 * @throws SQLException, IOException, MapperException 
	 */
	public static Message find(BigInteger mid) throws IOException, SQLException, MapperException {
		Message message = null;
		
		// check if the message exists in the identity map
		message = MessageIdentityMap.get(mid);
		
		// if it doesn't, the above call returns null
		if (message == null) {
			
			// find the message in the db
			ResultSet rs = MessageFinder.find(mid);
			
			// if it isn't found, throw mapper exception
			if (!rs.next())
				throw new MapperException("Message with id " + mid.toString() + " does not exist.");
			
			// create the message found
			message = getMessage(rs);
			
			// add it to the identity map
			MessageIdentityMap.put(mid, message);
			
			rs.close();
		}
		
		return message;		
	}
	
	/**
	 * Find all messages belonging to a user.
	 * @param user Owner of the message(s)
	 * @return Returns a list of messages belonging to a specified user.
	 * @throws SQLException, IOException
	 * @throws MapperException 
	 */
	public static List<Message> findByUser(IUser user) throws IOException, SQLException {
		List<Message> messages = new LinkedList<Message>();
		
		// Call the db finder method 
		ResultSet rs = MessageFinder.findByUser(user.getUid());
		
		Message message = null;
		
		while(rs.next()) {
			
			// get the message id
			BigInteger mid = rs.getBigDecimal("m.mid").toBigInteger();
			
			// check if the message exists in the identity map
			message = MessageIdentityMap.get(mid);
			
			// if it isn't, the above statement returned null, so create it
			if (message == null) {
				message = getMessage(rs);
				// add the message to the identity map
				MessageIdentityMap.put(mid, message);
			}
			
			// add the message to the list
			messages.add(message);
		}
		
		rs.close();
		
		return messages;
	}

	/**
	 * Finds all messages in the table.
	 * @return Returns a list of all messages found.
	 * @throws IOException, SQLException
	 */
	public static List<Message> findAll() throws IOException, SQLException {
		List<Message> messages = new LinkedList<Message>();
		Message message = null;
		
		ResultSet rs = MessageFinder.findAll();
		
		while(rs.next()) {
			// get the message id
			BigInteger mid = rs.getBigDecimal("m.mid").toBigInteger();
			
			// check if the message exists in the identity map
			message = MessageIdentityMap.get(mid);
			
			// if it isn't, the above statement returned null, so create it
			if (message == null) {
				message = getMessage(rs);
				// add the message to the identity map
				MessageIdentityMap.put(mid, message);
			}
			
			// add the message to the list
			messages.add(message);
		}
		
		rs.close();
		
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
	 * Find message ids in proximity
	 * @param longitude
	 * @param latitude
	 * @param speed
	 * @return List of message ids
	 * @throws IOException
	 * @throws SQLException
	 */
	public static List<BigInteger> findIdsInProximity(double longitude, double latitude, double speed) throws IOException, SQLException {
		
		ResultSet rs = MessageFinder.findIdsInProximity(longitude, latitude, speed);	
		
		List<BigInteger> messageIds = new LinkedList<BigInteger>();

		while(rs.next()) {
			messageIds.add(rs.getBigDecimal("m.mid").toBigInteger());
		}
		
		return messageIds;
	}
	/**
	 * Internal use of getMessage. Does NOT check the map.
	 * Object relational mapping for the Message occurs here.
	 * @param rs Result set containing a message
	 * @return Message Message object created based on the result set
	 * @throws SQLException
	 */
	private static Message getMessage(ResultSet rs) throws SQLException {
		Message message = null;
		
		Timestamp date = rs.getTimestamp("m.created_at");
		
		BigInteger mid = rs.getBigDecimal("m.mid").toBigInteger();
		
		message = MessageFactory.createClean(mid,
								 rs.getBigDecimal("m.uid").toBigInteger(),
								 rs.getBytes("m.message"),
								 rs.getFloat("m.speed"),
								 rs.getDouble("m.latitude"),
								 rs.getDouble("m.longitude"),
								 date,
								 rs.getInt("m.user_rating"));
		return message;
	}
}
