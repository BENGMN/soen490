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

package foundation.tdg;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import javax.sql.rowset.serial.SerialBlob;

import foundation.Database;


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
			"user_rating) " +
			"VALUES (?, ?, ?, ?, ?, ?, ?, ?);";
	
	/**
	 * Inserts a row into the table for Message, where the column row values are the passed parameters.
	 * @param mid Message id
	 * @param uid User id
	 * @param message Message contents
	 * @param speed Message speed 
	 * @param latitude Message latitude of location
	 * @param longitude Message longitude of location
	 * @param created_at Message date created
	 * @param user_rating Message rating
	 * @return Returns 1 if the insert succeeded. 
	 * @throws SQLException
	 */
	public static int insert(BigInteger mid, BigInteger uid, byte[] message, float speed, double latitude , double longitude , Timestamp created_at , int user_rating) throws SQLException {
		Connection connection = Database.getConnection();
		PreparedStatement ps = connection.prepareStatement(INSERT);
		
		ps.setBigDecimal(1, new BigDecimal(mid));
		ps.setBigDecimal(2, new BigDecimal(uid));
		ps.setBlob(3, new SerialBlob(message));
		ps.setFloat(4, speed);
		ps.setDouble(5, latitude);
		ps.setDouble(6, longitude);
		ps.setTimestamp(7, created_at);
		ps.setInt(8, user_rating);
		
		int count = ps.executeUpdate();
		ps.close();
		return count;
	}
	
	private final static String UPDATE = 
		"UPDATE " + TABLE + " " +
		"SET user_rating = ? " +
		"WHERE mid = ?";
	
	/**
	 * Not much use for this, since we always want to increment or decrement the user rating by 1.
	 * Updates a row in the table for Message, changing only the user rating
	 * @param mid Message id
	 * @param user_rating Message rating
	 * @return Returns the number of rows updated, should be 1.
	 * @throws SQLException
	 */
	public static int update(BigInteger mid, int user_rating) throws SQLException {
		Connection connection = Database.getConnection();
		PreparedStatement ps = connection.prepareStatement(UPDATE);
		
		ps.setInt(1, user_rating);
		ps.setBigDecimal(2, new BigDecimal(mid));
		
		int count = ps.executeUpdate();
		ps.close();
		return count;	
	}

	/*
	 * Two update methods for incrementing or decrementing the user rating.
	 * These are needed so that SQL can take care of locking the row so that there is no lost modifications.
	 * Having multiple application servers, you can't only load one instance of a message -> Identity map is useless
	 */
	private final static String UPDATE_DECREMENT =
		"UPDATE " + TABLE + " " + 
		"SET user_rating = user_rating - 1 " +
		"WHERE mid = ?";
	
	public static int decrementRating (BigInteger mid) throws SQLException {
		Connection connection = Database.getConnection();
		PreparedStatement ps = connection.prepareStatement(UPDATE_DECREMENT);
		ps.setBigDecimal(1, new BigDecimal(mid));
		int count = ps.executeUpdate();
		ps.close();
		return count;
	}
	
	private final static String UPDATE_INCREMENT =
		"UPDATE " + TABLE + " " + 
		"SET user_rating = user_rating + 1 " +
		"WHERE mid = ?";
	
	public static int incrementRating (BigInteger mid) throws SQLException {
		Connection connection = Database.getConnection();
		PreparedStatement ps = connection.prepareStatement(UPDATE_INCREMENT);
		ps.setBigDecimal(1, new BigDecimal(mid));
		int count = ps.executeUpdate();
		ps.close();
		return count;
	}
	
	private final static String DELETE = 
		"DELETE FROM " + TABLE + " WHERE mid = ?";
	
	/**
	 * Deletes a message from the Message table.
	 * @param mid Message id
	 * @return Returns 1 if the operation was successful, 0 if it no rows were affected.
	 * @throws SQLException
	 */
	public static int delete(BigInteger mid) throws SQLException {
		Connection connection = Database.getConnection();
		PreparedStatement ps = connection.prepareStatement(DELETE);
		
		ps.setBigDecimal(1, new BigDecimal(mid));
		
		int count = ps.executeUpdate();
		ps.close();
		return count;
	}
	
	private final static String CREATE_TABLE =
		"CREATE TABLE " + TABLE + " " +
		"(mid decimal(39) NOT NULL, " +
		"uid decimal(39) NOT NULL, " +
		"message blob NOT NULL, " +
		"speed float, " +
		"latitude double NOT NULL, " +
		"longitude double NOT NULL, " +
		"created_at timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP, " +
		"user_rating int NOT NULL, " +
		"CONSTRAINT pk_mid PRIMARY KEY(mid));";
		//"CONSTRAINT fk_uid FOREIGN KEY(uid) REFERENCES User (uid));";
	
	/**
	 * Creates the table Message in the database.
	 * @throws SQLException 
	 */
	public static void create() throws SQLException {
		Connection connection = Database.getConnection();
		PreparedStatement ps = connection.prepareStatement(CREATE_TABLE);
		
		ps.executeUpdate();
		ps.close();
	}
	
	private final static String DROP_TABLE =
		"DROP TABLE " + TABLE + ";";	

	/**
	 * Drops the table Message from the database.
	 * @throws SQLException
	 */
	public static void drop() throws SQLException {
		Connection connection = Database.getConnection();
		PreparedStatement ps = connection.prepareStatement(DROP_TABLE);
		
		ps.executeUpdate();
		ps.close();
	}
}
