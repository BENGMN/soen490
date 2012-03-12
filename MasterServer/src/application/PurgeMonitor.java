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

package application;

import java.io.IOException;
import java.math.BigInteger;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import domain.message.mappers.MessageInputMapper;
import domain.message.mappers.MessageOutputMapper;

public class PurgeMonitor {
	
	private final int timeToLive = 7; // In days. Temporary. Will be taken from singleton class
	private final int initialDelay;// in seconds
	private final int delay;// in seconds
	private final ScheduledExecutorService  scheduler = Executors.newSingleThreadScheduledExecutor(); // Only 1 thread is used to perform this task

	public PurgeMonitor(int initialDelay, int delay) {
		this.initialDelay = initialDelay;
		this.delay = delay;
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
				} catch (IOException e) {
					e.printStackTrace();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			};		
		}, initialDelay, delay, TimeUnit.SECONDS);
	}
	
	public void deactivate() {
		scheduler.shutdown();
	}
	
	/**
	 * Gets the list of expired messages and deletes htem.
	 * @throws IOException
	 * @throws SQLException
	 */
	private void deleteExpiredMessages() throws IOException, SQLException {		
		List<BigInteger> messageIds = MessageInputMapper.findExpiredMessages(timeToLive);		
		for(BigInteger mid: messageIds) {
			MessageOutputMapper.delete(mid);
		}	
	}	
}