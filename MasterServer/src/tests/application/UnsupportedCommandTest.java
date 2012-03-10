/**
 * SOEN 490
 * Capstone 2011
 * Test for UnsupportedCommand.
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

import javax.servlet.http.HttpServletResponse;

import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import exceptions.ParameterException;

import application.commands.UnsupportedCommand;
public class UnsupportedCommandTest {

	@Test
	public void testUnsupportedCommand() throws IOException, SQLException
	{
		MockHttpServletRequest request = new MockHttpServletRequest();
		MockHttpServletResponse response = new MockHttpServletResponse();
		UnsupportedCommand command = new UnsupportedCommand("GET", "Unsupported command");
		try {
			command.execute(request, response);
			assertEquals(HttpServletResponse.SC_NOT_IMPLEMENTED, response.getStatus());
		}
		catch (ParameterException e) {
			
		}
	}
}