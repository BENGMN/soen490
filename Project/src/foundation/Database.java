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

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

public class Database {
	private static Database singleton = null;
	private Properties prop = new Properties();

	private Database() {
		try {
			prop.load(new FileInputStream("src/foundation/Database.properties"));
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private Connection getConnection() throws SQLException
	{
		try {
			// This will load the MySQL driver, each DB has its own driver
			Class.forName("com.mysql.jdbc.Driver");
			// Setup the connection with the DB
			Connection connection = DriverManager
					.getConnection("jdbc:mysql://" + prop.getProperty("hostname") + "/" + prop.getProperty("database") + "?"
							+ "user=" + prop.getProperty("username") + "&password=" + prop.getProperty("password"));
			return connection;
		}
		catch (SQLException e) {
			throw e;
		}
		catch (ClassNotFoundException e) {
			
		}
		return null;
	}
	
	private void freeConnection(Connection connection) throws SQLException
	{
		connection.close();
	}
	
	public ResultSet query(String queryString, Object[] objects) throws SQLException{
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
	
	public int update(String queryString, Object[] objects) throws SQLException{
		Connection connection = getConnection();
		PreparedStatement statement = connection.prepareStatement(queryString);
		for (int c = 0; c < objects.length; ++c)
			statement.setObject(c, objects[c]);
		int result = statement.executeUpdate();
		freeConnection(connection);
		return result;
	}
	
	public PreparedStatement getStatement(String query) throws SQLException
	{
		Connection connection = getConnection();
		return connection.prepareStatement(query);
	}

	public static Database getInstance()
	{
		if (singleton == null)
			singleton = new Database();
		return singleton;
	}
}
