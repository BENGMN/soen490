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

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.LinkedList;

import foundation.MessageFinder;
import java.util.List;

/**
 * Message input mapper
 * @author Moving Target
 */
public class MessageInputMapper {
	public static Message[] findInProximity(double longitude, double latitude, double radius){
		return null;
	}
	
	
	
	/*
	 * TODO FIX DATA TYPE FOR LATITUDE AND LONGITUDE
	 */
	private static Message getMessage(ResultSet rs) throws SQLException {
		Calendar date = Calendar.getInstance();
		date.setTime(rs.getDate("m.created_at"));
		
		Message message = MessageFactory.createNew(rs.getLong("m.mid"),
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
	public static Message find(long mid) throws SQLException {
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

	
	/*
	 * TODO CHECK IDENTITY MAP FOR MESSAGE WITH ID AND LOAD IT IF PRESENT
	 */
	public static List<Message> findAll() throws SQLException {
		List<Message> messages = new LinkedList<Message>();
		
		ResultSet rs = MessageFinder.findAll();
		
		while(rs.next()) {
			Message m = getMessage(rs);
			messages.add(m);
		}
		

		return messages;
	}
	
}
