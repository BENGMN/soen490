/**
 * SOEN 490
 * Capstone 2011
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
package application.strategy;

import java.math.BigInteger;
import java.sql.SQLException;
import java.util.List;

import application.ServerParameters;

/**
 * Parent class for the implementation of the Strategy pattern for retrieving messages.
 * Concrete child classes should have expressive names.
 */
public abstract class RetrievalStrategy {
	protected ServerParameters params = null;
	protected RetrievalStrategy() throws SQLException {
		params = ServerParameters.getUniqueInstance();
	}

	// Strategy method to retrieve message ids
	public abstract List<BigInteger> retrieve(double longitude, double latitude, double speed, int limit) throws NumberFormatException, SQLException;

	// returns the default radius to check for messages in
	protected double getDefaultRadiusMeters() throws NumberFormatException {
		double defaultRadius = Double.parseDouble(params.get("defaultRadiusMeters").getValue());
		return defaultRadius;
	}
	
	// returns the min messages to try and return 
	protected int getMinMessages() throws NumberFormatException {
		int minMessages = Integer.parseInt(params.get("minMessages").getValue());
		return minMessages;
	}
	
	/**
	 * Class to increase the radius in which messages are retrieved. Depends on the current radius and speed of the request. 
	 * @param radius
	 * @param speed
	 * @return
	 * @throws NumberFormatException
	 * @throws SQLException
	 */
	protected double getIncreasedRadius(double radius, double speed) throws NumberFormatException, SQLException {
		double increasedRadius = getDefaultRadiusMeters();
		double multiplier = 0;
		//TODO fix this
		//If the gps doesn't return a speed the default speed is 30
		if(speed == 0)
			speed = 30;
		//The speed is in km 
		//over 60km/h is highway driving 8 minutes to reach the farthest of the messages
		//up to 16 minutes if you don't have at least 10 messages
		if (speed > 60){	
			multiplier = 134;
			increasedRadius = multiplier * speed;
		} // over 30km/h driving is city driving 10 minutes to reach the farthest of the messages Up to 20 minutes if you don't have at least 10 messages
		else if (speed > 30 && speed <= 60) {
			multiplier = 167;
			increasedRadius = multiplier * speed;
		} // this is for biking speed 7 minutes to reach the farthest of the messages up to 14 minutes if you don't have at least 10 messages
		else if (speed > 9 && speed <= 30) {
			multiplier = 116;
			increasedRadius = multiplier * speed;
		} // this is walking speed 5 minutes to reach the farthest of the messages up to 10 minutes if you don't have at least 10 messages
		else if (speed <= 9) {
			multiplier = 83.3;
			increasedRadius = multiplier * speed;
		}
		
		return increasedRadius;
	}
}
