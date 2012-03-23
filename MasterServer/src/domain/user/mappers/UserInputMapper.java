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

import java.math.BigInteger;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import domain.user.User;
import domain.user.UserFactory;
import domain.user.UserType;
import exceptions.MapperException;
import foundation.finder.UserFinder;

/**
 * InputMapper class that provides indirection to the Foundation classes to persist the domain model.
 */
public class UserInputMapper {

	/**
	 * Helper method to create a User from a row in the given ResultSet
	 * @param rs ResultSet containing a User
	 * @return Returns a User constructed from the ResultSet
	 * @throws SQLException
	 */
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
	 * Finds a User by id.
	 * @param uid The BigInteger id of a User
	 * @return Returns a User instance representing the passed uid
	 * @throws SQLException
	 * @throws MapperException
	 */
	public static User find(BigInteger uid) throws SQLException, MapperException {
		User mappedUser = null;
	
		ResultSet rs = UserFinder.find(uid);
		
		if (!rs.next())
			throw new MapperException ("User with id " + uid.toString() + " does not exist.");
		
		// create the user found
		mappedUser = getUser(rs);
		
		rs.close();
		
		return mappedUser;
	}
	
	/**
	 * Finds a User by email address. 
	 * @param email A String email of a User
	 * @return Returns a User instance representing the passed email
	 * @throws SQLException
	 * @throws MapperException
	 */
	public static User findByEmail(String email) throws SQLException, MapperException {		
		User mappedUser = null;
		
		ResultSet rs = UserFinder.find(email);
		
		if (!rs.next()) 
			throw new MapperException ("User with email " + email + " does not exist.");
		else {	
			mappedUser = getUser(rs);
		}
			
		rs.close();
		
		return mappedUser;
	}
	
	/**
	 * Finds all users. 
	 * @return Returns a List of all User's
	 * @throws SQLException
	 */
	public static List<User> findAll() throws SQLException {
		List<User> users = new LinkedList<User>();
		User user = null;
		
		ResultSet rs = UserFinder.findAll();
		
		while(rs.next()) {
			user = getUser(rs);
	
			// add the user to the list
			users.add(user);
		}
		
		rs.close();
		
		return users;
	}
}
