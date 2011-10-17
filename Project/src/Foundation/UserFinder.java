import java.sql.ResultSet;
import java.sql.SQLException;

public class UserFinder {
	// Table name
	private final static String TABLE = "User";	
	
	/**
	 * Finds a row from the table for User where the User row ID is equal to the passed parameter.
	 * @param uid User ID
	 * @return Returns a ResultSet containing the values of the row found.
	 * @throws SQLException
	 */
	public static ResultSet findById(long uid) throws SQLException {
		Object[] objects = {uid};
		return Database.getInstance().query("SELECT u.uid, u.version, u.email, u.password, u.type FROM " + TABLE + " AS u WHERE u.uid=?;",
				objects);
	}
	
	
	/**
	 * Finds all rows from the table for User.
	 * @return Returns a ResultSet containing all the values of the rows found.
	 * @throws SQLException
	 */
	public static ResultSet findAll() throws SQLException {
		return Database.getInstance().query("SELECT u.uid, u.version, u.email, u.password, u.type FROM " + TABLE + " AS u;",
				null);
	}
}
