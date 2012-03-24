package foundation.tdg;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import foundation.DbRegistry;


/**
 * Table data gateway for the ServerParameters table in persistence.
 */
public class ServerParameterTDG {
	/**
	 * SQL table name of parameters
	 */
	public final static String TABLE = DbRegistry.getTablePrefix() + "ServerParameters";
	
	/**
	 * SQL query for updating an existing server parameter
	 */
	protected final static String UPDATE = 
		"UPDATE " + TABLE + " SET description = ?, value = ? WHERE paramName = ?;";
	
	/**
	 * TDG function for updating exiting server parameters. 
	 * @param variableName
	 * @param description
	 * @param value
	 * @param version
	 * @return Returns the number of rows that were affected by the SQL query.
	 */
	public static int update (String paramName, String description, String value) throws SQLException {
		Connection connection = DbRegistry.getDbConnection();
		PreparedStatement ps = connection.prepareStatement(UPDATE);
		
		ps.setString(1, description);
		ps.setString(2, value);
		ps.setString(3, paramName);
		
		int rows = ps.executeUpdate();
		ps.close();
		
		return rows;
	}
	
	/*
	 * The methods below are basic TDG methods, but should not be used in most cases with this application. 
	 * If someone wants to a new server parameter, they should probably do it straight to the database and
	 * would probably need to restart the application.
	 */
	
	/**
	 * SQL query for inserting new server parameters
	 */
	protected final static String INSERT = 
		"INSERT INTO " + TABLE + "(paramName, description, value) VALUES (?, ?, ?);";
	
	/**
	 * TDG function for inserting new server parameters
	 * @param variableName
	 * @param description
	 * @param value
	 * @return Returns the number of rows that were affected by the SQL query.
	 * @throws SQLException
	 */
	public static int insert (String paramName, String description, String value) throws SQLException {
		Connection connection = DbRegistry.getDbConnection();
		PreparedStatement ps = connection.prepareStatement(INSERT);
		
		ps.setString(1, paramName);
		ps.setString(2, description);
		ps.setString(3, value);
		
		int rows = ps.executeUpdate();
		ps.close();
		
		return rows;
	}
	
	/**
	 * SQL query for deleting an existing server parameter
	 */
	protected final static String DELETE =
		"DELETE FROM " + TABLE + " WHERE paramName = ?;";
	
	/**
	 * TDG function for deleting existing server parameters
	 * @param paramName
	 * @return Returns the number of rows that were affected by the SQL query.
	 * @throws SQLException
	 */
	public static int delete (String paramName) throws SQLException {
		Connection connection = DbRegistry.getDbConnection();
		PreparedStatement ps = connection.prepareStatement(DELETE);
		
		ps.setString(1, paramName);
		
		int rows = ps.executeUpdate();
		ps.close();
		
		return rows;
	}
	
	/**
	 * SQL query for creating a new table for Server Parameters;
	 */
	protected final static String CREATE = 
		"CREATE TABLE " + TABLE + " (paramName varchar(64), description varchar(256), value varchar(32) NOT NULL, PRIMARY KEY (paramName));";

	/**
	 * SQL function for creating the table. Will throw SQLExceptin if table already exists.
	 * Public visibility so that the controller can call it. This function actually adds rows to the table since it is needed configuration information.
	 * @throws SQLException 
	 */
	public static void create() throws SQLException {
		Connection connection = DbRegistry.getDbConnection();
		PreparedStatement ps = connection.prepareStatement(CREATE);
		ps.executeUpdate();
		ps.close();
	
		for (String insertion : INSERTIONS) {
			ps = connection.prepareStatement(insertion);
			ps.executeUpdate();
			ps.close();
		}

		ps.close();
	}
	
	/**
	 * SQL query for dropping the ServerParameters table.
	 */
	protected final static String DROP = 
		"DROP TABLE " + TABLE + ";";
	
	/**
	 * SQL function for dropping the table. Will throw SQLException if table doesn't exist.
	 * @throws SQLException
	 */
	public static void drop() throws SQLException {
		Connection connection = DbRegistry.getDbConnection();
		PreparedStatement ps = connection.prepareStatement(DROP);
		
		ps.executeUpdate();
		ps.close();
	}
	
	/**
	 * SQL queries for inserting the configuration data we need. It is public for testing purposes.
	 */
	public static final String INSERTIONS[] = 
		{
		"INSERT INTO " + TABLE + " " +
		"VALUES ('minMessageSizeBytes', 'The minimum size of uploaded audio files that should be accepted, in bytes.', 2000);",

		"INSERT INTO " + TABLE + " " +
		"VALUES ('maxMessageSizeBytes', 'The maximum size of uploaded audio files that should be accepted, in bytes.', 50000);",

		"INSERT INTO " + TABLE + " " +
		"VALUES ('messageLifeDays', 'The time to live of regular messages. If a message is older than this amount, in days, it should be deleted.', 7);",

		"INSERT INTO " + TABLE + " " +
		"VALUES ('advertiserMessageLifeDays', 'The time to live of advertiser messages.', 30);",

		"INSERT INTO " + TABLE + " " +
		"VALUES ('minEmailLength', 'The minimum character length of email addresses when creating user accounts.', 15);",

		"INSERT INTO " + TABLE + " " +
		"VALUES ('maxEmailLength', 'The maximum character length of email addresses when creating user accounts.', 50);",

		"INSERT INTO " + TABLE + " " + 
		"VALUES ('minPasswordLength', 'The minimum character length of password when creating user accounts.', 6);",

		"INSERT INTO " + TABLE + " " + 
		"VALUES ('maxPasswordLength', 'The maximum character length of password when creating user accounts.', 20);",

		"INSERT INTO " + TABLE + " " + 
		"VALUES ('speedThreshold', 'The speed threshold to compare against a user requesting messages.', 15);",

		"INSERT INTO " + TABLE + " " + 
		"VALUES ('defaultMessageRadiusMeters', 'The default radius, in meters, in which to check if there are any messages.', 100);",

		"INSERT INTO " + TABLE + " " + 
		"VALUES ('minMessages', 'The minimum amount of messages fetched to the user.', 10);",

		"INSERT INTO " + TABLE + " " + 
		"VALUES ('maxMessages', 'The maximum amount of messages fetched to the user.', 50);",
		};
}
