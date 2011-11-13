/**
 * SOEN 490
 * Capstone 2011
 * Test for UnknownCommand.
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

package tests;

import application.UnknownCommand;
import junit.framework.TestCase;

public class UnknownCommandTest extends TestCase {

	public void testUnknownCommand()
	{
		UnknownCommand command = new UnknownCommand();
		HttpServletRequest request = new HttpServletRequest();
		HttpServletResponse response = new HttpServletResponse();
		command.execute(request, response)
	}
}
