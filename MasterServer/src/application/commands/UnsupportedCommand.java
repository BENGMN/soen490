/**
 * SOEN 490
 * Capstone 2011
 * Team members: 	
 *  	    Sotirios Delimanolis
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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.Logger;

import exceptions.ParameterException;

/**
 * Command for an unsupported request type. If the command parameter in the request doesn't match any of the commands in the map,
 * this command will be executed, informing the client of the wrong command request provided. Unlike other commands, this one has 
 * class attributes. A new instance must be created for each wrong request.
 *
 */
public class UnsupportedCommand extends FrontCommand {
	
	private String httpMethod;
	private String commandString;
	
	public UnsupportedCommand(String httpMethod, String commandString) {
		this.httpMethod = httpMethod;
		if (commandString == null || "".equals(commandString))
			this.commandString = "Missing 'command' parameter.";
		else
			this.commandString = commandString;
	}

	public void execute(HttpServletRequest request, HttpServletResponse response) throws ParameterException, IOException {
		String text = "HTTP " + httpMethod + " method for '" + commandString + "' command is not supported.";
		// log
		Logger logger = (Logger)LoggerFactory.getLogger("application");
		logger.debug(text);
		
		response.sendError(HttpServletResponse.SC_NOT_IMPLEMENTED, text);
	}

	public String getCommandString() {
		return commandString;
	}

	public void setCommandString(String commandString) {
		this.commandString = commandString;
	}
	
	public String getHttpMethod() {
		return httpMethod;
	}

	public void setHttpMethod(String httpMethod) {
		this.httpMethod = httpMethod;
	}

}

