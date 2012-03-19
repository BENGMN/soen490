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

import application.commands.LostUpdateException;

import domain.user.User;
import domain.user.UserType;
import foundation.tdg.UserTDG;


public class UserOutputMapper {
	/**
	 * Calls the persistence method for updating a User.
	 * @param user A User to update
	 * @return Returns the number of rows affected
	 * @throws SQLException
	 * @throws LostUpdateException 
	 */
	public static int update(User user) throws SQLException, LostUpdateException {
		int rows = UserTDG.update(user.getUid(), user.getVersion(), user.getEmail(), user.getPassword(), UserType.convertEnum(user.getType()));
		if (rows == 0) 
			throw new LostUpdateException("User with uid='" + user.getUid() + "' and version= '" + user.getVersion() + "' could not be updated because its version has changed.");
		return rows;
	}
	
	/**
	 * Calls the persistence method for deleting a User.
	 * @param user A User to delete
	 * @return Returns the number of rows affected
	 * @throws SQLException
	 * @throws LostUpdateException 
	 */
	public static int delete(User user) throws SQLException, LostUpdateException {
		int rows = UserTDG.delete(user.getUid(), user.getVersion());
		if (rows == 0) 
			throw new LostUpdateException("User with uid='" + user.getUid() + "' and version= '" + user.getVersion() + "' could not be deleted because its version has changed.");
		return rows;
	}
	
	/**
	 * Calls the persistence method for inserting a User
	 * @param user A User to insert
	 * @return Returns the number of rows affected
	 * @throws SQLException
	 */
	public static int insert(User user) throws SQLException {
		return UserTDG.insert(user.getUid(), user.getVersion(), user.getEmail(), user.getPassword(), UserType.convertEnum(user.getType()));
	}
}
