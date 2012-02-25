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

package REFACTORED.domain.user.mappers;

import java.io.IOException;
import java.sql.SQLException;

import REFACTORED.domain.user.User;
import REFACTORED.domain.user.UserType;
import REFACTORED.foundation.UserTDG;


public class UserOutputMapper {
	
	public static int update(User user) throws IOException {
		try	{
			return UserTDG.update(user.getUid(), user.getVersion(), user.getEmail(), user.getPassword(), UserType.convertEnum(user.getType()));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return -1;
	}
	
	public static int delete(User user) throws IOException	{
		try	{
			return UserTDG.delete(user.getUid(), user.getVersion());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return -1;
	}
	
	public static void insert(User user) throws IOException
	{
		try	{
			UserTDG.insert(user.getUid(), user.getVersion(), user.getEmail(), user.getPassword(), UserType.convertEnum(user.getType()));
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
