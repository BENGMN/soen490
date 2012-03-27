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
package application;

import java.sql.SQLException;

import java.util.HashMap;
import java.util.List;

import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.Logger;

import domain.serverparameter.ServerParameter;
import domain.serverparameter.mappers.ServerParameterInputMapper;
import exceptions.MapperException;


/**
 * Singleton class that holds all the server parameters as a HashMap, 
 * where the key is the parameter name and the value is its value.
 * The class also acts as an Identity Map so that parameter are only loaded once.
 * @author Anthony
 *
 */
public class ServerParameters extends HashMap<String, ServerParameter> {

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
		updateParameters();
	}

	/**
	 * Overriden get(key) method. It will first check if the underlying Map has a value for the given key. If it doesn't, returns false. 
	 * If it does, it will check the database for an always up-to-date value and return that.
	 */
	@Override
	public ServerParameter get(Object key) {
		ServerParameter param = null;
		
		// If the underlying map doesn't have it, then return null
		if (super.get(key) == null)
			return null;
		
		Logger logger = (Logger) LoggerFactory.getLogger("application");
	
		try {
			param = ServerParameterInputMapper.find((String)key);
			this.put((String)key, param);
		} catch (SQLException e) {
			
			logger.error("SQLExcepion occurred when trying the retrieve a parameter from the database: {}", e);
		} catch (MapperException e) {
			// This error should not occur, since we alreayd pulled from the database once
			logger.error("ServerParameter with name '{}' does not exist.", (String)key);
		}
		return param;
	}
	
	/**
	 * Grabs all the parameters from the database and stores them in the HashMap.
	 * @throws SQLException
	 */
	public void updateParameters() throws SQLException {
		List<ServerParameter> list = ServerParameterInputMapper.findAll();
		
		// for each item in the list, add it to this map
		for(ServerParameter param: list) {
			this.put(param.getParamName(), param);
		}
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