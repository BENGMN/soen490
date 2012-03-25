package application.strategy;

import java.util.List;

import domain.message.Message;

/**
 * Parent class for the implementation of the Strategy pattern for retrieving messages.
 */
public abstract class RetrievalStrategy {
	public abstract List<Message> retrieve();
}
