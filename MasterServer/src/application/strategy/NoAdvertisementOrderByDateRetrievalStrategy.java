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

import domain.message.mappers.MessageInputMapper;

public class NoAdvertisementOrderByDateRetrievalStrategy extends RetrievalStrategy {

	public NoAdvertisementOrderByDateRetrievalStrategy() throws SQLException {
		super();
	}
	
	@Override
	public List<BigInteger> retrieve(double longitude, double latitude,	double speed, int limit) throws NumberFormatException, SQLException {
		List<BigInteger> messageids = null;
		int radius = 0;
		messageids = MessageInputMapper.findIdsInProximityNoAdvertisersOrderDate(longitude, latitude, radius, limit);
		
		if (messageids.size() < getMinMessages()) 
			messageids = MessageInputMapper.findIdsInProximityNoAdvertisersOrderDate(longitude, latitude, getIncreasedRadius(radius, speed), limit);
		
		return messageids;
	}

}
