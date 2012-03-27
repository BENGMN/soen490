/**
 * SOEN 490
 * Capstone 2011
 * Exception, passed in whenever we have invalid parameter settings
 * Team members: 	Sotirios Delimanolis
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

public class ParameterException extends Exception {
	
	private static final long serialVersionUID = -1553684524957058659L;
	
	public ParameterException()	{
		super();
	}
	
	public ParameterException(Exception e) { 
		super(e);
	}
	
	public ParameterException(String message) {
		super(message);
	}

	public ParameterException(String message, Exception e) {
		super(message, e);
	}
}
