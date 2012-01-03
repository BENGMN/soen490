/**
 * SOEN 490
 * Capstone 2011
 * Rate message command; allows a user to thumbs up/thumbs down a command.
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

import domain.message.Message;
import domain.message.MessageInputMapper;
import domain.message.MessageOutputMapper;

public class DownvoteMessageCommand extends RegionalCommand
{

	public boolean execute(HttpServletRequest request, HttpServletResponse response)
	{
		if (!super.execute(request, response))
			return false;
		try
		{
			long mid = Long.parseLong(request.getParameter("mid"));
			Message message = MessageInputMapper.find(mid);
			synchronized(message) {
				message.setUserRating(message.getUserRating()-1);
			}
			MessageOutputMapper.update(message);
			response.setStatus(HttpServletResponse.SC_ACCEPTED);
			return true;
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
		return false;
	}
}
