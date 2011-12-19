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
import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import technical.UnrecognizedUserException;

import domain.message.Message;
import domain.message.MessageFactory;
import domain.message.MessageOutputMapper;
import domain.user.User;
import domain.user.UserMapper;
import foundation.MessageTDG;

public class PutMessageCommand extends RegionalCommand
{

	public boolean execute(HttpServletRequest request, HttpServletResponse response)
	{
		if (!super.execute(request, response))
			return false;
		double longitude = Double.parseDouble(request.getParameter("longitude"));
		double latitude = Double.parseDouble(request.getParameter("latitude"));
		float speed = Float.parseFloat(request.getParameter("speed"));
		byte[] data = request.getParameter("message").getBytes();
		// They told us to keep the user system light; this is about as light as it gets; we're not authenticating, just passing in the email as a parameter.
		// TODO Will obviously have to change this in future, but will work for now.
		String email = request.getParameter("email");
		User user = UserMapper.findByEmail(email);
		try
		{
			if (user == null)
				throw new UnrecognizedUserException();
			Message message = MessageFactory.createNew(MessageTDG.getUniqueId(), user.getUid(), data, speed, latitude, longitude, Calendar.getInstance(), 0, 0);
			MessageOutputMapper.insert(message);
			response.setStatus(HttpServletResponse.SC_ACCEPTED);
			return true;
		}
		catch (Exception e1)
		{
			try	{
				response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error: " + e1);
			}
			catch (IOException e2) {
				e1.printStackTrace();
			}
			return false;
		}
	}

}
