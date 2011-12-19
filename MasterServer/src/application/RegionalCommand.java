package application;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import technical.ServerConfiguration;

/**
 * Class designed to authenticate commands based on what kind of data they're asking for.
 * If the command is asking for data not within the purview of this server, the server
 * will spit out a redirect, telling the user which server it should ask.
 * @author Moving Target
 *
 */
public abstract class RegionalCommand extends FrontCommand {
	public boolean execute(HttpServletRequest request, HttpServletResponse response)
	{
		double longitude = Double.parseDouble(request.getParameter("longitude"));
		double latitude = Double.parseDouble(request.getParameter("latitude"));
		if (!ServerConfiguration.getLocalConfiguration().isResponsible(longitude, latitude)) {
			// Should redirect to the proper hostname, based on information in our server configuration.
			try {
				response.sendRedirect(null);
			} catch (IOException e) {
				e.printStackTrace();
			}
			return false;
		}
		return true;
	}
}
