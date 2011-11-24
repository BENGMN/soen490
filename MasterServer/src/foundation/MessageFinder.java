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

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
		public static ResultSet findAll() throws SQLException {
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
		public static ResultSet find(long mid) throws SQLException {
			PreparedStatement ps = Database.getInstance().getStatement(SELECT);
			ps.setLong(1, mid);
			ResultSet rs = ps.executeQuery();
			return rs;
		}
}
