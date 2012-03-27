/**
 * SOEN 490
 * Capstone 2011
 * Team members: 	
 * 			Sotirios Delimanolis
 * 			Filipe Martinho
 * 			Adam Harrison
 * 			Vahe Chahinian
 * 			Ben Crudo
 * 			Anthony Boyer
 * 
 * @author Capstone 490 Team Moving Target
 *
 */
package exceptions;

public class UnrecognizedUserException extends Exception {
	public UnrecognizedUserException(String message) {
		super(message);
	}

	public UnrecognizedUserException() {
		super("Unable to find user.");
	}
	
	private static final long serialVersionUID = -6387329253758991087L;

}
