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

package tests.application;

import static org.junit.Assert.*;

import java.io.IOException;
import java.sql.SQLException;

import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import application.ParameterException;
import application.UnknownCommand;

public class UnknownCommandTest {

	@Test
	public void testUnknownCommand() throws IOException, SQLException
	{
		MockHttpServletRequest request = new MockHttpServletRequest();
		MockHttpServletResponse response = new MockHttpServletResponse();
		UnknownCommand command = new UnknownCommand();
		try {
			command.execute(request, response);
			fail("Exception should've been thrown.");
		}
		catch (ParameterException e) {
			
		}
	}
}