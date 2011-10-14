package Foundation;

import java.sql.SQLException;

public class UserFinder {
	// Table name
	private final static String TABLE = "User";
	
	// MySQL SELECT command for finding Users by ID 
	private final static String SELECT_BY_UID = "SELECT u.uid, u.version, u.email, u.password, u.type FROM " + TABLE + " AS u WHERE u.uid=?;";
	
	
	/**
	 * Finds a row from the table for User where the User row ID is equal to the passed parameter.
	 * @param uid User ID
	 * @return Returns a ResultSet containing the values of the row found.
	 * @throws SQLException
	 */
	public static ResultSet findById(long uid) throws SQLException {
		
	}
	
	
	/**
	 * Finds all rows from the table for User.
	 * @return Returns a ResultSet containing all the values of the rows found.
	 * @throws SQLException
	 */
	public static ResultSet findAll() throws SQLException {
		
	}
}
