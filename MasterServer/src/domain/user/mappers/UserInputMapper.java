/**
 * SOEN 490
 * Capstone 2011
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

package domain.user.mappers;

import java.io.IOException;
import java.math.BigInteger;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import domain.user.User;
import domain.user.UserFactory;
import domain.user.UserIdentityMap;
import domain.user.UserType;
import exceptions.MapperException;
import foundation.finder.UserFinder;



public class UserInputMapper {

	private static User getUser(ResultSet rs) throws SQLException {
		User user = null;
			
		BigInteger uid = rs.getBigDecimal("u.uid").toBigInteger();
		int version = rs.getInt("u.version");
		String email = rs.getString("u.email");
		UserType type = UserType.convertInt(rs.getInt("u.type"));
		
		// Do not give the password 
		user = UserFactory.createClean(uid, email, "", type, version);
	
		return user;
	}
	
	/**
	 * Locate a user by ID. 
	 * If the user is loaded within the identity map then call the foundation layer's
	 * UserFinder to get the information needed to construct the object from the database.
	 * If the object is reconstructed from the database it should be put back into the identity map. (Use the UserFactory create clean method instead)
	 * @param uid
	 * @return A user object is returned and is placed in the identity map if it has not been already.
	 * @throws IOException
	 */
	public static User find(BigInteger uid) throws SQLException, MapperException {
		User mappedUser = null;
		
		// Check if user exists in the identity map
		mappedUser = UserIdentityMap.get(uid);
		
		// If he doesn't, check the database		
		if (mappedUser == null) {
			
			ResultSet rs = UserFinder.find(uid);
			
			if (!rs.next())
				throw new MapperException ("User with id " + uid.toString() + " does not exist.");
			
			// create the user found
			mappedUser = getUser(rs);
			
			UserIdentityMap.put(uid, mappedUser);
			
			rs.close();
			
		}
		
		return mappedUser;
	}
	
	/**
	 * Find a User by their email address. This method calls the database layer via the UserFinder.
	 * @param email
	 * @return
	 * @throws IOException
	 */
	public static User findByEmail(String email) throws SQLException, MapperException {		
		User mappedUser = null;
		
		ResultSet rs = UserFinder.find(email);
		
		if (!rs.next()) 
			throw new MapperException ("User with email " + email + " does not exist.");
		else {
			BigInteger uid = rs.getBigDecimal("u.uid").toBigInteger();
			if ((mappedUser = UserIdentityMap.get(uid)) == null) {
				mappedUser = getUser(rs);
				UserIdentityMap.put(uid, mappedUser);
			}
		}
			
		rs.close();
		
		return mappedUser;
	}
	
	/**
	 * Finds all users. 
	 * @return
	 * @throws SQLException
	 */
	public static List<User> findAll() throws SQLException {
		List<User> users = new LinkedList<User>();
		User user = null;
		
		ResultSet rs = UserFinder.findAll();
		
		while(rs.next()) {
			// get the user id
			BigInteger uid = rs.getBigDecimal("u.mid").toBigInteger();
			
			// check if the user exists in the identity map
			user = UserIdentityMap.get(uid);
			
			// if it isn't, the above statement returned null, so create it
			if (user == null) {
				user = getUser(rs);
				// add the user to the identity map
				UserIdentityMap.put(uid, user);
			}
			
			// add the user to the list
			users.add(user);
		}
		
		rs.close();
		
		return users;
	}
}
