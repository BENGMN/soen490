package application.commands;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.Logger;
import domain.message.mappers.MessageOutputMapper;
import domain.serverparameter.ServerParameter;
import domain.serverparameter.mappers.ServerParameterOutputMapper;

import application.ServerParameters;

import exceptions.MapperException;
import exceptions.ParameterException;
import exceptions.UnrecognizedUserException;
import foundation.tdg.ServerParameterTDG;

public class UpdateServerParametersCommand extends FrontCommand {
	private static String SERVER_PARAMETERS_JSP = "/WEB-INF/jsp/ServerConfigurationUtility.jsp";
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response)throws MapperException, ParameterException, UnrecognizedUserException, SQLException, ServletException, IOException {
		// Source: where to get server parameter updates
		String source = request.getParameter("source");
		
		if (source == null)
			throw new ParameterException ("Missing 'source' parameter.");			
		else if ("database".equals(source))
			// Update from the database
			ServerParameters.getUniqueInstance().updateParameters();
		else if ("client".equals(source)) {
			// TODO if you were to change the description too, it would be done here
			
			// Updates the values
			
			List<String> parameterNames = new ArrayList<String>(10);
			
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
			
			for (String name: parameterNames) {
				String value = request.getParameter(name);
				
				if (value == null || "".equals(value))
					throw new ParameterException("Missing '" + name + "' parameter.");
				
				ServerParameter param = ServerParameters.getUniqueInstance().get(name);
				param.setValue(value);
				
				//TODO some kind of check for the type of the value
				
				ServerParameterOutputMapper.update(param);	
			}
			
			// Add the server parameters back to the request
			request.setAttribute("serverParameters", ServerParameters.getUniqueInstance());
			
			// TODO update all other servers
			// Get and forward to the jsp pages
			RequestDispatcher view = request.getRequestDispatcher(SERVER_PARAMETERS_JSP);
			view.forward(request, response);
		}
		
	}
}
