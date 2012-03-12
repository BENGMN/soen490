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
 * Singleton class that holds all the server parameters.
 * @author Anthony
 *
 */
public class ServerParameters {

	/**
	 * Singleton instance
	 */
	private static ServerParameters uniqueInstance = null;

	/**
	 * HashMap holding all the server parameters, where the key is the parameter name and the value is its value.
	 */
	private HashMap<String, Double> parameters;
	
	/**
	 * Constructs the unique instance.
	 * @throws SQLException
	 */
	private ServerParameters() throws SQLException {
		parameters = new HashMap<String, Double>();
		populateVariables();
	}
	/**
	 * SQL query for selecting all variable names and their values.
	 */
	private final static String SELECT = 
			"SELECT * " + 
			"FROM " + ServerParametersTDG.TABLE + ";";

	/**
	 * Grabs all the parameters from the database and stores them in the HashMap.
	 * @throws SQLException
	 */
	protected void populateVariables() throws SQLException {
		ResultSet rs = ServerParametersFinder.findALL();
		
		while (rs.next()) {
			String variableName = rs.getString("variableName");
			double value = rs.getDouble("value");
			
			parameters.put(variableName, value);
		}
		
		rs.close();
		
		// Frees the connection from the conenction pool
		Database.freeConnection();
	}
	
	/**
	 * Getter method for individual parameters.
	 * @param parameterName Name of the parameter
	 * @return Returns the value for given parameter
	 */
	public double getValue(String parameterName) {
		return parameters.get(parameterName);
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