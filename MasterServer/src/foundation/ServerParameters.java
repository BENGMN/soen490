package foundation;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


/**
 * Singleton class that holds all the server parameters.
 * @author Anthony
 *
 */
public class ServerParameters {

	private static ServerParameters uniqueInstance = null;

	public final static String TABLE = "ServerConfiguration";

	/**
	 * The speed (in Kms) threshold of users' GPS information. Below it, check within default radius. Above it, check larger radius.
	 */
	private double speedThreshold;

	/**
	 * The default number of meters to check for message.
	 */
	private double defaultMessageRadius;

	/**
	 * Time (in days) after which messages should expire and be deleted.
	 */
	private double messageLife;

	/**
	 * Time (in days) after which advertiser messages should expire and be deleted.
	 */
	private double advMessageLife;

	/**
	 * Maximum size (in bytes) of audio files accepted for upload.
	 */
	private double maxMessageSize;

	/**
	 * Minimum size (in bytes) of audio files accepted for upload.
	 */
	private double minMessageSize;

	/**
	 * Maximum length (in characters) that a user's email can be.
	 */
	private double maxEmailLength;

	/**
	 * Minimum length (in characters) that a user's email can be.
	 */
	private double minEmailLength;

	/**
	 * Maximum length (in characters) that a user's password can be.
	 */
	private double maxPasswordLength;

	/**
	 * Minimum length (in characters) that a user's password can be.
	 */
	private double minPasswordLength;

	private ServerParameters() throws SQLException {
			populateVariables();
	}

	private final static String SELECT = 
			"SELECT * " + 
			"FROM " + TABLE + ";";

	public void populateVariables() throws SQLException {
		Connection connection = Database.getConnection();
		PreparedStatement ps = connection.prepareStatement(SELECT);
		
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			String var = rs.getString("variable");
			double val = rs.getDouble("value");
			
			var = var.substring(0, 1).toUpperCase() + var.substring(1);
			
			Method meth = null;
			try {
				meth = this.getClass().getMethod("set" + var, Double.class);
			} catch (SecurityException e1) {
				e1.printStackTrace();
			} catch (NoSuchMethodException e1) {
				e1.printStackTrace();
			}
			try {
				meth.invoke(this, val);
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
		}
		ps.close();
	}

	public static ServerParameters getUniqueInstance() throws SQLException {
		if (uniqueInstance == null)
			uniqueInstance = new ServerParameters();
		return uniqueInstance;
	}

	/*
	 * Getters and Setters
	 */
	public double getSpeedThreshold() {
		return speedThreshold;
	}

	public void setSpeedThreshold(double speedThreshold) {
		this.speedThreshold = speedThreshold;
	}

	public double getDefaultRadiusMeters() {
		return defaultMessageRadius;
	}

	public void setDefaultRadiusMeters(double defaultRadiusMeters) {
		this.defaultMessageRadius = defaultRadiusMeters;
	}

	public double getMessageExpirationTime() {
		return messageLife;
	}

	public void setMessageExpirationTime(double messageExpirationTime) {
		this.messageLife = messageExpirationTime;
	}

	public double getMaximumUploadSize() {
		return maxMessageSize;
	}

	public void setMaximumUploadSize(double maximumUploadSize) {
		this.maxMessageSize = maximumUploadSize;
	}


	/**
	 * @return the minMessageSize
	 */
	public double getMinMessageSize() {
		return minMessageSize;
	}


	/**
	 * @param minMessageSize the minMessageSize to set
	 */
	public void setMinMessageSize(double minMessageSize) {
		this.minMessageSize = minMessageSize;
	}


	/**
	 * @return the advMessageLife
	 */
	public double getAdvMessageLife() {
		return advMessageLife;
	}


	/**
	 * @param advMessageLife the advMessageLife to set
	 */
	public void setAdvMessageLife(double advMessageLife) {
		this.advMessageLife = advMessageLife;
	}


	/**
	 * @return the minEmailLength
	 */
	public double getMinEmailLength() {
		return minEmailLength;
	}


	/**
	 * @param minEmailLength the minEmailLength to set
	 */
	public void setMinEmailLength(double minEmailLength) {
		this.minEmailLength = minEmailLength;
	}


	/**
	 * @return the maxEmailLength
	 */
	public double getMaxEmailLength() {
		return maxEmailLength;
	}


	/**
	 * @param maxEmailLength the maxEmailLength to set
	 */
	public void setMaxEmailLength(double maxEmailLength) {
		this.maxEmailLength = maxEmailLength;
	}


	/**
	 * @return the minPasswordLength
	 */
	public double getMinPasswordLength() {
		return minPasswordLength;
	}


	/**
	 * @param minPasswordLength the minPasswordLength to set
	 */
	public void setMinPasswordLength(double minPasswordLength) {
		this.minPasswordLength = minPasswordLength;
	}


	/**
	 * @return the maxPasswordLength
	 */
	public double getMaxPasswordLength() {
		return maxPasswordLength;
	}


	/**
	 * @param maxPasswordLength the maxPasswordLength to set
	 */
	public void setMaxPasswordLength(double maxPasswordLength) {
		this.maxPasswordLength = maxPasswordLength;
	}

}