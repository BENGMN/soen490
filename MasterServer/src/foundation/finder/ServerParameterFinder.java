/**
 * SOEN 490
 * Capstone 2011
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
package foundation.finder;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import foundation.DbRegistry;
import foundation.tdg.ServerParameterTDG;

/**
 * Finder class for selecting server parameters
 * @author Soto
 *
 */
public class ServerParameterFinder {
	/**
	 * SQL query for selecting all the server parameters
	 */
	protected final static String SELECT_ALL =
		"SELECT * FROM " + ServerParameterTDG.TABLE + ";";
	
	/**
	 * Finder method for returning result set of all server parameters. 
	 * @return Returns ResultSet containing all the server parameters in the ServerConfiguration table
	 * @throws SQLException
	 */
	public static ResultSet findAll() throws SQLException {
		Connection connection = DbRegistry.getDbConnection();
		PreparedStatement ps = connection.prepareStatement(SELECT_ALL);
		
		ResultSet rs = ps.executeQuery();
		return rs;		
	}
	
	/**
	 * SQL query for selecting a single server parameter
	 */
	protected final static String SELECT = 
		"SELECT * FROM " + ServerParameterTDG.TABLE + " WHERE paramName = ?;";
	
	/**
	 * Finder method for returning result set containing a single server parameter.
	 * @param paramName
	 * @return Returns ResultSet containing the server parameter referenced by the passed variable name
	 * @throws SQLException
	 */
	public static ResultSet find(String paramName) throws SQLException {
		Connection connection = DbRegistry.getDbConnection();
		PreparedStatement ps = connection.prepareStatement(SELECT);
		
		ps.setString(1, paramName);
		
		ResultSet rs = ps.executeQuery();
		return rs;
	}
}
