/**
 * SOEN 490
 * Capstone 2011
 * Exception, passed in whenever we have invalid parameter settings.
 * Delegates to FrontCommands.
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

package REFACTORED.application;

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
}
