/**
 * SOEN 490
 * Capstone 2011
 * Table Data Gateway for the User Domain Object
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

package foundation;

import java.sql.ResultSet;
import java.sql.SQLException;

//CREATE TABLE User (uid NOT NULL bigint, email NOT NULL varchar(64), 
//password NOT NULL varchar(256), type NOT NULL tinyint, CONSTRAINT pk_uid PRIMARY KEY (uid))


public class UserTDG {
	// Table name
	private final static String TABLE = "User";
	
	// Private constructor
	private UserTDG() {}
	
	/**
	 * Inserts a row into the table for User, where the column row are the passed parameters.
	 * @param uid User ID
	 * @return Returns the ResultSet of the query.
	 * @throws SQLException
	 */
	public static ResultSet find(long uid) throws SQLException
	{
		Object[] objects = {uid};
		return Database.getInstance().query("SELECT u.uid, u.version, u.email, u.password, u.type FROM " + TABLE + " AS u WHERE u.uid=?;",
				objects);
	}
	
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
		String query = "INSERT INTO " + TABLE + " (uid, version, email, password, type) VALUES (?,?,?,?,?);";
		Object[] objects = {uid, version, email, password, type};
		Database.getInstance().update(query, objects);
		/*PreparedStatement ps = Database.getInstance().getStatement(query);
		
		ps.setString(1, uid.toString());
		ps.setInt(2, version);
		ps.setString(3, email);
		ps.setString(4, password);
		ps.setInt(5, type);
		
		ps.executeUpdate();
		ps.close();	*/
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
		String query = "UPDATE " + TABLE + " AS u SET u.email=?, u.password=?, u.type=?, u.version=u.version+1 WHERE u.uid=? AND u.version=?;";
		Object[] objects = {email, password, type, uid, version};
		return Database.getInstance().update(query, objects);
		
		/*PreparedStatement ps = Database.getInstance().getStatement(query);
		
		ps.setString(1, email);
		ps.setString(2, password);
		ps.setInt(3, type);
		ps.setString(4, uid.toString());
		ps.setInt(5, version);
		
		int count = ps.executeUpdate();
		ps.close();
		return count; */
	}
	
	/**
	 * Deletes a row from the table for User, where the User ID and version are equal to the passed parameters.
	 * @param uid User ID
	 * @param version User version of object in memory
	 * @return Returns the number of rows affected by the delete.
	 * @throws SQLException
	 */
	public static int delete(long uid, int version) throws SQLException {
		String query = "DELETE FROM " + TABLE + " AS u WHERE u.uid=? AND u.version=?;";
		Object[] objects = {uid, version};
		return Database.getInstance().update(query, objects);
		/*PreparedStatement ps = Database.getInstance().getStatement(query);

		ps.setString(1, uid.toString());
		ps.setInt(2, version);
		
		int count = ps.executeUpdate();
		ps.close();
		return count;*/
	}
}
