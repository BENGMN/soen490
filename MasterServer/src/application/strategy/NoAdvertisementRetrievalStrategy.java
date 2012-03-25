package application.strategy;

import java.util.List;

import domain.message.Message;
import domain.message.mappers.MessageInputMapper;
import foundation.tdg.MessageTDG;
import foundation.tdg.UserTDG;

/**
 * Retrieval strategy that returns non advertisement messages ordered by date.
 *
 */
public class NoAdvertisementRetrievalStrategy extends RetrievalStrategy {

	@Override
	public List<Message> retrieve() {
		List<Message> messages = null;
		
		return messages;
	}
	

}
