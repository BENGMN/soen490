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
import java.sql.SQLException;
import java.util.Calendar;
import javax.sql.rowset.serial.SerialBlob;

/**
 * Foundation class for executing insert/delete/update operations on Message table
 * @author Moving Target
 *
 */
public class MessageTDG {

	
	public static final String TABLE = "Message";

	private final static String INSERT =
			"INSERT INTO " + TABLE + 
			"(mid, " +
			"uid, " +
			"message, " +
			"speed, " +
			"latitude, " +
			"longitude, " +
			"created_at, " +
			"user_rating, " +
			"version) " +
			"VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);";
	
	/**
	 * Inserts a row into the table for Message, where the column row values are the passed parameters.
	 * @param mid Message id
	 * @param uid User id
	 * @param version Message version
	 * @param message Message contents
	 * @param speed Message speed 
	 * @param latitude Message latitude of location
	 * @param longitude Message longitude of location
	 * @param created_at Message date created
	 * @param user_rating Message rating
	 * @return Returns 1 if the insert succeeded. 
	 * @throws SQLException
	 */
	public static int insert(long mid, long uid, int version, byte[] message, float speed, double latitude , double longitude , Calendar created_at , int user_rating) throws SQLException {
		PreparedStatement ps = Database.getInstance().getStatement(INSERT);
		
		ps.setLong(1, mid);
		ps.setLong(2, uid);
		ps.setBlob(3, new SerialBlob(message));
		ps.setFloat(4, speed);
		ps.setDouble(5, latitude);
		ps.setDouble(6, longitude);
		ps.setDate(7, new java.sql.Date(created_at.getTime().getTime()));
		ps.setInt(8, user_rating);
		ps.setInt(9, version);
		
		int count = ps.executeUpdate();
		ps.close();
		return count;
	}
	
	private final static String UPDATE = 
		"UPDATE " + TABLE + " " +
		"SET user_rating = ?, version = version + 1  " +
		"WHERE mid = ? AND version = ?";
	
	/**
	 * Not much use for this, since we always want to increment or decrement the user rating by 1.
	 * Updates a row in the table for Message, changing only the user rating
	 * @param mid Message id
	 * @param user_rating Message rating
	 * @return Returns the number of rows updated, should be 1.
	 * @throws SQLException
	 */
	public static int update(long mid, int user_rating, int version) throws SQLException {
		PreparedStatement ps = Database.getInstance().getStatement(UPDATE);
		
		ps.setInt(1, user_rating);
		ps.setLong(2, mid);
		ps.setLong(3, version);
		
		int count = ps.executeUpdate();
		ps.close();
		return count;	
	}

	private final static String DELETE = 
		"DELETE FROM " + TABLE + " WHERE mid = ? AND version = ?";
	
	/**
	 * Deletes a message from the Message table.
	 * @param mid Message id
	 * @param version Message version
	 * @return Returns 1 if the operation was successful, 0 if it no rows were affected.
	 * @throws SQLException
	 */
	public static int delete(long mid, int version) throws SQLException {
		PreparedStatement ps = Database.getInstance().getStatement(DELETE);
		
		ps.setLong(1, mid);
		ps.setInt(2, version);
		
		int count = ps.executeUpdate();
		ps.close();
		return count;
	}
	
	private final static String CREATE_TABLE =
		"CREATE TABLE " + TABLE + " " +
		"(mid bigint NOT NULL, " +
		"uid bigint NOT NULL, " +
		"message blob NOT NULL, " +
		"speed float, " +
		"latitude double NOT NULL, " +
		"longitude double NOT NULL, " +
		"created_at datetime NOT NULL, " +
		"user_rating int NOT NULL, " +
		"version int NOT NULL, " +
		"CONSTRAINT pk_mid PRIMARY KEY(mid), " +
		"CONSTRAINT fk_uid FOREIGN KEY(uid) REFERENCES User (uid));";
	
	/**
	 * Creates the table Message in the database.
	 * @throws SQLException 
	 */
	public static void create() throws SQLException {
		PreparedStatement ps = Database.getInstance().getStatement(CREATE_TABLE);

		ps.executeUpdate();
	}
	
	private final static String DROP_TABLE =
		"DROP TABLE " + TABLE + ";";	

	/**
	 * Drops the table Message from the database.
	 * @throws SQLException
	 */
	public static void drop() throws SQLException {
		PreparedStatement ps = Database.getInstance().getStatement(DROP_TABLE);
		ps.executeUpdate();
	}
}
