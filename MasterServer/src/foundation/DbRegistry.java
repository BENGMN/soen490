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
package foundation;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import com.jolbox.bonecp.BoneCP;

import foundation.tdg.MessageTDG;
import foundation.tdg.ServerListTDG;
import foundation.tdg.ServerParameterTDG;
import foundation.tdg.UserTDG;

/**
 * 
 *
 */
public class DbRegistry extends ThreadLocal<Connection> {
	private static HashMap<String, DbRegistry> internalDbRegistry = new HashMap<String, DbRegistry>();

	private BoneCP connectionPool;
	private String tablePrefix = null;

	private DbRegistry() {
		super();
	}
	
	public static Connection getDbConnection(String key) throws SQLException {
		DbRegistry db = getInternalDbRegistry(key);
		Connection connection = db.get();
		
		if (connection == null) {
			connection = db.connectionPool.getConnection();
			db.set(connection);
			connection = db.get();
		}
		return connection;
	}
	
	public static Connection getDbConnection() throws SQLException {
		return getDbConnection("");
	}

	public static void closeDbConnection(String key) throws SQLException {
		DbRegistry db = getInternalDbRegistry(key);
		Connection connection = db.get();
		if (connection != null) {
			db.remove();
			connection.close();
		} 
	}
	
	public static void closeDbConnection() throws SQLException {
		closeDbConnection("");
	}
	/**
	 * Checks if the database has a table identified by the given table name
	 * @param tableName A table name to check for in the Database
	 * @return Returns true if the table exists, false otherwise.
	 * @throws SQLException
	 */
	public static boolean hasTable(String key, String tableName) throws SQLException {
		Connection connection = getDbConnection(key);
		
		DatabaseMetaData metaData = connection.getMetaData();
		ResultSet tables = metaData.getTables(null, null, tableName, null);
		boolean hasTable = tables.next();
		tables.close();
		
		return hasTable;
	}

	public static boolean hasTable(String tableName) throws SQLException {
		return hasTable("", tableName);
	}
	
	/*
	 * The following methods are used to create or drop the database in is entirety
	 */
	public static void createDatabaseTables(String key) throws SQLException {
		if (!hasTable(key, UserTDG.TABLE))
			UserTDG.create();
		if (!hasTable(key, MessageTDG.TABLE))
			MessageTDG.create();
		if (!hasTable(key, ServerListTDG.TABLE))
			ServerListTDG.create();
		if (!hasTable(key, ServerParameterTDG.TABLE))
			ServerParameterTDG.create();
	}
	
	public static void createDatabaseTables() throws SQLException {
		createDatabaseTables("");
	}
	
	public static void dropDatabaseTables(String key) throws SQLException {
		if (hasTable(key, UserTDG.TABLE))
			UserTDG.drop();
		if (hasTable(key, MessageTDG.TABLE))
			MessageTDG.drop();
		if (hasTable(key, ServerListTDG.TABLE))
			ServerListTDG.drop();
		if (hasTable(key, ServerParameterTDG.TABLE))
			ServerParameterTDG.drop();
	}
	
	public static void dropDatabaseTables() throws SQLException {
		dropDatabaseTables("");
	}
	
	public static boolean isDatabaseCreated(String key) throws SQLException {
		return hasTable(key, UserTDG.TABLE) && hasTable(key, MessageTDG.TABLE) && hasTable(key, ServerListTDG.TABLE) && hasTable(key, ServerParameterTDG.TABLE); 
	}

	public static boolean isDatabaseCreated() throws SQLException {
		return isDatabaseCreated("");
	}
	
	protected static DbRegistry getInternalDbRegistry(String connectionKey) {
		if (internalDbRegistry.get(connectionKey) == null) 
			internalDbRegistry.put(connectionKey, new DbRegistry());
		return internalDbRegistry.get(connectionKey);
	}
	
	protected static DbRegistry getInternalDbRegistry() {
		return getInternalDbRegistry("");
	}
	
	// Getters and Setters
	public static void setConnectionPool(String connectionKey, BoneCP connectionPool) {
		getInternalDbRegistry(connectionKey).connectionPool = connectionPool;
	}
	
	public static void setConnectionPool(BoneCP connectionPool) {
		setConnectionPool("", connectionPool);
	}
	
	public static void setTablePrefix(String connectionKey, String tablePrefix) {
		getInternalDbRegistry(connectionKey).tablePrefix = tablePrefix;
	}
	
	public static void setTablePrefix(String tablePrefix) {
		setTablePrefix("", tablePrefix);
	}

	public static String getTablePrefix(String key) {
		if( getInternalDbRegistry(key).tablePrefix == null) 
			throw new NullPointerException("Undefined Table Prefix");
		return getInternalDbRegistry(key).tablePrefix;
	}
	
	public static String getTablePrefix() {
		return getTablePrefix("");
	}
}
