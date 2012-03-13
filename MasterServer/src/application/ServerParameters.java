package application;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.HashMap;

import foundation.Database;
import foundation.finder.ServerParametersFinder;
import foundation.tdg.ServerParametersTDG;

/**
 * Singleton class that holds all the server parameters as a HashMap, 
 * where the key is the parameter name and the value is its value.
 * @author Anthony
 *
 */
public class ServerParameters extends HashMap<String, Double> {

	public final static String PREFIX = "PARAM";
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Singleton instance
	 */
	private static ServerParameters uniqueInstance = null;
	
	/**
	 * Constructs the unique instance.
	 * @throws SQLException
	 */
	private ServerParameters() throws SQLException {
		super();
		populateVariables();
	}

	/**
	 * Grabs all the parameters from the database and stores them in the HashMap.
	 * @throws SQLException
	 */
	protected void populateVariables() throws SQLException {
		ResultSet rs = ServerParametersFinder.findALL();
		
		while (rs.next()) {
			String variableName = rs.getString("variableName");
			double value = rs.getDouble("value");
			
			this.put(variableName, value);
		}
		
		rs.close();
		
		// Frees the connection from the connection pool
		// TODO change this
		Database.freeConnection();
	}
		
	/**
	 * Getter to retrieve the ServerParameters unique instance.
	 * @return Returns singleton ServerParameters instance
	 * @throws SQLException
	 */
	public static ServerParameters getUniqueInstance() throws SQLException {
		if (uniqueInstance == null)
			uniqueInstance = new ServerParameters();
		return uniqueInstance;
	}
}