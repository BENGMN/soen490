package application;

import java.sql.SQLException;

import java.util.HashMap;
import java.util.List;

import domain.serverparameter.ServerParameter;
import domain.serverparameter.mappers.ServerParameterInputMapper;


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