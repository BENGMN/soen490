/**
 * SOEN 490
 * Capstone 2011
 * Team members: 	
 * 			Sotirios Delimanolis
 * 			Filipe Martinho
 * 			Adam Harrison
 * 			Vahe Chahinian
 * 			Ben Crudo
 * 			Anthony Boyer
 * 
 * @author Capstone 490 Team Moving Target
 *
 */
package application.commands;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import application.ServerParameters;

import exceptions.MapperException;
import exceptions.ParameterException;
import exceptions.UnrecognizedUserException;

/**
 * Command for retrieving server configuration parameters and forwarding them to a jsp view.
 *
 */
public class GetServerParametersCommand extends FrontCommand {

	private static String SERVER_PARAMETERS_JSP = "/WEB-INF/jsp/ServerConfigurationUtility.jsp";

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws MapperException, ParameterException, IOException, UnrecognizedUserException, SQLException, ServletException {		
		
		// Get the singleton 
		ServerParameters params = ServerParameters.getUniqueInstance();	
		
		// Give it to the request object to be unfolded in the jsp
		request.setAttribute("serverParameters", params);

		response.setStatus(HttpServletResponse.SC_OK);
		
		// Get and forward to the jsp pages
		RequestDispatcher view = request.getRequestDispatcher(SERVER_PARAMETERS_JSP);
		view.forward(request, response);
		
	}

}
