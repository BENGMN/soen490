package domain.serverparameter.mappers;

import java.sql.SQLException;

import domain.serverparameter.ServerParameter;
import foundation.tdg.ServerParameterTDG;

public class ServerParameterOutputMapper {	
	/**
	 * Mapper method to update a ServerParameter
	 * @param param
	 * @return Returns number of rows affected
	 * @throws SQLException
	 */
	public static int update (ServerParameter param) throws SQLException {
		return ServerParameterTDG.update(param.getParamName(), param.getDescription(), param.getValue());
	}
	
	/*
	 * These methods below should not really be called by application as it stands 2012-03-15
	 */
	
	/**
	 * Mapper method to delete a ServerParameter
	 * @param param
	 * @return Returns number of rows affected
	 * @throws SQLException
	 */
	public static int delete (ServerParameter param) throws SQLException {
		return ServerParameterTDG.delete(param.getParamName());
	}
	
	/**
	 * Mapper method to insert a ServerParameter
	 * @param param
	 * @return Returns number of rows affected
	 * @throws SQLException
	 */
	public static int insert (ServerParameter param) throws SQLException {
		return ServerParameterTDG.insert(param.getParamName(), param.getDescription(), param.getValue());
	}
}
