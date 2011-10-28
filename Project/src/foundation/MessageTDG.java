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

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class MessageTDG {

	
	public static final String TABLE = "Group";
	
	public static ResultSet findAll() throws SQLException {
		String query = "SELECT m.mid, m.uid, m.message, m.speed, m.latitude, m.longitude, m.created_at, m.user_rating, m.version, FROM " + TABLE + " AS m";
		return Database.getInstance().query(query, null);
		/*PreparedStatement ps = Database.getInstance().getStatement(query);
		ResultSet rs = ps.executeQuery();
		return rs;*/
	}

	public static ResultSet find(long mid) throws SQLException {
		String query = "SELECT m.mid, m.uid, m.message, m.speed, m.latitude, m.longitude, m.created_at, m.user_rating, m.version FROM " + TABLE + " AS m WHERE m.mid=?";
		Object objects[] = {mid};
		return Database.getInstance().query(query, objects);
		/*PreparedStatement ps = Database.getInstance().getStatement(query);
		ps.setString(1, mid.toString());
		ResultSet rs = ps.executeQuery();
		return rs;*/
	}

	public static void insert(long mid, long uid, String message,float speed, double latitude , double longitude , java.sql.Date created_at , int user_rating) throws SQLException {
		
		String query = "INSERT INTO " + TABLE + " (mid , uid , message , speed , latitude , longitude , created_at , user_rating) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
		Object[] objects = {mid, uid, message, speed, latitude, longitude, created_at, user_rating};
		Database.getInstance().update(query, objects);
		/*PreparedStatement ps = Database.getInstance().getStatement(query);
		
		ps.setString(1, mid.toString());
		ps.setString(2, uid.toString());
		ps.setString(3, message);
		ps.setFloat(4, speed);
		ps.setDouble(5, latitude);
		ps.setDouble(6, longitude);
		ps.setDate(7, created_at);
		ps.setInt(8, user_rating);
		
		ps.executeUpdate();
		ps.close();*/
		
	}
	
	public static int update(int version, long mid, long uid, String message,float speed, double latitude , double longitude , java.sql.Date created_at , int user_rating) throws SQLException {
		String query = "UPDATE " + TABLE + " SET version = ?, mid = ?, uid = ?, message = ?, speed = ?, latitude = ?, longitude = ?, created_at = ? user_rating = ?  WHERE mid = ? AND version = ?";
		Object[] objects = {version+1, mid, uid, message, speed, latitude, longitude, created_at, user_rating, mid, version};
		return Database.getInstance().update(query, objects);
		/*PreparedStatement ps = Database.getInstance().getStatement(query);
		
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
		
		return count;*/	
	}

	public static int delete(long mid, int version) throws SQLException {
		String query = "DELETE FROM " + TABLE + " WHERE mid = ? AND version = ?";
		Object[] objects = {mid, version};
		return Database.getInstance().update(query, objects);
		/*PreparedStatement ps = Database.getInstance().getStatement(query);
		ps.setString(1, mid.toString());
		ps.setInt(2, version);
		int count = ps.executeUpdate();
		ps.close();
		return count;*/
	}

}
