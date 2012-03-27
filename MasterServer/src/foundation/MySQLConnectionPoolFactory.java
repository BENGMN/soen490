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

import java.sql.SQLException;

import org.slf4j.LoggerFactory;

import com.jolbox.bonecp.BoneCP;
import com.jolbox.bonecp.BoneCPConfig;

import ch.qos.logback.classic.Logger;
import foundation.registry.PropertiesRegistry;

public class MySQLConnectionPoolFactory {
	private static final String DRIVER = "com.mysql.jdbc.Driver";
	private static final String SCHEME = "jdbc:mysql://";
	private static Logger logger = (Logger) LoggerFactory.getLogger("application");
	
	// Default values
	private String hostname = "";
	private String database = "";
	private String username = "";
	private String password = "";
	
	private int minConnections = 15;
	private int maxConnections = 35;
	private int partitionCount = 1;
	
	public MySQLConnectionPoolFactory(String hostname, String database, String username, String password, int minConnections, int maxConnections, int partitionCount) throws SQLException {
		this(hostname, database, username, password);

		try {
			Class.forName(DRIVER);
		} catch (ClassNotFoundException e) {
			logger.error("MySQL Driver '{}' could not be loaded. Exception: {}", DRIVER, e.getMessage());
		}
		
		this.minConnections = minConnections;
		this.maxConnections = maxConnections;
		this.partitionCount = partitionCount;
		
	}

	public MySQLConnectionPoolFactory(String hostname, String database, String username, String password) throws SQLException {
		try {
			Class.forName(DRIVER);
		} catch (ClassNotFoundException e) {
			logger.error("MySQL Driver '{}' could not be loaded. Exception: {}", DRIVER, e.getMessage());
		}
		
		this.hostname = hostname;
		this.database = database;
		this.username = username;
		this.password = password;
		
	}
	
	public void defaultInitialization() throws SQLException {
		defaultInitialization("");
	}
	
	public void defaultInitialization(String db_id) throws SQLException {
		try {
			setHostname(PropertiesRegistry.getProperty(db_id + "hostname"));
			setDatabase(PropertiesRegistry.getProperty(db_id + "database"));
			setUsername(PropertiesRegistry.getProperty(db_id + "username"));
			setPassword(PropertiesRegistry.getProperty(db_id + "password"));
			setMaxConnections(PropertiesRegistry.getInt(db_id + "maxConnections"));
			setMinConnections(PropertiesRegistry.getInt(db_id + "minConnections"));
			setPartitionCount(PropertiesRegistry.getInt(db_id + "partitionCount"));
		} catch (Exception e) {
			throw new SQLException("defaultInitialization(" + db_id + ") failure. The following exception was thrown: ", e);
		}
	}
	
	public BoneCP createConnectionPool() throws SQLException {
		BoneCPConfig config = new BoneCPConfig();
		config.setJdbcUrl(SCHEME + hostname + "/" + database);
		config.setUsername(username);
		config.setPassword(password);
		config.setMinConnectionsPerPartition(minConnections);
		config.setMaxConnectionsPerPartition(maxConnections);
		config.setPartitionCount(partitionCount);
		BoneCP boneCP = new BoneCP(config);
		return boneCP;
	}
		
	/*
	 * GETTERS and SETTERS
	 */
	public String getMySQLDatabaseURL() {
		return SCHEME + getHostname() + "/" + getDatabase();
	}
	
	public String getHostname() {
		return hostname;
	}

	public void setHostname(String hostname) {
		this.hostname = hostname;
	}

	public String getDatabase() {
		return database;
	}

	public void setDatabase(String database) {
		this.database = database;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getMinConnections() {
		return minConnections;
	}

	public void setMinConnections(int minConnections) {
		this.minConnections = minConnections;
	}

	public int getMaxConnections() {
		return maxConnections;
	}

	public void setMaxConnections(int maxConnections) {
		this.maxConnections = maxConnections;
	}
	
	public int getPartitionCount() {
		return partitionCount;
	}

	public void setPartitionCount(int partitionCount) {
		this.partitionCount = partitionCount;
	}
}
