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

import static org.junit.Assert.*;

import javax.servlet.http.HttpServletResponse;

import org.junit.Test;

import application.UnknownCommand;

public class UnknownCommandTest {

	@Test
	public void testUnknownCommand()
	{
		MockHttpServletRequest request = new MockHttpServletRequest();
		MockHttpServletResponse response = new MockHttpServletResponse();
		UnknownCommand command = new UnknownCommand();
		command.execute(request, response);
		assertEquals(HttpServletResponse.SC_BAD_REQUEST, response.getStatus());
	}
}
