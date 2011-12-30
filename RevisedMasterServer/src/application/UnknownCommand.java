/**
 * SOEN 490
 * Capstone 2011
 * Unknown Command; basically handles what happens when there is no available command.
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

package application;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UnknownCommand extends FrontCommand
{
	public boolean execute(HttpServletRequest request, HttpServletResponse response)
	{
		try
		{
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Unknown Command");
			return true;
		}
		catch (IOException e)
		{
			return false;
		}
	}
}
