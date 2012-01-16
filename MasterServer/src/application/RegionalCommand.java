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
	public boolean responsible(HttpServletRequest request, HttpServletResponse response) throws IOException, ParameterException 
	{
		if (request.getParameter("longitude") == null || request.getParameter("latitude") == null)
			throw new ParameterException("Both longitude and latitude must be passed in.");
		double longitude = Double.parseDouble(request.getParameter("longitude"));
		double latitude = Double.parseDouble(request.getParameter("latitude"));
		if (longitude < -180.0 || longitude > 180.0 || latitude < -90.0 || latitude > 90.0)
			throw new ParameterException("Longitude must be within -180 to 180 degrees, and latitude must be within -90 to 90 degrees.");
		if (!ServerConfiguration.getLocalConfiguration().isResponsible(longitude, latitude)) {
			// Should redirect to the proper hostname, based on information in our server configuration.
			response.sendRedirect(null);
			return false;
		}
		return true;
	}
}
