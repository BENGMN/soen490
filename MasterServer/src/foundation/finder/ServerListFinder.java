package foundation.finder;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import foundation.Database;
import foundation.tdg.ServerListTDG;

public class ServerListFinder {
	/**
	 * SQL query for selecting all the server parameters
	 */
	protected final static String SELECT_ALL =
		"SELECT * FROM " + ServerListTDG.TABLE + ";";
	
	/**
	 * Finder method for returning result set of all server parameters. 
	 * @return Returns ResultSet containing all the server parameters in the ServerConfiguration table
	 * @throws SQLException
	 */
	public static ResultSet findAll() throws SQLException {
		Connection connection = Database.getConnection();
		PreparedStatement ps = connection.prepareStatement(SELECT_ALL);
		
		ResultSet rs = ps.executeQuery();
		return rs;		
	}
	
	/**
	 * SQL query for selecting a single server parameter
	 */
	protected final static String SELECT = 
		"SELECT * FROM " + ServerListTDG.TABLE + " WHERE hostname = ?;";
	
	/**
	 * Finder method for returning result set containing a single server parameter.
	 * @param paramName
	 * @return Returns ResultSet containing the server parameter referenced by the passed variable name
	 * @throws SQLException
	 */
	public static ResultSet find(String hostname) throws SQLException {
		Connection connection = Database.getConnection();
		PreparedStatement ps = connection.prepareStatement(SELECT);
		
		ps.setString(1, hostname);
		
		ResultSet rs = ps.executeQuery();
		return rs;
	}
}