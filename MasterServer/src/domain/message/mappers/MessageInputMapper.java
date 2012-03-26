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

import java.math.BigInteger;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.LinkedList;

import java.util.List;

import exceptions.MapperException;
import domain.message.Message;
import domain.message.MessageFactory;

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
	 * @throws SQLException
	 */
	public static List<Message> findInProximity(double longitude, double latitude, double radius) throws SQLException {
		ResultSet rs = MessageFinder.findInProximity(longitude, latitude, radius);
		List<Message> messages = new LinkedList<Message>();
		while (rs.next()) {
			messages.add(getMessage(rs));
		}
		return messages;
	}
	
	
	/**
	 * Finds a Message by id.
	 * @param mid Message id
	 * @return Returns a message with the specified id
	 * @throws SQLException, MapperException 
	 */
	public static Message find(BigInteger mid) throws SQLException, MapperException {
		Message message = null;
	
		// find the message in the db
		ResultSet rs = MessageFinder.find(mid);
		
		// if it isn't found, throw mapper exception
		if (!rs.next())
			throw new MapperException("Message with id " + mid.toString() + " does not exist.");
		
		// create the message found
		message = getMessage(rs);
		
		rs.close();
		
		return message;		
	}
	
	/**
	 * Find all messages belonging to a user.
	 * @param user Owner of the message(s)
	 * @return Returns a list of messages belonging to a specified user.
	 * @throws SQLException
	 */
	public static List<Message> findByUser(IUser user) throws SQLException {
		List<Message> messages = new LinkedList<Message>();
		
		// Call the db finder method 
		ResultSet rs = MessageFinder.findByUser(user.getUid());
		
		Message message = null;
		
		while(rs.next()) {
			message = getMessage(rs);
			
			// add the message to the list
			messages.add(message);
		}
		
		rs.close();
		
		return messages;
	}

	/**
	 * Finds all messages in the table.
	 * @return Returns a list of all messages found.
	 * @throws SQLException
	 */
	public static List<Message> findAll() throws SQLException {
		List<Message> messages = new LinkedList<Message>();
		Message message = null;
		
		ResultSet rs = MessageFinder.findAll();
		
		while(rs.next()) {
			message = getMessage(rs);
			
			// add the message to the list
			messages.add(message);
		}
		
		rs.close();
		
		return messages;
	}
	
	/**
	 * Finds messages in high density areas needed to be deleted
	 * @param latitude
	 * @param longitude
	 * @param radius
	 * @return list of message IDs to be deleted
	 * @throws SQLException
	 */
	public static List<BigInteger> findByDensityToDestroy(double latitude, double longitude, double radius) throws SQLException {		
		List<BigInteger> messages = new LinkedList<BigInteger>();
		ResultSet rs = MessageFinder.findByMaxDensityToPurge(latitude, longitude, radius);
				
		while(rs.next()) {
			messages.add(rs.getBigDecimal("m.mid").toBigInteger());
		}		
		rs.close();
			
		return messages;
	}
	
	public static List<BigInteger> findByTimeAndRatingToDestroy(int daysOfGrace) throws SQLException {
		List<BigInteger> messages = new LinkedList<BigInteger>();
		ResultSet rs = MessageFinder.findByTimeAndRatingToPurge(daysOfGrace);
		
		while(rs.next()) {
			messages.add(rs.getBigDecimal("m.mid").toBigInteger());
		}		
		rs.close();
		
		return messages;
	}
	
	/**
	 * Finds message ids in proximity excluding advertisers and ordered by descending date.
	 * @param longitude
	 * @param latitude
	 * @param radius
	 * @return List of message ids
	 * @throws SQLException
	 */
	public static List<BigInteger> findIdsInProximityNoAdvertisersOrderDate(double longitude, double latitude, double radius, int limit) throws SQLException {
		
		ResultSet rs = MessageFinder.findIdsInProximityNoAdvertisersOrderDate(longitude, latitude, radius, limit);	
		
		List<BigInteger> messageIds = new LinkedList<BigInteger>();

		while(rs.next()) {
			messageIds.add(rs.getBigDecimal("m.mid").toBigInteger());
		}
		
		rs.close();
		
		return messageIds;
	}
	
	/**
	 * Finds message ids in proximity excluding advertisers and ordered by descending rating.
	 * @param longitude
	 * @param latitude
	 * @param radius
	 * @return List of message ids
	 * @throws SQLException
	 */
	public static List<BigInteger> findIdsInProximityNoAdvertisersOrderRating(double longitude, double latitude, double radius, int limit) throws SQLException {
		
		ResultSet rs = MessageFinder.findIdsInProximityNoAdvertisersOrderRating(longitude, latitude, radius, limit);	
		
		List<BigInteger> messageIds = new LinkedList<BigInteger>();

		while(rs.next()) {
			messageIds.add(rs.getBigDecimal("m.mid").toBigInteger());
		}
		
		rs.close();
		
		return messageIds;
	}
	
	/**
	 * Finds message ids in proximity, only advertisers' messages and ordered randomly.
	 * @param longitude
	 * @param latitude
	 * @param radius
	 * @return List of message ids
	 * @throws SQLException
	 */
	public static List<BigInteger> findIdsInProximityOnlyAdvertisersOrderRand(double longitude, double latitude, double radius) throws SQLException {
		
		ResultSet rs = MessageFinder.findIdsInProximityOnlyAdvertisersOrderRand(longitude, latitude, radius);	
		
		List<BigInteger> messageIds = new LinkedList<BigInteger>();

		while(rs.next()) {
			messageIds.add(rs.getBigDecimal("m.mid").toBigInteger());
		}
		
		rs.close();
		
		return messageIds;
	}
	
	/**
	 * Finds message ids in proximity ordered by descending date. Returns a max of 'limit' ids.
	 * @param longitude
	 * @param latitude
	 * @param radius
	 * @return List of message ids
	 * @throws SQLException
	 */
	public static List<BigInteger> findIdsInProximityOrderDate(double longitude, double latitude, double radius, int limit) throws SQLException {
		
		ResultSet rs = MessageFinder.findIdsInProximityOrderDate(longitude, latitude, radius, limit);	
		
		List<BigInteger> messageIds = new LinkedList<BigInteger>();

		while(rs.next()) {
			messageIds.add(rs.getBigDecimal("m.mid").toBigInteger());
		}
		
		rs.close();
		
		return messageIds;
	}
	
	/**
	 * Finds message ids in proximity ordered randomly. Returns a max of 'limit' ids.
	 * @param longitude
	 * @param latitude
	 * @param radius
	 * @return List of message ids
	 * @throws SQLException
	 */
	public static List<BigInteger> findIdsInProximityOrderRand(double longitude, double latitude, double radius) throws SQLException {
		
		ResultSet rs = MessageFinder.findIdsInProximityOrderRand(longitude, latitude, radius);	
		
		List<BigInteger> messageIds = new LinkedList<BigInteger>();

		while(rs.next()) {
			messageIds.add(rs.getBigDecimal("m.mid").toBigInteger());
		}
		
		rs.close();
		
		return messageIds;
	}
	
	/**
	 * Finds message ids in proximity ordered randomly. Returns a max of 'limit' ids.
	 * @param longitude
	 * @param latitude
	 * @param radius
	 * @return List of message ids
	 * @throws SQLException
	 */
	public static List<BigInteger> findIdsInProximityOrderRandLimit(double longitude, double latitude, double radius, int limit) throws SQLException {
		
		ResultSet rs = MessageFinder.findIdsInProximityOrderRandLimit(longitude, latitude, radius, limit);	
		
		List<BigInteger> messageIds = new LinkedList<BigInteger>();

		while(rs.next()) {
			messageIds.add(rs.getBigDecimal("m.mid").toBigInteger());
		}
		
		rs.close();
		
		return messageIds;
	}
	
	/**
	 * Finds message ids in proximity ordered by rating. Returns a max of 'limit' ids.
	 * @param longitude
	 * @param latitude
	 * @param radius
	 * @return List of message ids
	 * @throws SQLException
	 */
	public static List<BigInteger> findIdsInProximityOrderRatingLimit(double longitude, double latitude, double radius, int limit) throws SQLException {
		
		ResultSet rs = MessageFinder.findIdsInProximityOrderRating(longitude, latitude, radius, limit);	
		
		List<BigInteger> messageIds = new LinkedList<BigInteger>();

		while(rs.next()) {
			messageIds.add(rs.getBigDecimal("m.mid").toBigInteger());
		}
		
		rs.close();
		
		return messageIds;
	}
	
	/**
	 * Internal use of getMessage.
	 * Object relational mapping for the Message occurs here.
	 * @param rs Result set containing a message
	 * @return Returns a Message object created based on the result set
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
	
	/**
	 * Find message ids in proximity
	 * @param longitude
	 * @param latitude
	 * @param speed
	 * @return List of message ids
	 * @throws SQLException
	 * @throws IOException 
	public static List<BigInteger> findIdsInProximity(double longitude, double latitude, double speed, String sort) throws SQLException, IOException {
		
		ResultSet rs = MessageFinder.findIdsInProximity(longitude, latitude, speed, sort);	
		
		List<BigInteger> messageIds = new LinkedList<BigInteger>();

		while(rs.next()) {
			messageIds.add(rs.getBigDecimal("m.mid").toBigInteger());
		}
		
		rs.close();
		
		return messageIds;
	}
	 */
}
