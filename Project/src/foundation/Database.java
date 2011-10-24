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

	private Connection connect = null;
	private static Database singleton = null;
	private Properties prop = new Properties();

	private Database() {
		try {
			prop.load(new FileInputStream("Database.properties"));
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void connect() throws SQLException{
		try {
			// This will load the MySQL driver, each DB has its own driver
			Class.forName("com.mysql.jdbc.Driver");
			// Setup the connection with the DB
			connect = DriverManager
					.getConnection("jdbc:mysql://" + prop.getProperty("hostname") + "/" + prop.getProperty("database") + "?"
							+ "user=" + prop.getProperty("username") + "&password=" + prop.getProperty("password"));

		}
		catch (SQLException e) {
			throw e;
		}
		catch (ClassNotFoundException e) {
			
		}
	}
	
	public ResultSet query(String queryString, Object[] objects) throws SQLException{
		if(connect == null){
			this.connect();
		}
		PreparedStatement statement = connect.prepareStatement(queryString);
		if (objects != null) {
			for (int c = 0; c < objects.length; ++c)
				statement.setObject(c, objects[c]);
		}
		return statement.executeQuery();
	}
	
	public int update(String queryString, Object[] objects) throws SQLException{
		if(connect == null){
			this.connect();
		}
		PreparedStatement statement = connect.prepareStatement(queryString);
		for (int c = 0; c < objects.length; ++c)
			statement.setObject(c, objects[c]);
		return statement.executeUpdate();
	}
	
	
	// You need to close the connection
	private void close() {
		try {
			if (connect != null) {
				connect.close();
			}
		} catch (Exception e) {

		}
	}
	
	public PreparedStatement getStatement(String query) throws SQLException
	{
		if(connect == null){
			this.connect();
		}
		return connect.prepareStatement(query);
	}

	public static Database getInstance()
	{
		if (singleton == null)
			singleton = new Database();
		return singleton;
	}
}
