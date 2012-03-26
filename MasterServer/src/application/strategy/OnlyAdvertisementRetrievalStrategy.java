package application.strategy;

import java.math.BigInteger;
import java.sql.SQLException;
import java.util.List;

import domain.message.mappers.MessageInputMapper;

public class OnlyAdvertisementRetrievalStrategy extends RetrievalStrategy {

	public OnlyAdvertisementRetrievalStrategy() {
		super();
	}

	@Override
	public List<BigInteger> retrieve(double longitude, double latitude,	double speed, int limit) throws NumberFormatException, SQLException {
		List<BigInteger> messageids = null;
		double radius = getDefaultRadiusMeters();
		
		messageids = MessageInputMapper.findIdsInProximityOnlyAdvertisersOrderRand(longitude, latitude, radius);
		
		if (messageids.size() < getMinMessages()) 
			messageids = MessageInputMapper.findIdsInProximityOnlyAdvertisersOrderRand(longitude, latitude, getIncreasedRadius(radius, speed));
		
		return messageids;
	}

}
