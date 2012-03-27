package application.strategy;

import java.math.BigInteger;
import java.sql.SQLException;
import java.util.List;

import domain.message.mappers.MessageInputMapper;

public class AllOrderByRatingRetrievalStrategy extends RetrievalStrategy {

	public AllOrderByRatingRetrievalStrategy() throws SQLException {
		super();
	}

	@Override
	public List<BigInteger> retrieve(double longitude, double latitude,	double speed, int limit) throws NumberFormatException, SQLException {
		List<BigInteger> messageids = null;
		int radius = 0;
		messageids = MessageInputMapper.findIdsInProximityOrderRatingLimit(longitude, latitude, radius, limit);
		
		if (messageids.size() < getMinMessages()) 
			messageids = MessageInputMapper.findIdsInProximityOrderRatingLimit(longitude, latitude, getIncreasedRadius(radius, speed), limit);
		
		return messageids;
	}

}
