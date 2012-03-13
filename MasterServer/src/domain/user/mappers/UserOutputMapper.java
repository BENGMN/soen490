/**
 * SOEN 490
 * Capstone 2011
 * Mapper for the User Domain Object.
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

package domain.user.mappers;

import java.sql.SQLException;

import domain.user.User;
import domain.user.UserType;
import foundation.tdg.UserTDG;


public class UserOutputMapper {
	
	public static int update(User user) throws SQLException {
		return UserTDG.update(user.getUid(), user.getVersion(), user.getEmail(), user.getPassword(), UserType.convertEnum(user.getType()));
	}
	
	public static int delete(User user) throws SQLException	{
		return UserTDG.delete(user.getUid(), user.getVersion());
	}
	
	public static int insert(User user) throws SQLException {
		return UserTDG.insert(user.getUid(), user.getVersion(), user.getEmail(), user.getPassword(), UserType.convertEnum(user.getType()));
	}
}
