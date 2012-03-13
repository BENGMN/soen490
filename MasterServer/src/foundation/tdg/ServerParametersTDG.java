package foundation.tdg;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import foundation.Database;



public class ServerParametersTDG {
	/**
	 * SQL table name of parameters
	 */
	public final static String TABLE = "ServerConfiguration";
	
	/**
	 * SQL query for updating an existing server parameter
	 */
	protected final static String UPDATE = 
		"UPDATE " + TABLE + " SET value = ? WHERE variableName = ?;";
	
	/**
	 * TDG function for updating exiting server parameters
	 * @param variableName
	 * @param value
	 * @return Returns the number of rows that were affected by the SQL query.
	 */
	public static int update (String variableName, double value) throws SQLException {
		Connection connection = Database.getConnection();
		PreparedStatement ps = connection.prepareStatement(UPDATE);
		
		ps.setDouble(1, value);
		ps.setString(2, variableName);
		
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
		"INSERT INTO " + TABLE + "(variableName, value) VALUES (?, ?);";
	
	/**
	 * TDG function for inserting new server parameters
	 * @param variableName
	 * @param value
	 * @return Returns the number of rows that were affected by the SQL query.
	 * @throws SQLException
	 */
	protected static int insert (String variableName, double value) throws SQLException {
		Connection connection = Database.getConnection();
		PreparedStatement ps = connection.prepareStatement(INSERT);
		
		ps.setString(1, variableName);
		ps.setDouble(2, value);
		
		int rows = ps.executeUpdate();
		ps.close();
		
		return rows;
	}
	
	/**
	 * SQL query for deleting an existing server parameter
	 */
	protected final static String DELETE =
		"DELETE FROM " + TABLE + " WHERE variableName = ?;";
	
	/**
	 * TDG function for deleting existing server parameters
	 * @param variableName
	 * @return Returns the number of rows that were affected by the SQL query.
	 * @throws SQLException
	 */
	protected static int delete (String variableName) throws SQLException {
		Connection connection = Database.getConnection();
		PreparedStatement ps = connection.prepareStatement(DELETE);
		
		ps.setString(1, variableName);
		
		int rows = ps.executeUpdate();
		ps.close();
		
		return rows;
	}
}
