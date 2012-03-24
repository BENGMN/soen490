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

package foundation.tdg;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import foundation.DbRegistry;

/**
 * Foundation class for executing insert/update/delete on Users.
 * @author Moving Target
 */
public class UserTDG {
	// Table name
	public final static String TABLE = DbRegistry.getTablePrefix() + "User";
		
	// Private constructor
	private UserTDG() {}
		
	private final static String INSERT = 
		"INSERT INTO " + TABLE + " " +
		"(uid, " +
		"version, " +
		"email, " +
		"password, " +
		"type) " + 			
		"VALUES (?, ?, ?, ?, ?);";
	
	/**
	 * Inserts a row into the table for User, where the column row values are the passed parameters.
	 * @param uid User ID
	 * @param version User version of object in memory
	 * @param email User email
	 * @param password User password
	 * @param type User type as an integer
	 * @throws SQLException
	 */
	public static int insert(BigInteger uid, int version, String email, String password, int type) throws SQLException {
		Connection connection = DbRegistry.getDbConnection();
		PreparedStatement ps = connection.prepareStatement(INSERT);
		
		ps.setBigDecimal(1, new BigDecimal(uid));
		ps.setInt(2, version);
		ps.setString(3, email);
		ps.setString(4, password);
		ps.setInt(5, type);
		
		int count = ps.executeUpdate();
		ps.close();
		return count;
	}
	
	private final static String UPDATE = 
		"UPDATE " + TABLE + " " +
		"AS u SET" + " " +
		"u.email = ?, " +
		"u.password = ?, " +
		"u.type = ?, " +
		"u.version = u.version + 1 " +
		"WHERE u.uid = ? AND u.version = ?;";
		
	
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
	public static int update(BigInteger uid, int version, String email, String password, int type) throws SQLException {
		Connection connection = DbRegistry.getDbConnection();
		PreparedStatement ps = connection.prepareStatement(UPDATE);
		
		ps.setString(1, email);
		ps.setString(2, password);
		ps.setInt(3, type);
		ps.setBigDecimal(4, new BigDecimal(uid));
		ps.setInt(5, version);
		
		int count = ps.executeUpdate();
		ps.close();
		return count; 
	}
	
	private final static String DELETE =
		"DELETE FROM " + TABLE + " " + 
		"WHERE uid = ? AND version = ?;";
	
	/**
	 * Deletes a row from the table for User, where the User ID and version are equal to the passed parameters.
	 * @param uid User ID
	 * @param version User version of object in memory
	 * @return Returns the number of rows affected by the delete.
	 * @throws SQLException
	 */
	public static int delete(BigInteger uid, int version) throws SQLException {
		Connection connection = DbRegistry.getDbConnection();
		PreparedStatement ps = connection.prepareStatement(DELETE);
		ps.setBigDecimal(1, new BigDecimal(uid));
		ps.setInt(2, version);
		
		int count = ps.executeUpdate();
		ps.close();
		return count;
	}
	
	private final static String CREATE_TABLE =
			"CREATE TABLE " + TABLE + " (uid decimal(39) NOT NULL, email varchar(64) NOT NULL, password varchar(256) CHARACTER SET utf8 NOT NULL, type tinyint NOT NULL, version int NOT NULL, CONSTRAINT pk_uid PRIMARY KEY (uid));";
		
	/**
	 * Creates the table User in the database.
	 * @throws SQLException 
	 */
	public static void create() throws SQLException {
		Connection connection = DbRegistry.getDbConnection();
		PreparedStatement ps = connection.prepareStatement(CREATE_TABLE);

		ps.executeUpdate();
		ps.close();
	}
	
	private final static String DROP_TABLE =
		"DROP TABLE " + TABLE + ";";	

	/**
	 * Drops the table User from the database.
	 * @throws SQLException
	 */
	public static void drop() throws SQLException {
		Connection connection = DbRegistry.getDbConnection();
		PreparedStatement ps = connection.prepareStatement(DROP_TABLE);
		
		ps.executeUpdate();
		ps.close();
	}
}
