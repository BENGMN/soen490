/**
 * SOEN 490
 * Capstone 2011
 * Put message command; allows a user to store a message on the server.
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

public class PutMessageCommand extends FrontCommand
{

	public void execute(HttpServletRequest request, HttpServletResponse response)
	{
		try
		{
			double longitude = Double.parseDouble(request.getParameter("longitude"));
			double latitude = Double.parseDouble(request.getParameter("latitude"));
			float speed = Float.parseFloat(request.getParameter("speed"));
		}
		catch (Exception e1)
		{
			try
			{
				response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error: " + e1);
			}
			catch (IOException e2)
			{
				e1.printStackTrace();
			}
		}
	}

}
