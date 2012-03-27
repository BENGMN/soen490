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
