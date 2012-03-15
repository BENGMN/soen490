package foundation.tdg;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import foundation.Database;



public class ServerParameterTDG {
	/**
	 * SQL table name of parameters
	 */
	public final static String TABLE = "ServerParameters";
	
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
		Connection connection = Database.getConnection();
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
		Connection connection = Database.getConnection();
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
		Connection connection = Database.getConnection();
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
	 * Public visibility so that the controller can call it.
	 * @throws SQLException 
	 */
	public static void create() throws SQLException {
		Connection connection = Database.getConnection();
		PreparedStatement ps = connection.prepareStatement(CREATE);
		
		ps.executeUpdate();
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
		Connection connection = Database.getConnection();
		PreparedStatement ps = connection.prepareStatement(DROP);
		
		ps.executeUpdate();
		ps.close();
	}
}
