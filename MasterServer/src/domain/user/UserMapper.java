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

package domain.user;

import java.sql.ResultSet;
import java.sql.SQLException;
import foundation.UserFinder;
import foundation.UserTDG;

public class UserMapper {
	
	public static User find(long uid) {
		User mappedUser = UserIdentityMap.getUniqueInstance().get(uid);
		if (mappedUser != null)
			return mappedUser;
		
		try	{
			ResultSet rs = UserFinder.find(uid);
			long ruid = rs.getLong(1);
			assert(ruid == uid);
			int version = rs.getInt(2);
			String email = rs.getString(3);
			String password = rs.getString(4);
			UserType type = UserType.convertInt(rs.getInt(5));
			return new User(uid, email, password, type, version);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static User findByEmail(String email) {		
		try	{
			ResultSet rs = UserFinder.find(email); rs.next();
			long uid = rs.getLong(1);
			User mappedUser = UserIdentityMap.getUniqueInstance().get(uid);
			if (mappedUser != null)
				return mappedUser;
			int version = rs.getInt(2);
			String password = rs.getString(4);
			UserType type = UserType.convertInt(rs.getInt(5));
			return new User(uid, email, password, type, version);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static int update(User user) {
		try	{
			return UserTDG.update(user.getUid(), user.getVersion(), user.getEmail(), user.getPassword(), UserType.convertEnum(user.getType()));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return -1;
	}
	
	public static int delete(User user)	{
		try	{
			return UserTDG.delete(user.getUid(), user.getVersion());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return -1;
	}
	
	public static void insert(User user)
	{
		try	{
			UserTDG.insert(user.getUid(), user.getVersion(), user.getEmail(), user.getPassword(), UserType.convertEnum(user.getType()));
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
