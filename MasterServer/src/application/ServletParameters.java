package application;
/**
 * SOEN 490
 * Capstone 2011
 * Singleton class that holds all the servlet parameters.
 * Delegates to FrontCommands.
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

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class ServletParameters {

	private static ServletParameters uniqueInstance = null;
	
	/**
	 * The speed (in Kms) threshold of users' GPS information. Below it, check within default radius. Above it, check larger radius.
	 */
	private double speedThreshold;
	
	/**
	 * The default number of meters to check for message.
	 */
	private double defaultRadiusMeters;
	
	/**
	 * Time (in ??) after which messages should expire and be deleted.
	 */
	private int messageExpirationTime;
	
	/**
	 * Maximum size (in bytes) of audio files accepted for upload.
	 */
	private int maximumUploadSize;
	
	private ServletParameters() {
		Properties props = new Properties();
		FileInputStream inStream = null;
		try {
			inStream = new FileInputStream("configuration.properties");
			props.load(inStream);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		speedThreshold = Double.parseDouble(props.getProperty("speedThreshold", "30"));
		defaultRadiusMeters = Double.parseDouble(props.getProperty("defaultRadiusMeters", "500"));
		messageExpirationTime = Integer.parseInt(props.getProperty("messageExpirationTime", "4320"));
		maximumUploadSize = Integer.parseInt(props.getProperty("maximumUploadSize", "50000"));
	}

	
	public static ServletParameters getUniqueInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new ServletParameters();
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
		return defaultRadiusMeters;
	}

	public void setDefaultRadiusMeters(double defaultRadiusMeters) {
		this.defaultRadiusMeters = defaultRadiusMeters;
	}

	public int getMessageExpirationTime() {
		return messageExpirationTime;
	}

	public void setMessageExpirationTime(int messageExpirationTime) {
		this.messageExpirationTime = messageExpirationTime;
	}

	public int getMaximumUploadSize() {
		return maximumUploadSize;
	}

	public void setMaximumUploadSize(int maximumUploadSize) {
		this.maximumUploadSize = maximumUploadSize;
	}
	
}
