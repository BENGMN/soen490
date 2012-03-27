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

/**
 * A CorruptStreamException should be thrown when trying to read from a 
 * DataInputStream and values being read aren't the ones that are expected.
 * @author Soto
 *
 */
public class CorruptStreamException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CorruptStreamException() {
		super("The stream is corrupt. Values and types are not in the correct order.");
	}
	
	public CorruptStreamException(String message) {
		super(message);
	}
}
