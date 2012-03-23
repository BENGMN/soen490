package Client;


public class MapperException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MapperException() {
		super("Mapping exception");
	}
	
	public MapperException(String message) {
		super(message);
	}
}
