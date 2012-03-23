package foundation.tdg;

import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import foundation.DbRegistry;

public class ServerListTDG {

	public final static String TABLE = DbRegistry.getTablePrefix() +  "ServerList";
	
//	/**
//	 * SQL query for updating an existing server parameter
//	 */
//	protected final static String UPDATE = 
//		"UPDATE " + TABLE + " SET hostname = ?;";
//	
//	/**
//	 * TDG function for updating exiting hostname. 
//	 * @param hostname
//	 * @return Returns the number of rows that were affected by the SQL query.
//	 */
//	public static int update (String hostname) throws SQLException {
//		Connection connection = Database.getConnection();
//		PreparedStatement ps = connection.prepareStatement(UPDATE);
//		
//		ps.setString(1, hostname);
//		
//		int rows = ps.executeUpdate();
//		ps.close();
//		
//		return rows;
//	}
	
	
	/**
	 * SQL query for inserting a new hostname
	 */
	protected final static String INSERT = 
		"INSERT INTO " + TABLE + "(hostname, port) VALUES (?, ?);";
	
	/**
	 * TDG function for inserting the server's hostname.
	 * @return Returns the number of rows that were affected by the SQL query.
	 * @throws SQLException
	 * @throws UnknownHostException 
	 */
	public static int insert (String hostname, int port) throws SQLException {
		Connection connection = DbRegistry.getDbConnection();
		PreparedStatement ps = connection.prepareStatement(INSERT);

		ps.setString(1, hostname);
		ps.setInt(2, port);
		
		int rows = ps.executeUpdate();
		ps.close();
		
		return rows;
	}
	
	/**
	 * SQL query for deleting an existing server parameter
	 */
	protected final static String DELETE =
		"DELETE FROM " + TABLE + " WHERE hostname = ?;";
	
	/**
	 * TDG function for deleting existing server parameters
	 * @param paramName
	 * @return Returns the number of rows that were affected by the SQL query.
	 * @throws SQLException
	 */
	public static int delete (String hostname) throws SQLException {
		Connection connection = DbRegistry.getDbConnection();
		PreparedStatement ps = connection.prepareStatement(DELETE);
		
		ps.setString(1, hostname);
		
		int rows = ps.executeUpdate();
		ps.close();
		
		return rows;
	}
	
	private final static String CREATE_TABLE =
			"CREATE TABLE " + TABLE + " " +
			"(hostname varchar(255) NOT NULL," +
			"port integer NOT NULL," +
			"PRIMARY KEY (hostname));";
		
		/**
		 * Creates the table ServerList in the database.
		 * @throws SQLException 
		 */
		public static void create() throws SQLException {
			Connection connection = DbRegistry.getDbConnection();
			PreparedStatement ps = connection.prepareStatement(CREATE_TABLE);
			
			ps.executeUpdate();
			ps.close();
		}
		
		private final static String DROP_TABLE =
			"DROP TABLE " + TABLE + ";";	

		/**
		 * Drops the table ServerList from the database.
		 * @throws SQLException
		 */
		public static void drop() throws SQLException {
			Connection connection = DbRegistry.getDbConnection();
			PreparedStatement ps = connection.prepareStatement(DROP_TABLE);
			
			ps.executeUpdate();
			ps.close();
		}
}
