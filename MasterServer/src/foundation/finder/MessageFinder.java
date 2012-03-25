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

package foundation.finder;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

import application.ServerParameters;

import foundation.DbRegistry;
import foundation.tdg.MessageTDG;
import foundation.tdg.UserTDG;


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
			"m.user_rating " +
			"FROM " + MessageTDG.TABLE + " AS m";

	/**
	 * Finds all messages in the Message table
	 * @return Returns the ResultSet containing the message information as 
	 * m.mid, m.uid, m.message, m.speed, m.latitude, m.longitude, m.created_at, m.user_rating, m.version
	 * @throws SQLException
	 */
	public static ResultSet findAll() throws SQLException {
		Connection connection = DbRegistry.getDbConnection();
		PreparedStatement ps = connection.prepareStatement(SELECT_ALL);
		
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
		"m.user_rating " +
		"FROM " + MessageTDG.TABLE + " AS m " +
		"WHERE m.mid = ?;";

	/** 
	 * Finds a message with the passed message id
	 * @param mid Message id
	 * @return Returns the ResultSet containing the message information as
	 * m.mid, m.uid, m.message, m.speed, m.latitude, m.longitude, m.created_at, m.user_rating, m.version
	 * @throws SQLException
	 */
	public static ResultSet find(BigInteger mid) throws SQLException {
		Connection connection = DbRegistry.getDbConnection();
		PreparedStatement ps = connection.prepareStatement(SELECT);
		
		ps.setBigDecimal(1, new BigDecimal(mid.toString()));
		ResultSet rs = ps.executeQuery();
		return rs;
	}

	private static final String SELECT_BY_EMAIL =
			"SELECT * FROM " + MessageTDG.TABLE + " AS m WHERE m.uid = ?;";

	public static ResultSet findByUser(BigInteger uid) throws SQLException {
		Connection connection = DbRegistry.getDbConnection();
		PreparedStatement ps = connection.prepareStatement(SELECT_BY_EMAIL);
		
		ps.setBigDecimal(1, new BigDecimal(uid));
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

	public static ResultSet findInProximity(double longitude, double latitude, double radius) throws SQLException {
		Connection connection = DbRegistry.getDbConnection();
		PreparedStatement ps = connection.prepareStatement(SELECT_BY_RADIUS);
		
		List<Coordinate> rectangle = GeoSpatialSearch.convertPointToRectangle(new Coordinate(longitude, latitude), radius);
		ps.setDouble(1, rectangle.get(0).getLongitude());
		ps.setDouble(2, rectangle.get(1).getLongitude());
		ps.setDouble(3, rectangle.get(0).getLatitude());
		ps.setDouble(4, rectangle.get(1).getLatitude());
		ResultSet rs = ps.executeQuery();
		return rs;
	}

	private static final String SELECT_ID_BY_RADIUS = 
			"SELECT m.mid "+
			"FROM " + MessageTDG.TABLE + " AS m " +
			"WHERE m.longitude BETWEEN ? AND ? AND m.latitude BETWEEN ? AND ?;";
	
	private static final String ORDER_BY_USER_TYPE = " ORDER BY u.type DESC;";
	private static final String ORDER_BY_USER_RATING = " ORDER BY messages.user_rating DESC;";
	private static final String ORDER_BY_CREATED_TYPE = " ORDER BY messages.created_at DESC;";
	
	private static String GET_SIZE = 
			"SELECT COUNT(m.mid) AS size " +
			"FROM " + MessageTDG.TABLE + " AS m " + "WHERE m.longitude BETWEEN ? AND ? AND m.latitude BETWEEN ? AND ?;";

	/**
	 * 
	 * @param longitude
	 * @param latitude
	 * @param speed
	 * @return All ids of messages within a bounding rectangle that has a half cross-section of radius.
	 * @throws SQLException
	 */		
	public static ResultSet findIdsInProximity(double longitude, double latitude, double speed, String orderBy) throws SQLException, IOException {
		
		Connection connection = DbRegistry.getDbConnection();
		ServerParameters params = ServerParameters.getUniqueInstance();
		int minMessages = Integer.parseInt(params.get("minMessages").getValue());
		int maxMessages = Integer.parseInt(params.get("maxMessages").getValue());

		String query = "";
		
		if(orderBy.equals("user_rating"))
			query = SELECT_ID_BY_RADIUS+ORDER_BY_USER_RATING;
		else if(orderBy.equals("type"))
			query = SELECT_ID_BY_RADIUS+ORDER_BY_USER_TYPE;
		else if(orderBy.equals("created_at"))
			query = SELECT_ID_BY_RADIUS+ORDER_BY_CREATED_TYPE;
			
		double radius = 0;
		double multiplier = 0;
		double radiusAdder = 500;
		double maxRadius = 0; //TODO Use this in your loop.
		
		//If the gps doesn't return a speed the default speed is 30
		if(speed == 0)
			speed = 30;
		//The speed is in km 
		//over 60km/h is highway driving 8 minutes to reach the farthest of the messages
		//up to 16 minutes if you don't have at least 10 messages
		if(speed > 60)
		{	
			multiplier = 134;
			radius = multiplier*speed;
			maxRadius = 2*multiplier*speed;
		}//over 30km/h driving is city driving 10 minutes to reach the farthest of the messages Up to 20 minutes if you don't have at least 10 messages
		else if(speed>30 && speed<=60)
		{
			multiplier = 167;
			radius = multiplier*speed;
			maxRadius = 2*multiplier*speed;
		}//this is for biking speed 7 minutes to reach the farthest of the messages up to 14 minutes if you don't have at least 10 messages
		else if(speed>9 && speed <=30)
		{
			multiplier = 11;
			radius = multiplier * speed;
			maxRadius = 2*multiplier*speed;
		}//this is walking speed 5 minutes to reach the farthest of the messages up to 10 minutes if you don't have at least 10 messages
		else if(speed<=9)
		{
			multiplier = 83.3;
			radius = multiplier * speed;
			maxRadius = 2*multiplier*speed;
		}
		//Next block of code is to enlarge the radius until you either find 10 messages or you reach the maximum radius allowed for that speed
		ResultSet rsSize;
		//flag for not incrementing the radius on the first run
		boolean flag = false;

		int size = 0;
		do {		
			PreparedStatement psSize = connection.prepareStatement(GET_SIZE);
			
			List<Coordinate> rectangle = GeoSpatialSearch.convertPointToRectangle(new Coordinate(longitude, latitude), radius);
			psSize.setDouble(1, rectangle.get(0).getLongitude());
			psSize.setDouble(2, rectangle.get(1).getLongitude());
			psSize.setDouble(3, rectangle.get(0).getLatitude());
			psSize.setDouble(4, rectangle.get(1).getLatitude());
			rsSize = psSize.executeQuery();
			rsSize.next();
		
			if(flag)
				radius += radiusAdder;
			else
				flag = true;
			
			size = rsSize.getInt("size");

		}
		while(size <= minMessages && radius <= maxRadius);
		
		//Getting the actual ids at this point
		ResultSet finaleRs;

		PreparedStatement finalPs = connection.prepareStatement(query);
		List<Coordinate> rectangle = GeoSpatialSearch.convertPointToRectangle(new Coordinate(longitude, latitude), radius);
		finalPs.setDouble(1, rectangle.get(0).getLongitude());
		finalPs.setDouble(2, rectangle.get(1).getLongitude());
		finalPs.setDouble(3, rectangle.get(0).getLatitude());
		finalPs.setDouble(4, rectangle.get(1).getLatitude());

		finaleRs = finalPs.executeQuery();

		return finaleRs;
	}
	
	
	/**
	 * 
	 * DAYS_Of_GRACE is equal to the minimum time that a message has to live before it begins to be considered for deletion.
	 * DAYS_Of_GRACE is equal to the number of days of grace in UNIX time units.
	 * 
	 * 
	 * 	SELECT m.mid
	 *	FROM (
	 *			SELECT msg.mid, 
	 *	 			UNIX_TIMESTAMP(DATE(msg.created_at)) AS created_at,
	 *	 			DATE(msg.created_at) AS created_at_date,
	 *			FROM 	application_Message as msg
	 *			WHERE DATE(msg.created_at) < (CURRENT_DATE()- DAYS_OF_GRACE)
     *		) AS m
	 *	WHERE POW(2, ((UNIX_TIMESTAMP(CURRENT_DATE) - (m.created_at + DAYS_OF_GRACE_UNIX)) / 86400)) > m.user_rating;
	 *
	 */
	private static final int ONE_UNIX_DAY = 60*60*24;
	
	private static final String SELECT_MIDS_WHOSE_RATING_IS_LESS_THAN_2_POW_DAYS_OLD_PLUS_DOG = 
			"SELECT m.mid " +
			"FROM (SELECT msg.mid, " +
			             "UNIX_TIMESTAMP(DATE(msg.created_at)) AS created_at, " +
			             "msg.user_rating " +
			      "FROM "+MessageTDG.TABLE+ " AS msg " +
			      "WHERE DATE(msg.created_at) < (CURRENT_DATE()-?)" +
			     ") AS m " +
			"WHERE POW(2, ((UNIX_TIMESTAMP(CURRENT_DATE) - (m.created_at + ?)) / "+ONE_UNIX_DAY+")) > m.user_rating;";
	
	public static ResultSet findByTimeAndRatingToPurge() throws SQLException {
		int DAYS_OF_GRACE = Integer.parseInt(ServerParameters.getUniqueInstance().get("dogsBeforeDelete").getValue());
		int DAYS_OF_GRACE_UNIX = ONE_UNIX_DAY*DAYS_OF_GRACE;
		Connection connection = DbRegistry.getDbConnection();
		PreparedStatement ps = connection.prepareStatement(SELECT_MIDS_WHOSE_RATING_IS_LESS_THAN_2_POW_DAYS_OLD_PLUS_DOG);
		ps.setInt(1, DAYS_OF_GRACE);
		ps.setInt(2, DAYS_OF_GRACE_UNIX);
		ResultSet rs = ps.executeQuery();
		return rs;
	}

	
	/*** Get the number of all messages in the minimum sized square that are from Regular Users ***/
	private static final String COUNT_NORMAL_MESSAGES_PER_SQUARE =
			"SELECT COUNT(m.mid) AS messageCount " +
			"FROM "+MessageTDG.TABLE+" AS m, "+UserTDG.TABLE+" AS u " +
			"WHERE m.longitude BETWEEN ? AND ? " +
				"AND m.latitude BETWEEN ? AND ? AND " +
				"u.uid = m.uid AND u.type = 0 " +
			"ORDER BY m.user_rating DESC, m.created_at DESC;";	
						
	
	/*** Get a list of all the messages that should be deleted ***/
	private static final String SELECT_INVALID_MESSAGES = 
			"SELECT m.mid "+
			"FROM "+MessageTDG.TABLE+" as m,"+ UserTDG.TABLE+" as u "+
			"WHERE m.longitude BETWEEN ? AND ? AND "+ 
				  "m.latitude BETWEEN ? AND ? AND "+
				  "u.uid = m.uid AND "+
				  "u.type = 0 "+
			"ORDER BY m.user_rating DESC,"+ 
					 "m.created_at DESC "+
			"LIMIT 41, ?;";
	
	public static ResultSet findByMaxDensityToPurge(double latitude, double longitude, double radius) throws SQLException {
		// Get all the points in the database close to the coordinates supplied
		Coordinate coordinate = new Coordinate(latitude, longitude);
		List<Coordinate> rectangle = GeoSpatialSearch.convertPointToRectangle(coordinate, radius);
		Connection connection = DbRegistry.getDbConnection();
		PreparedStatement ps = connection.prepareStatement(COUNT_NORMAL_MESSAGES_PER_SQUARE);
		ps.setDouble(1, rectangle.get(0).getLongitude());
		ps.setDouble(2, rectangle.get(1).getLongitude());
		ps.setDouble(3, rectangle.get(0).getLatitude());
		ps.setDouble(4, rectangle.get(1).getLatitude());
		ResultSet rs = ps.executeQuery();
		
		int msgCount = 0;
		
		while(rs.next()) {
			msgCount = rs.getInt("messageCount");
		}
		ResultSet considerForPurge = null;
		if (msgCount > 40) {
			ps = connection.prepareStatement(SELECT_INVALID_MESSAGES);
			ps.setDouble(1, rectangle.get(0).getLongitude());
			ps.setDouble(2, rectangle.get(1).getLongitude());
			ps.setDouble(3, rectangle.get(0).getLatitude());
			ps.setDouble(4, rectangle.get(1).getLatitude());
			ps.setInt(5, msgCount);
			considerForPurge = ps.executeQuery();
		}
		
		return considerForPurge;
	}
	
	
	private static final String SELECT_BY_DATE = 
			"SELECT m.mid" +  
			"From" + MessageTDG.TABLE + "As m " +
		    "Where DATE_ADD(created_at, INTERVAL ? DAY) >= ?";
	
	/**
	 * Finds messages that are expired based on their date and time to live
	 * @param timeToLive
	 * @return Returns the resultset containing the message Ids to be deleted
	 * @throws SQLException
	 * @throws IOException
	 */
	public static ResultSet findOlderMessages(int timeToLive) throws SQLException {
		Connection connection = DbRegistry.getDbConnection();
		PreparedStatement ps = connection.prepareStatement(SELECT_BY_DATE);	
		
		ps.setInt(1, timeToLive);
		ps.setTimestamp(2, new Timestamp(System.currentTimeMillis()));
		ResultSet rs = ps.executeQuery();
		return rs;
	}
		
	
	public static ResultSet findIdsInProximity(double longitude, double latitude, double speed, int radius) throws SQLException {
		Connection connection = DbRegistry.getDbConnection();
		PreparedStatement ps = connection.prepareStatement(FIND_PROXIMITY);
		
		GeoSpatialSearch.convertPointToRectangle(new Coordinate(latitude, longitude), radius);
		
		ps.setDouble(1, longitude);
		
		return null;
	}
	
	private static final String FIND_PROXIMITY = "SELECT m.mid, m.uid, m.user_rating, m.created_at FROM " + MessageTDG.TABLE + " AS m " +
			   "WHERE m.longitude BETWEEN ? AND ? AND m.latitude BETWEEN ? AND ?";
	
	private static final String FIND_PROXIMITY_JOIN_USER = "SELECT m.mid FROM " + UserTDG.TABLE + " AS u, (" +
				FIND_PROXIMITY + ") AS m ";
	
	private static final String FIND_PROXIMITY_NO_ADV_ORDER_DATE = FIND_PROXIMITY_JOIN_USER + "WHERE m.uid = u.uid AND u.type != 1 ORDER BY m.created_at DESC;";
	
	private static final String FIND_PROXIMITY_NO_ADV_ORDER_RATING = FIND_PROXIMITY_JOIN_USER + "WHERE m.uid = u.uid AND u.type != 1 ORDER BY m.user_rating DESC;";																

	private static final String FIND_PROXIMITY_ONLY_ADV_ORDER_RAND = FIND_PROXIMITY_JOIN_USER + "WHERE m.uid = u.uid AND u.type = 1 ORDER BY RAND();";
	
	private static final String FIND_PROXIMITY_ORDER_RATING = FIND_PROXIMITY + "ORDER BY m.user_rating DESC;";
	
	private static final String FIND_PROXIMITY_ORDER_DATE = FIND_PROXIMITY + "ORDER BY m.created_at DESC;";
	
	private static final String FIND_PROXIMITY_ORDER_RAND = FIND_PROXIMITY + "ORDER BY RAND();";
	
	private static final String FIND_PROXIMITY_ORDER_RAND_LIMIT = FIND_PROXIMITY + "ORDER BY RAND() LIMIT ?;";
	
	
}
