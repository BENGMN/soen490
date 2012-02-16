package domain.user;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import foundation.UserFinder;

public class UserInputMapper {

	/**
	 * Locate a user by ID. 
	 * If the user is loaded within the identity map then call the foundation layer's
	 * UserFinder to get the information needed to construct the object from the database.
	 * If the object is reconstructed from the database it should be put back into the identity map. (Use the UserFactory create clean method instead)
	 * @param uid
	 * @return A user object is returned and is placed in the identity map if it has not been already.
	 * @throws IOException
	 */
	public static User find(long uid) throws IOException {
		User mappedUser = UserIdentityMap.getUniqueInstance().get(uid);
		if (mappedUser != null)
			return mappedUser;
		
		try	{
			ResultSet rs = UserFinder.find(uid);
			if (rs.next()) {
				long ruid = rs.getLong(1);
				assert(ruid == uid);
				int version = rs.getInt(2);
				String email = rs.getString(3);
				String password = rs.getString(4);
				UserType type = UserType.convertInt(rs.getInt(5));
				return UserFactory.createClean(uid, email, password, type, version);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static User findByEmail(String email) throws IOException {		
		try	{
			ResultSet rs = UserFinder.find(email);
			if (rs.next()) {
				long uid = rs.getLong(1);
				User mappedUser = UserIdentityMap.getUniqueInstance().get(uid);
				if (mappedUser != null)
					return mappedUser;
				int version = rs.getInt(2);
				String password = rs.getString(4);
				UserType type = UserType.convertInt(rs.getInt(5));
				return UserFactory.createClean(uid, email, password, type, version);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
}
