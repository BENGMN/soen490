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

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class MessageTDG {

	
	public static final String table = "Group";
	static Connection conn = null;
	
	public static ResultSet findAll() throws SQLException {
		String query = "SELECT * FROM " + table + " ORDER BY name ASC";
		PreparedStatement ps = conn.prepareStatement(query);
		ResultSet rs = ps.executeQuery();
		return rs;
	}

	public static ResultSet find(Long mid ) throws SQLException {
		String query = "SELECT * FROM " + table + " where id = ?";
		PreparedStatement ps = conn.prepareStatement(query);
		ps.setString(1, mid.toString());
		ResultSet rs = ps.executeQuery();
		return rs;
	}

	public static void insert(Long mid, Long uid, String message,float speed, double latitude , double longitude , java.sql.Date created_at , int user_rating) throws SQLException {
		
		String query = "INSERT INTO " + table + " (mid , uid , message , speed , latitude , longitude , created_at , user_rating) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
		PreparedStatement ps = conn.prepareStatement(query);
		
		ps.setString(1, mid.toString());
		ps.setString(2, uid.toString());
		ps.setString(3, message);
		ps.setFloat(4, speed);
		ps.setDouble(5, latitude);
		ps.setDouble(6, longitude);
		ps.setDate(7, created_at);
		ps.setInt(8, user_rating);
		
		ps.executeUpdate();
		ps.close();
		
	}
	
	public static int update(int version, Long mid, Long uid, String message,float speed, double latitude , double longitude , java.sql.Date created_at , int user_rating) throws SQLException {
		String query = "UPDATE " + table + " SET version = ?, mid = ?, uid = ?, message = ?, speed = ?, latitude = ?, longitude = ?, created_at = ? user_rating = ?  WHERE mid = ? AND version = ?";
		PreparedStatement ps = conn.prepareStatement(query);
		
		ps.setInt(1, version); 
		ps.setString(2, mid.toString()); //Is this supposed to be version + 1?
		ps.setString(3, uid.toString());
		ps.setString(4, message);
		ps.setFloat(5, speed);
		ps.setDouble(6, latitude);
		ps.setDouble(7, longitude);
		ps.setDate(8, created_at);
		ps.setInt(9, user_rating);	
		ps.setString(10, mid.toString());
		ps.setInt(11, version);
		
		int count = ps.executeUpdate();
		
		ps.close();
		return count;	
	}

	public static int delete(Long mid, int version) throws SQLException {
		String query = "DELETE FROM " + table + " WHERE mid = ? AND version = ?";
		PreparedStatement ps = conn.prepareStatement(query);
		ps.setString(1, mid.toString());
		ps.setInt(2, version);
		int count = ps.executeUpdate();
		ps.close();
		return count;
	}

}
