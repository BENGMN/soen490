/**
 * SOEN 490
 * Capstone 2011
 * Table Data Gateway for the User Domain Object
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
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class FrontController extends HttpServlet {
	private static final long serialVersionUID = -7153670066472721404L;
	
	private static FrontController singleton = null;
	private HashMap<String, FrontCommand> commandMap;
	private UnknownCommand unknownCommand = null;
	
	private FrontController()
	{
		commandMap = new HashMap<String, FrontCommand>();
		unknownCommand = new UnknownCommand();
		commandMap.put("GetMessages", new GetMessagesCommand());
		commandMap.put("PutMessage", new PutMessageCommand());
	}
	
	public static FrontController getInstance()
	{
		if (singleton == null)
			singleton = new FrontController();
		return singleton;
	}
	
	private FrontCommand getCommand(String commandString)
	{
		FrontCommand command = commandMap.get(commandString);
		if (command == null)
			command = unknownCommand;
		return command;
	}
	
	private void handleRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		String commandString = request.getParameter("command");
		FrontCommand command = getCommand(commandString);
		command.execute(request, response);
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		handleRequest(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		handleRequest(request, response);
	}
}
