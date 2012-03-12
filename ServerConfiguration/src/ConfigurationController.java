/**
 * SOEN 490
 * Capstone 2011
 * Front Controller; acts as an entrypoint for the program.
 * Delegates to FrontCommands.
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


import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import foundation.Database;
import foundation.ServletInformation;

public class ConfigurationController extends HttpServlet {

	private static final long serialVersionUID = 8691536805973858130L;
	
	private static ConfigurationController singleton = null;
	private HashMap<String, FrontCommand> commandMap;
	private UnknownCommand unknownCommand = null;
	
	public ConfigurationController()
	{
		commandMap = new HashMap<String, FrontCommand>();
		unknownCommand = new UnknownCommand();
		commandMap.put("ServerConfiguration", new ServerConfigurationCommand());
	}
	
	public static ConfigurationController getInstance()
	{
		if (singleton == null)
			singleton = new ConfigurationController();
		return singleton;
	}
	
	private FrontCommand getCommand(String commandString)
	{
		FrontCommand command = commandMap.get(commandString);
		if (command == null)
			command = unknownCommand;
		return command;
	}
	
	private void handleRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException
	{
		String commandString = request.getParameter("command");
		FrontCommand command = getCommand(commandString);
		try {
			try {
				if (command.responsible(request, response))
					command.execute(request, response);
			}
			catch (SQLException e) {
				response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.toString());
			}
			catch (IOException e) {
				response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.toString());
			}
			catch (ParameterException e) {
				response.sendError(HttpServletResponse.SC_BAD_REQUEST, e.toString());
			}
		}
		catch (Exception e) {
			throw new ServletException(e);
		}
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException {
		handleRequest(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException {
		handleRequest(request, response);
	}
	
	// Overridden to make sure that we have a database.
	public void init() throws ServletException {
		try {
			ServletInformation.getInstance().setServletContext(getServletContext());
			if (!Database.getInstance().isDatabaseCreated())
				Database.getInstance().createDatabase();
		}
		catch (Exception E) {
			throw new ServletException(E);
		}
	}
}
