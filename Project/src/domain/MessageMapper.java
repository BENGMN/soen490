/**
 * SOEN 490
 * Capstone 2011
 * Message mapper for message domain object.
 * Team members: 	Sotirios Delimanolis
 * 			Filipe Martinho
 * 			Adam Harrison
 * 			Vahe Chahinian
 * 			Ben Crudo
 * 			Anthony Boyer
 * 
 * @author Capstone 490 Team Moving Target
 *
 */

package domain;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;


import domain.User.UserType;
import foundation.MessageTDG;
import foundation.UserTDG;

public class MessageMapper {
	public static Message[] findInProximity(double longitude, double latitude, double radius)
	{
		return null;
	}
	
	public static Message find(long mid)
	{
		Message mappedMessage = MessageIdentityMap.getInstance().get(mid);
		if (mappedMessage != null)
			return mappedMessage;
		try
		{
			ResultSet rs = MessageTDG.find(mid);
			
			long rmid = rs.getLong(1);
			long uid = rs.getLong(2);
			assert(rmid == mid);
			String text = rs.getString(3);
			float speed = rs.getFloat(4);
			double latitude = rs.getDouble(5);
			double longitude = rs.getDouble(6);
			Date createdAt = rs.getDate(7);
			int userRating = rs.getInt(8);
			int version = rs.getInt(9);		
			
			return new Message(mid, new UserProxy(uid), text, speed, latitude, longitude, createdAt, userRating, version);
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		return null;
	}
	
	public static int update(Message message)
	{
		try
		{
			return MessageTDG.update(message.getVersion(), message.getMid(), message.getOwner().getUid(), message.getText(), message.getSpeed(), message.getLatitude(), message.getLongitude(), message.getCreatedAt(), message.getUserRating());
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		return -1;
	}
	
	public static int delete(Message message)
	{
		try
		{
			return MessageTDG.delete(message.getMid(), message.getVersion());
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		return -1;
	}
	
	public static void insert(Message message)
	{
		try
		{
			MessageTDG.insert(message.getMid(), message.getOwner().getUid(), message.getText(), message.getSpeed(), message.getLatitude(), message.getLongitude(), message.getCreatedAt(), message.getUserRating());
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
	}
}
