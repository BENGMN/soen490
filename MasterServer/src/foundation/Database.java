/**
 * SOEN 490
 * Capstone 2011
 * Table Data Gateway for the User Domain Object
 * Team members: 	Sotirios Delimanolis
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

import java.io.FileNotFoundException;
import java.io.IOException;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import com.jolbox.bonecp.BoneCP;
import com.jolbox.bonecp.BoneCPConfig;

import foundation.tdg.MessageTDG;
import foundation.tdg.ServerListTDG;
import foundation.tdg.ServerParameterTDG;
import foundation.tdg.UserTDG;



//import java.util.Stack;

public class Database {
	private static Properties prop = new Properties();
	public static final ThreadLocal<Connection> threadConnection = new ThreadLocal<Connection>();
	private static BoneCP connectionPool = null;
	
	protected static boolean TESTING;
	
	// File path to properties file
	private static final String FILENAME = "Database.properties";
	
	static {
		try {
			// This will load the MySQL driver, each DB has its own driver
			Class.forName("com.mysql.jdbc.Driver");
			
			// Loads properties file
			prop.load(Database.class.getClassLoader().getResourceAsStream(FILENAME));
			
			TESTING = Boolean.getBoolean(prop.getProperty("testing"));
					
			String host = prop.getProperty("hostname");
			String db;
			if(TESTING)
				db = prop.getProperty("testdatabase");
			else
				db = prop.getProperty("database");
			
			String username = prop.getProperty("username");
			String password = prop.getProperty("password");
			
			// Setup the connection with the DB
			BoneCPConfig config = new BoneCPConfig();
			config.setJdbcUrl("jdbc:mysql://" + host + "/" + db);
			config.setUsername(username);
			config.setPassword(password);
			config.setMinConnectionsPerPartition(15);
			config.setMaxConnectionsPerPartition(35);
			config.setPartitionCount(1);
			connectionPool = new BoneCP(config);
			
		} catch (SQLException e) {			
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}

	
	// If we want to pool connections we'd put the code in here; create at startup, and allocate connections on getConnection and freeConnection.
	private Database() throws IOException {
		// Note: The path cannot be resolved properly unless the container is running
		// LOCAL HACK: cd to MasterServer then ln -s /WebContent/WEB-INF (creates a symbolic link)
		//String path = ServletInformation.getInstance().resolvePath("WEB-INF/Database.properties");
		
	}
	
	/**
	 * If the current thread doesn't have one, retrieves a connection from the pool, assigns it to the current thread, and returns it.
	 * Otherwise returns the threadlocal connection. 
	 * @return Returns a threadlocal connection
	 * @throws SQLException
	 */
	public static Connection getConnection() throws SQLException {
		
		if(threadConnection.get() == null) {
			Connection connection = connectionPool.getConnection();
			threadConnection.set(connection);
			return threadConnection.get();
		} else
			return threadConnection.get();
		
	}
	
	/**
	 * If the current thread has a connection, closes it.
	 * @throws SQLException
	 */
	public static void freeConnection() throws SQLException {
		Connection connection = threadConnection.get();
		if (connection != null) {
			connection.close();
			threadConnection.remove();
		}
	}
		
	/**
	 * Checks if the Database has a table identified by the given table name
	 * @param tableName A table name to check for in the Database
	 * @return Returns true if the table exists, false otherwise.
	 * @throws SQLException
	 */
	public static boolean hasTable(String tableName) throws SQLException {
		Connection connection = getConnection();
		
		DatabaseMetaData metaData = connection.getMetaData();
		ResultSet tables = metaData.getTables(null, null, tableName, null);
		boolean hasTable = tables.next();
		tables.close();
		
		return hasTable;
	}

	/*
	 * The following methods are used to create or drop the database in is entirety
	 */
	public static void createDatabaseTables() throws SQLException {
		if (!hasTable(UserTDG.TABLE))
			UserTDG.create();
		if (!hasTable(MessageTDG.TABLE))
			MessageTDG.create();
		if (!hasTable(ServerListTDG.TABLE))
			ServerListTDG.create();
		if (!hasTable(ServerParameterTDG.TABLE))
			ServerParameterTDG.create();
	}
	
	public static void dropDatabaseTables() throws SQLException {
		if (hasTable(UserTDG.TABLE))
			UserTDG.drop();
		if (hasTable(MessageTDG.TABLE))
			MessageTDG.drop();
		if (hasTable(ServerListTDG.TABLE))
			ServerListTDG.drop();
		if (hasTable(ServerParameterTDG.TABLE))
			ServerParameterTDG.drop();
	}
	
	public static boolean isDatabaseCreated() throws SQLException {
		return hasTable(UserTDG.TABLE) && hasTable(MessageTDG.TABLE) && hasTable(ServerListTDG.TABLE) && hasTable(ServerParameterTDG.TABLE); 
	}
	
}
