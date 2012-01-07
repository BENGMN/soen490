/**
 * SOEN 490
 * Capstone 2011
 * Table Data Gateway for the User Domain Object
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

package foundation;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Foundation class for executing finds on Users.
 * @author Moving Target
 */
public class UserFinder {
	
	private final static String SELECT =
		"SELECT u.uid, u.version, u.email, u.password, u.type " + 
		"FROM " + UserTDG.TABLE + " AS u WHERE u.uid = ?;";
	
	/**
	 * Finds a user from the User table with user id equal to the passed id.
	 * @param uid User ID
	 * @return Returns the ResultSet containing the user information as u.uid, u.version, u.email, u.password, u.type.
	 * @throws SQLException
	 */
	public static ResultSet find(long uid) throws SQLException {
		PreparedStatement ps = Database.getInstance().getStatement(SELECT);
		ps.setLong(1, uid);
		ResultSet rs = ps.executeQuery();		
		return rs;
	}
	
	private final static String SELECT_BY_EMAIL_AND_PASSWORD = 
		"SELECT u.uid, u.version, u.email, u.password, u.type " + 
		"FROM " + UserTDG.TABLE + " AS u WHERE u.email = ? AND u.password = password(?);";
	
	/**
	 * Finds a user from the User table that matches the email/password combination.
	 * Mostly used for login.
	 * @param email User's email
	 * @param password User's password
	 * @return Returns the ResultSet containing the user information as u.uid, u.version, u.email, u.password, u.type.
	 * @throws SQLException
	 */
	public static ResultSet find(String email, String password) throws SQLException {
		PreparedStatement ps = Database.getInstance().getStatement(SELECT_BY_EMAIL_AND_PASSWORD);
		ps.setString(1, email);
		ps.setString(2, password);
		ResultSet rs = ps.executeQuery();
		return rs;
	}
	
	private final static String SELECT_BY_EMAIL =
		"SELECT u.uid, u.version, u.email, u.password, u.type " + 
		"FROM " + UserTDG.TABLE + " AS u WHERE u.email = ?;";
		
	/**
	 * Finds a user from the User table that matches the user email.
	 * Mostly used to find other users.
	 * @param email User's email
	 * @return Returns the ResultSet containing the user information as u.uid, u.version, u.email, u.password, u.type.
	 * @throws SQLException
	 */
	public static ResultSet find(String email) throws SQLException {
		PreparedStatement ps = Database.getInstance().getStatement(SELECT_BY_EMAIL);
		ps.setString(1, email);
		ResultSet rs = ps.executeQuery();
		return rs;
	}
	
	private final static String SELECT_ALL = 
		"SELECT u.uid, u.version, u.email, u.password, u.type " +
		"FROM " + UserTDG.TABLE + " AS u;";
	
	/**
	 * Finds all users from the User table.
	 * @return Returns the ResultSet containing the user information as u.uid, u.version, u.email, u.password, u.type.
	 * @throws SQLException
	 */
	public static ResultSet findAll() throws SQLException {
		PreparedStatement ps = Database.getInstance().getStatement(SELECT_ALL);
		ResultSet rs = ps.executeQuery();
		return rs;		
	}
	
	private final static String SELECT_UNIQUE_ID =
			"SELECT u.uid FROM " + UserTDG.TABLE + " AS u ORDER BY u.uid DESC LIMIT 1";
	
	/**
	 * Retrieves a unique ID from the table, that is available.
	 * @return Returns a unique ID that is not shared by any user in the database.
	 * @throws SQLException
	 */
	public static long findUniqueId() throws SQLException { 
		PreparedStatement ps = Database.getInstance().getStatement(SELECT_UNIQUE_ID);
		ResultSet rs = ps.executeQuery();
		if (rs.next())
			return rs.getLong(1) + 1;
		return 0;
	}
}
