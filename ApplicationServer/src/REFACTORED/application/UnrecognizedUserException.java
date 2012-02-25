package REFACTORED.application;

public class UnrecognizedUserException extends Exception {
	public UnrecognizedUserException(String message) {
		super(message);
	}

	public UnrecognizedUserException() {
		super("Unable to find user.");
	}
	
	private static final long serialVersionUID = -6387329253758991087L;

}
