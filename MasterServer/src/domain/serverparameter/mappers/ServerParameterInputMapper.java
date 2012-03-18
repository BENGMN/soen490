package domain.serverparameter.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import domain.serverparameter.ServerParameter;

import exceptions.MapperException;
import foundation.finder.ServerParameterFinder;

/**
 * Mapper class for finding ServerParameters
 * @author Soto
 *
 */
public class ServerParameterInputMapper {
	/**
	 * Mapper method to find a single ServerParameter by name.
	 * @param paramName
	 * @return Returns the found ServerParameter.
	 * @throws SQLException
	 * @throws MapperException
	 */
	public static ServerParameter find(String paramName) throws SQLException, MapperException {
		ServerParameter param = null;
		
		ResultSet rs = ServerParameterFinder.find(paramName);
		
		if (!rs.next()) 
			throw new MapperException("Server parameter with name: " + paramName + " does not exist.");
		
		param = getServerParameter(rs);
		
		rs.close();
		
		return param;
	}
	

	/**
	 * Mapper method to find all ServerParameters.
	 * @return Returns a list containing all the ServerParameters found.
	 * @throws SQLException
	 */
	public static List<ServerParameter> findAll() throws SQLException {
		List<ServerParameter> params = new LinkedList<ServerParameter>();
		ServerParameter param = null;
		
		ResultSet rs = ServerParameterFinder.findAll();
		
		while (rs.next()) {
			param = getServerParameter(rs);
			
			params.add(param);
		}
		
		rs.close();
		
		return params;
	}
	
	/**
	 * Takes the current ResultSet position and creates a ServerParameter.
	 * @param rs
	 * @return Returns the ServerParameter contained in the current ResultSet position.
	 * @throws SQLException
	 */
	private static ServerParameter getServerParameter(ResultSet rs) throws SQLException{
		ServerParameter param = null;
		String paramName = rs.getString("paramName");
		String description = rs.getString("description");
		String value = rs.getString("value");
		
		param = new ServerParameter(paramName, description, value);
		return param;
	}
}
