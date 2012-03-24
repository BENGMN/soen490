package application.commands;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.Logger;
import domain.serverparameter.ServerParameter;
import domain.serverparameter.mappers.ServerParameterOutputMapper;

import application.ServerParameters;

import exceptions.MapperException;
import exceptions.ParameterException;
import exceptions.UnrecognizedUserException;

public class UpdateServerParametersCommand extends FrontCommand {
	private static String SERVER_PARAMETERS_JSP = "/WEB-INF/jsp/ServerConfigurationUtility.jsp";
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response)throws MapperException, ParameterException, UnrecognizedUserException, SQLException, ServletException, IOException {
		// Source: where to get server parameter updates
		String source = request.getParameter("source");
		
		// You might want cto update the parameters from some other source
		// That would go here
		if (source == null)
			throw new ParameterException ("Missing 'source' parameter.");	
		
		// 'client' source represents the management console
		else if ("client".equals(source)) {
			// TODO if you were to change the description too, it could be done here
			
			ServerParameters params = ServerParameters.getUniqueInstance();
			
			// Updates the values
			List<String> parameterNames = new ArrayList<String>(10);
			List<ServerParameter> toUpdate = new ArrayList<ServerParameter>();
			
			// HARDCODED list of server parameters
			parameterNames.add("minMessageSizeBytes");
			parameterNames.add("maxMessageSizeBytes");
			parameterNames.add("messageLifeDays");
			parameterNames.add("advertiserMessageLifeDays");
			parameterNames.add("minEmailLength");
			parameterNames.add("maxEmailLength");
			parameterNames.add("minPasswordLength");
			parameterNames.add("maxPasswordLength");
			parameterNames.add("speedThreshold");
			parameterNames.add("defaultMessageRadiusMeters");
			parameterNames.add("minMessages");
			parameterNames.add("maxMessages");
			
			// Go through each of the above
			for (String name: parameterNames) {
				String value = request.getParameter(name);
				
				if (value == null || "".equals(value))
					throw new ParameterException("Missing '" + name + "' parameter value.");
				
				try {
					
					ServerParameter param = params.get(name);
					
					param.setValue(value);
					
					// Try to parse the value given
					Integer.parseInt(value);
					
					toUpdate.add(param);
				} catch (NumberFormatException e) {
					// Some validation should already have been done in the jsp
					// NumberFormatException means the value given either has a decimal value, is not a number value, or is greater than Integer.MAX_VALUE
					Logger logger = (Logger) LoggerFactory.getLogger("application");
					logger.debug("Value '{}' for parameter '{}' is not valid.", value, name);
					
					// Add an error request attribute
					String error = new String("Value '" + value + "' for parameter '" + name + "' is not valid.");
					request.setAttribute("error", error);
					
					// Get and forward to the jsp pages
					RequestDispatcher view = request.getRequestDispatcher(SERVER_PARAMETERS_JSP);
					view.forward(request, response);
				}

			}
			
			// All parameter values were validated, so update them in the database
			for (ServerParameter param : toUpdate) {
				ServerParameterOutputMapper.update(param);	
			}
			
			// Add the server parameters back to the request
			request.setAttribute("serverParameters", params);
			request.setAttribute("success", new String("Successfully updated parameters."));
			
			// Get and forward to the jsp pages
			RequestDispatcher view = request.getRequestDispatcher(SERVER_PARAMETERS_JSP);
			view.forward(request, response);
		}
		
	}
}
