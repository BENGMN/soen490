/**
 * SOEN 490
 * Capstone 2011
 * Table Data Gateway for the User Domain Object
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

import java.io.IOException;
import java.math.BigInteger;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import technical.Coordinate;
import technical.GeoSpatialSearch;

/**
 * Foundation class for executing finds on Message table
 * @author Moving Target
 */
public class MessageFinder {
	private final static String SELECT_ALL = 
			"SELECT m.mid, " +
			"m.uid, " +
			"m.message, " +
			"m.speed, " +
			"m.latitude, " +
			"m.longitude, " +
			"m.created_at, " +
			"m.user_rating, " +
			"m.version " +
			"FROM " + MessageTDG.TABLE + " AS m";
		
		/**
		 * Finds all messages in the Message table
		 * @return Returns the ResultSet containing the message information as 
		 * m.mid, m.uid, m.message, m.speed, m.latitude, m.longitude, m.created_at, m.user_rating, m.version
		 * @throws SQLException
		 */
		public static ResultSet findAll() throws SQLException, IOException {
			PreparedStatement ps = Database.getInstance().getStatement(SELECT_ALL);
			ResultSet rs = ps.executeQuery();
			return rs;
		}

		private static final String SELECT =
			"SELECT m.mid, " +
			"m.uid, " +
			"m.message, " +
			"m.speed, " +
			"m.latitude, " +
			"m.longitude, " +
			"m.created_at, " +
			"m.user_rating, " +
			"m.version " +
			"FROM " + MessageTDG.TABLE + " AS m " +
			"WHERE m.mid = ?;";
		
		/** 
		 * Finds a message with the passed message id
		 * @param mid Message id
		 * @return Returns the ResultSet containing the message information as
		 * m.mid, m.uid, m.message, m.speed, m.latitude, m.longitude, m.created_at, m.user_rating, m.version
		 * @throws SQLException
		 */
		public static ResultSet find(BigInteger mid) throws SQLException, IOException {
			PreparedStatement ps = Database.getInstance().getStatement(SELECT);
			ps.setObject(1, mid);
			ResultSet rs = ps.executeQuery();
			return rs;
		}
		
		private static final String SELECT_BY_EMAIL =
				"SELECT * FROM " + MessageTDG.TABLE + " AS m WHERE m.uid = ?;";
		
		public static ResultSet findByUser(long uid) throws SQLException, IOException {
			PreparedStatement ps = Database.getInstance().getStatement(SELECT_BY_EMAIL);
			ps.setLong(1, uid);
			ResultSet rs = ps.executeQuery();
			return rs;
		}
		
		/**
		 * 
		 * @param longitude
		 * @param latitude
		 * @param radius
		 * @return All points within a bounding rectangle that has a half cross-section of radius.
		 * @throws SQLException
		 */		
		// Good explanation of how to create a fast query that is within particular bounds.
		// http://www.scribd.com/doc/2569355/Geo-Distance-Search-with-MySQL
		private static final String SELECT_BY_RADIUS =
				"SELECT m.mid, m.uid, m.message, m.speed, m.latitude, m.longitude, m.created_at, m.user_rating, m.version " +
				"FROM " + MessageTDG.TABLE + " AS m " + "WHERE m.longitude BETWEEN ? AND ? AND m.latitude BETWEEN ? AND ?;";
		
		public static ResultSet findInProximity(double longitude, double latitude, double radius) throws SQLException, IOException {
			PreparedStatement ps = Database.getInstance().getStatement(SELECT_BY_RADIUS);
			List<Coordinate> rectangle = GeoSpatialSearch.convertPointToRectangle(new Coordinate(longitude, latitude), radius);
			ps.setDouble(1, rectangle.get(0).getLongitude());
			ps.setDouble(2, rectangle.get(1).getLongitude());
			ps.setDouble(3, rectangle.get(0).getLatitude());
			ps.setDouble(4, rectangle.get(1).getLatitude());
			ResultSet rs = ps.executeQuery();
			return rs;
		}
		
		private static final String SELECT_ID_BY_RADIUS =
				"SELECT m.mid " +
				"FROM " + MessageTDG.TABLE + " AS m " + "WHERE m.longitude BETWEEN ? AND ? AND m.latitude BETWEEN ? AND ?;";
		
		public static ResultSet findIdsInProximity(double longitude, double latitude, double radius) throws SQLException, IOException {
			PreparedStatement ps = Database.getInstance().getStatement(SELECT_ID_BY_RADIUS);
			List<Coordinate> rectangle = GeoSpatialSearch.convertPointToRectangle(new Coordinate(longitude, latitude), radius);
			ps.setDouble(1, rectangle.get(0).getLongitude());
			ps.setDouble(2, rectangle.get(1).getLongitude());
			ps.setDouble(3, rectangle.get(0).getLatitude());
			ps.setDouble(4, rectangle.get(1).getLatitude());
			ResultSet rs = ps.executeQuery();
			return rs;
		}
		
}
