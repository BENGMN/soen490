/**
 * SOEN 490
 * Capstone 2011
 * Get Messages Command; gets messages for a particular user.
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
import java.io.ObjectOutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import domain.Message;
import domain.MessageMapper;

public class GetMessagesCommand extends FrontCommand
{
	public void execute(HttpServletRequest request, HttpServletResponse response)
	{
		double longitude = Double.parseDouble(request.getParameter("longitude"));
		double latitude = Double.parseDouble(request.getParameter("latitude"));
		double radius = 10.0;
		Message messages[] = MessageMapper.findInProximity(longitude, latitude, radius);
		try
		{
			ObjectOutputStream responseStream = new ObjectOutputStream(response.getOutputStream());
			responseStream.write(messages.length);
			for (int i = 0; i < messages.length; ++i)
				messages[i].writeObject(responseStream);
		}
		catch (Exception e1)
		{
			try
			{
				response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error: " + e1);
			}
			catch (IOException e2)
			{
				e2.printStackTrace();
			}
		}
	}
}
