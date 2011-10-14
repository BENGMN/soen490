/**
 * SOEN 490
 * Capstone 2011
 * Table Data Gateway for the User Domain Object
 * Team members: 	Sotirios Delimanolis
 * 					Filipe Martinho
 * 					Adam Harrison
 * 					Vahe Chahinian
 * 					Ben Crudo
 * 					Anthony Boyer
 * 
 * @author Capstone 490 Team Moving Target
 *
 */

import java.sql.SQLException;

//CREATE TABLE User (uid NOT NULL bigint, email NOT NULL varchar(64), 
//password NOT NULL varchar(256), type NOT NULL tinyint, CONSTRAINT pk_uid PRIMARY KEY (uid))


public class UserTDG {
	// Table name
	private final static String TABLE = "User";
	// MySQL INSERT command for User
	private final static String INSERT = "INSERT INTO " + TABLE + " (uid, version, email, password, type) VALUES (?,?,?,?,?);";
	// MySQL UPDATE command for User
	private final static String UPDATE = "UPDATE " + TABLE + " AS u SET u.email=?, u.password=?, u.type=?, u.version=u.version+1 WHERE u.uid=? AND u.version=?;";
	// MySQL DELETE command for User
	private final static String DELETE = "DELETE FROM " + TABLE + " AS u WHERE u.uid=? AND u.version=?;";
	
	// Private constructor
	private UserTDG() {}
	
	/**
	 * Inserts a row into the table for User, where the column row are the passed parameters.
	 * @param uid User ID
	 * @param version User version of object in memory
	 * @param email User email
	 * @param password User password
	 * @param type User type as an integer
	 * @throws SQLException
	 */
	public static void insert(long uid, int version, String email, String password, int type) throws SQLException {
		
	}
	
	/**
	 * Updates a row in the table for User, where the row values are the passed parameters.
	 * @param uid User ID
	 * @param version User version of object in memory
	 * @param email User email
	 * @param password User password
	 * @param type User type as an integer
	 * @return Returns the number of rows affected by the update.
	 * @throws SQLException
	 */
	public static int update(long uid, int version, String email, String password, int type) throws SQLException {
		
	}
	
	/**
	 * Deletes a row from the table for User, where the User ID and version are equal to the passed parameters.
	 * @param uid User ID
	 * @param version User version of object in memory
	 * @return Returns the number of rows affected by the delete.
	 * @throws SQLException
	 */
	public static int delete(long uid, int version) throws SQLException {
		
	}
}
