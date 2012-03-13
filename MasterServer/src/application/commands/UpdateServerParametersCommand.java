package application.commands;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.Logger;

import application.ServerParameters;

import exceptions.MapperException;
import exceptions.ParameterException;
import exceptions.UnrecognizedUserException;
import foundation.tdg.ServerParametersTDG;

public class UpdateServerParametersCommand extends FrontCommand {
	private static String SERVER_PARAMETERS_JSP = "/WEB-INF/jsp/ServerConfigurationUtility.jsp";
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response)throws MapperException, ParameterException, UnrecognizedUserException, SQLException, ServletException, IOException {

		Enumeration<String> params = request.getParameterNames();
		while(params.hasMoreElements()) {
			String variableName = params.nextElement();
			if(variableName.equals("command")) 
				continue;
			Double value = null;
			
			try {
				value = Double.parseDouble(request.getParameter(variableName));
			} catch (NumberFormatException e) {
				// Should never come here, javascript should already have checked them
				Logger logger = (Logger)LoggerFactory.getLogger("application");
				logger.error("NumberFormatException occurred when parsing server parameters from jsp. These should be checked in jsp through javascript: {}", e);
			}
			
			// Update in db
			ServerParametersTDG.update(variableName, value);
			// Update in memory, can change this if ping all servers to update directly from db
			ServerParameters.getUniqueInstance().put(variableName, value);
		}
		
		// Add the server parameters back to the request
		request.setAttribute("serverConfiguration", ServerParameters.getUniqueInstance());
		
		// TODO update all other servers
		// Get and forward to the jsp pages
		RequestDispatcher view = request.getRequestDispatcher(SERVER_PARAMETERS_JSP);
		view.forward(request, response);
	}
}
