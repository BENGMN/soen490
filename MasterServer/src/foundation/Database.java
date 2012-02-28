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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import com.jolbox.bonecp.BoneCP;
import com.jolbox.bonecp.BoneCPConfig;



//import java.util.Stack;

public class Database {
	private static Database singleton = null;
	private static Properties prop = new Properties();
	public static final ThreadLocal<Connection> threadConnection = new ThreadLocal<Connection>();
	private static BoneCP connectionPool = null;
	
	// File path to properties file
	private static final String PATH  = "Database.properties";
	
	static {
		try {
			// This will load the MySQL driver, each DB has its own driver
			Class.forName("com.mysql.jdbc.Driver");
			// Setup the connection with the DB
			prop.load(new FileInputStream(PATH));
			
			String host = prop.getProperty("hostname");
			String db = prop.getProperty("database");
			String username = prop.getProperty("username");
			String password = prop.getProperty("password");
			
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
	
	// If we want to pool connections we'd put the code in here; create at startup, and allocate connections on getConnection and freeConnection.
	private Database() throws IOException {
		// Note: The path cannot be resolved properly unless the container is running
		// LOCAL HACK: cd to MasterServer then ln -s /WebContent/WEB-INF (creates a symbolic link)
		String path = ServletInformation.getInstance().resolvePath("WEB-INF/Database.properties");
		
	}
	
	//Simply connects to the database using the correct credentials and returns a connection object.
	private Connection connect() throws SQLException {
		return null;
	}
	
	
	// This function returns a singleton threadlocal connection. 
	public static Connection getConnection() throws SQLException {
		
		if(threadConnection.get() == null) {
			Connection connection = connectionPool.getConnection();
			threadConnection.set(connection);
			return threadConnection.get();
		} else
			return threadConnection.get();
		
	}
	
	public static void freeConnection() throws SQLException {
		Connection connection = threadConnection.get();
		if (connection != null) {
			connection.close();
		}
	}
	
	private void freeConnection(Connection connection) throws SQLException {
		connection.close();
		threadConnection.remove();
	}
	
	public ResultSet query(String queryString, Object[] objects) throws SQLException
	{
		Connection connection = getConnection();
		if (connection == null)
			return null;
		PreparedStatement statement = connection.prepareStatement(queryString);
		if (objects != null) {
			for (int c = 0; c < objects.length; ++c)
				statement.setObject(c, objects[c]);
		}
		ResultSet rs = statement.executeQuery();
		freeConnection(connection);
		return rs;
	}
	
	public ResultSet query(String queryString) throws SQLException
	{
		return query(queryString, null);
	}
	
	public int update(String queryString, Object[] objects) throws SQLException
	{
		Connection connection = getConnection();
		PreparedStatement statement = connection.prepareStatement(queryString);
		if (objects != null) {
			for (int c = 0; c < objects.length; ++c)
				statement.setObject(c, objects[c]);
		}
		int result = statement.executeUpdate();
		freeConnection(connection);
		return result;
	}
	
	public int update(String queryString) throws SQLException
	{
		return update(queryString, null);
	}
	
	public boolean runFile(String path) throws IOException, SQLException
	{
		BufferedReader fileReader = new BufferedReader(new FileReader(path));
		String line;
		while ((line = fileReader.readLine()) != null)
		{
			update(line, null);
		}
		return true;
	}
	
	public boolean hasTable(String tableName) throws SQLException
	{
		Connection connection = getConnection();
		DatabaseMetaData metaData = connection.getMetaData();
		ResultSet tables = metaData.getTables(null, null, tableName, null);
		return tables.next();
	}
	
	public PreparedStatement getStatement(String query) throws SQLException
	{
		Connection connection = getConnection();
		return connection.prepareStatement(query);
	}

	public static Database getInstance() throws IOException
	{
		if (singleton == null)
			singleton = new Database();
		return singleton;
	}
	
	public void createDatabase() throws SQLException, IOException
	{
		if (!hasTable(UserTDG.TABLE))
			UserTDG.create();
		if (!hasTable(MessageTDG.TABLE))
			MessageTDG.create();
	}
	
	public void dropDatabase() throws SQLException, IOException
	{
		if (hasTable(UserTDG.TABLE))
			UserTDG.drop();
		if (hasTable(MessageTDG.TABLE))
			MessageTDG.drop();
	}
	
	// Refactor the hasTable method to be parameterized
	// Note this method is called from the frontController
	public boolean isDatabaseCreated() throws SQLException
	{
		return hasTable(UserTDG.TABLE) && hasTable(MessageTDG.TABLE); 
	}
}
