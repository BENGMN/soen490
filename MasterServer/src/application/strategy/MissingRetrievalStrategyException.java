package application.strategy;

public class MissingRetrievalStrategyException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 605435069105186400L;

	public MissingRetrievalStrategyException() {
		super("No Retrieval Strategy was found for the passed parameters.");
	}
	
	public MissingRetrievalStrategyException(String message) {
		super(message);
	}
}
