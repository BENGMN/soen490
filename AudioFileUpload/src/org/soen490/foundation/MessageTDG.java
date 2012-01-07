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

package org.soen490.foundation;

import java.io.File;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;

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
			"speed, " +
			"latitude, " +
			"longitude, " +
			"created_at, " +
			"user_rating, " +
			"version) " +
			"VALUES (?, ?, ?, ?, ?, ?, ?, ?);";
	
	/**
	 * Inserts a row into the table for Message, where the column row values are the passed parameters.
	 * @param mid Message id
	 * @param uid User id
	 * @param version Message version
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
		ps.setFloat(3, speed);
		ps.setDouble(4, latitude);
		ps.setDouble(5, longitude);
		ps.setDate(6, new java.sql.Date(created_at.getTime().getTime()));
		ps.setInt(7, user_rating);
		ps.setInt(8, version);
		
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

	public static final String FILE_TABLE = "MessageFilePath";	

	/**
	 * Drops the table Message from the database.
	 * @throws SQLException
	 */
	public static void drop() throws SQLException {
		PreparedStatement ps = Database.getInstance().getStatement(DROP_TABLE);
		ps.executeUpdate();
	}
	
	
	private final static String DELETE_REFERENTIAL_ACTION = 
		"DELETE FROM " + FILE_TABLE + " WHERE messageID = ?;";
	
	/**
	 * Deletes the row in the message audio file table and deletes the audio file found at that path. 
	 * @param mid Message identifier
	 * @return
	 * @throws SQLException
	 * @throws IOException 
	 */
	public static int deleteReferentialAction(long mid) throws SQLException, IOException {
		
		// Find the related audio file
		ResultSet rs = MessageFinder.findAudioFile(mid);
		if(rs.next()) {
			String filePath = rs.getString("a.path");
			File audioFile = new File(filePath);
			
			// Delete the file
			if (!audioFile.delete())
				throw new IOException("File does not exist or could not be deleted");
		}
		
		// Delete the row in the database 
		PreparedStatement ps = Database.getInstance().getStatement(DELETE_REFERENTIAL_ACTION);
		
		ps.setLong(1, mid);
		
		int count = ps.executeUpdate();
		ps.close();
		return count;
	}
	
	public static void insertReferentialAction(long mid, String filePath) {
		
	}
}
