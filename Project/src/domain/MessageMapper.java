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

import java.sql.ResultSet;
import java.sql.SQLException;

import domain.User.UserType;
import foundation.MessageTDG;
import foundation.UserTDG;

public class MessageMapper {
	public static Message find(long mid)
	{
		Message mappedMessage = MessageIdentityMap.getInstance().get(mid);
		if (mappedMessage != null)
			return mappedMessage;
		try
		{
			ResultSet rs = MessageTDG.find(mid);
			
			long rmid = rs.getLong(1);
			assert(rmid == mid);
			int version = rs.getInt(2);
			
			return new Message(mid, null, "", 0.0f, 0.0, 0.0, "", 0);
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		return null;
	}
	
	public static int update(User user)
	{
		try
		{
			return UserTDG.update(user.getUid(), user.getVersion(), user.getEmail(), user.getPassword(), UserType.convertEnum(user.getType()));
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		return -1;
	}
	
	public static int delete(User user)
	{
		try
		{
			return UserTDG.delete(user.getUid(), user.getVersion());
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		return -1;
	}
	
	public static void insert(User user)
	{
		try
		{
			UserTDG.insert(user.getUid(), user.getVersion(), user.getEmail(), user.getPassword(), UserType.convertEnum(user.getType()));
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
	}
}
