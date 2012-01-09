package domain.user;

import java.sql.ResultSet;
import java.sql.SQLException;

import foundation.UserFinder;

public class UserInputMapper {

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
}
