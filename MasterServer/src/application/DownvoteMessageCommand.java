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
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import domain.message.Message;
import domain.message.MessageInputMapper;
import domain.message.MessageOutputMapper;

public class DownvoteMessageCommand extends RegionalCommand
{

	public void execute(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ParameterException
	{
		if (request.getParameter("mid") == null)
			throw new ParameterException("Must pass in the mid to downvote.");
		long mid = Long.parseLong(request.getParameter("mid"));
		Message message = MessageInputMapper.find(mid);
		synchronized(message) {
			message.setUserRating(message.getUserRating()-1);
		}
		MessageOutputMapper.update(message);
		response.setStatus(HttpServletResponse.SC_ACCEPTED);
	}
}
