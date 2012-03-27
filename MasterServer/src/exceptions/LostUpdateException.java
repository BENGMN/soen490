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
