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
 * This class is designed to run a function that will delete messages from the database periodically.
 *
 */

package application;

import java.math.BigInteger;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.Logger;

import domain.message.mappers.MessageInputMapper;
import domain.message.mappers.MessageOutputMapper;

public class PurgeMonitor {
	
	private int initialDelay = 300;		// in seconds
	private int delay = 600;			// in seconds
	private int DAYS_OF_GRACE = 7;
	private Logger logger;
	
	// Only 1 thread is used to perform this task
	private final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor(); 

	public PurgeMonitor() {
		logger = (Logger) LoggerFactory.getLogger("application");
		try {
			ServerParameters params = ServerParameters.getUniqueInstance();
			this.initialDelay = Integer.parseInt(params.get("purgeMonitorInitialDelay").getValue());
			this.delay = Integer.parseInt(params.get("purgeMonitorInitialDelay").getValue());
		} catch (SQLException e) {
			logger.debug("SQLException occured when trying to retrieve Server Parameters 'initialDelay' and 'delay'. Exception: {}", e);
		} catch (NumberFormatException e) {
			logger.debug("Could not parse the 'initialDelay' or 'delay' server parameters. Threw NumberFormatException. Using class default values '{}' and '{}', respectively.", initialDelay, delay);
		}
	}
	
	// Mostly to test, the other constructor should be called for the application.
	public PurgeMonitor(int initialDelay, int delay) {
		this.initialDelay = initialDelay;
		this.delay = delay;
		logger = (Logger) LoggerFactory.getLogger("application");
	}
	
	/**
	 * Calls the function to delete the messages at a fixed interval
	 * @return boolean representing the state of the execution
	 */
	public void execute() {
		scheduler.scheduleWithFixedDelay(new Runnable() {			
			public void run() {
				try {
					deleteExpiredMessages();
					ServerParameters params = ServerParameters.getUniqueInstance();
					delay = Integer.parseInt(params.get("purgeMonitorInitialDelay").getValue());
				} catch (SQLException e) {
					logger.error("An SQLException occured when trying to delete old messages: {}", e);
				}
			};		
		}, initialDelay, delay, TimeUnit.SECONDS);
	}
	
	public void deactivate() {
		scheduler.shutdown();
	}
	
	/**
	 * Gets the list of expired messages and deletes htem.
	 * @throws SQLException
	 */
	private void deleteExpiredMessages() throws SQLException {		
		int daysOfGrace;
		try {
			daysOfGrace = Integer.parseInt(ServerParameters.getUniqueInstance().get("daysOfGrace").getValue());
		} catch (NumberFormatException e) {
			logger.debug("Could not parse the 'daysOfGrace' server parameter. Threw NumberFormatException. Using class default value '{}'.", DAYS_OF_GRACE);
			daysOfGrace = DAYS_OF_GRACE;
		}
		
		List<BigInteger> messageIds = MessageInputMapper.findByTimeAndRatingToDestroy(daysOfGrace); 
		
		// Change which function you call here to change the deletion strategy.
		for(BigInteger mid: messageIds) {
			MessageOutputMapper.delete(mid);
		}
	}	
}