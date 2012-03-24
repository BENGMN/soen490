package exceptions;

public class PropertyNotFoundException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1883300660633411993L;

	public PropertyNotFoundException() {
		super("A Property was not found.");
	}
	
	public PropertyNotFoundException (String message) {
		super(message);
	}
}
