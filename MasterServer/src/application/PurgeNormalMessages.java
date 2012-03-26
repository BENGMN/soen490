package application;

import java.math.BigInteger;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import domain.message.mappers.MessageInputMapper;
import domain.message.mappers.MessageOutputMapper;

public class PurgeNormalMessages {
	
	public static void deleteMessages(double latitude, double longitude, double radius) throws SQLException {
		List<BigInteger> messages = new LinkedList<BigInteger>(); // MessageInputMapper.findMessagesToDelete(latitude, longitude, radius);
		
		for(BigInteger mid: messages) {
			MessageOutputMapper.delete(mid);
		}
	}

}
