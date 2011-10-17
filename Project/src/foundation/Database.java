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

	public static Database getInstance()
	{
		if (singleton == null)
			singleton = new Database();
		return singleton;
	}
	


//	public void readDataBase() throws Exception {
//		try {
//			// This will load the MySQL driver, each DB has its own driver
//			Class.forName("com.mysql.jdbc.Driver");
//			// Setup the connection with the DB
//			connect = DriverManager
//					.getConnection("jdbc:mysql://localhost/feedback?"
//							+ "user=sqluser&password=sqluserpw");
//
//			// Statements allow to issue SQL queries to the database
//			statement = connect.createStatement();
//			// Result set get the result of the SQL query
//			resultSet = statement
//					.executeQuery("select * from FEEDBACK.COMMENTS");
//			writeResultSet(resultSet);
//
//			// PreparedStatements can use variables and are more efficient
//			preparedStatement = connect
//					.prepareStatement("insert into  FEEDBACK.COMMENTS values (default, ?, ?, ?, ? , ?, ?)");
//			// "myuser, webpage, datum, summery, COMMENTS from FEEDBACK.COMMENTS");
//			// Parameters start with 1
//			preparedStatement.setString(1, "Test");
//			preparedStatement.setString(2, "TestEmail");
//			preparedStatement.setString(3, "TestWebpage");
//			preparedStatement.setDate(4, new java.sql.Date(2009, 12, 11));
//			preparedStatement.setString(5, "TestSummary");
//			preparedStatement.setString(6, "TestComment");
//			preparedStatement.executeUpdate();
//
//			preparedStatement = connect
//					.prepareStatement("SELECT myuser, webpage, datum, summery, COMMENTS from FEEDBACK.COMMENTS");
//			resultSet = preparedStatement.executeQuery();
//			writeResultSet(resultSet);
//
//			// Remove again the insert comment
//			preparedStatement = connect
//			.prepareStatement("delete from FEEDBACK.COMMENTS where myuser= ? ; ");
//			preparedStatement.setString(1, "Test");
//			preparedStatement.executeUpdate();
//			
//			resultSet = statement
//			.executeQuery("select * from FEEDBACK.COMMENTS");
//			writeMetaData(resultSet);
//			
//		} catch (Exception e) {
//			throw e;
//		} finally {
//			close();
//		}
//
//	}
//
//	private void writeMetaData(ResultSet resultSet) throws SQLException {
//		// 	Now get some metadata from the database
//		// Result set get the result of the SQL query
//		
//		System.out.println("The columns in the table are: ");
//		
//		System.out.println("Table: " + resultSet.getMetaData().getTableName(1));
//		for  (int i = 1; i<= resultSet.getMetaData().getColumnCount(); i++){
//			System.out.println("Column " +i  + " "+ resultSet.getMetaData().getColumnName(i));
//		}
//	}

//	private void writeResultSet(ResultSet resultSet) throws SQLException {
//		// ResultSet is initially before the first data set
//		while (resultSet.next()) {
//			// It is possible to get the columns via name
//			// also possible to get the columns via the column number
//			// which starts at 1
//			// e.g. resultSet.getSTring(2);
//			String user = resultSet.getString("myuser");
//			String website = resultSet.getString("webpage");
//			String summery = resultSet.getString("summery");
//			Date date = resultSet.getDate("datum");
//			String comment = resultSet.getString("comments");
//			System.out.println("User: " + user);
//			System.out.println("Website: " + website);
//			System.out.println("Summery: " + summery);
//			System.out.println("Date: " + date);
//			System.out.println("Comment: " + comment);
//		}
//	}
}
