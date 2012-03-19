package exceptions;

public class LostUpdateException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public LostUpdateException() {
		super("A lost update has occurred.");
	}
	
	public LostUpdateException(String message) {
		super(message);
	}
}
