/**
 * SOEN 490
 * Capstone 2011
 * Unknown Command; basically handles what happens when there is no available command.
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

package application.commands;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import exceptions.ParameterException;


public class UnsupportedCommand extends FrontCommand {
	
	private String httpMethod;
	private String commandString;
	
	public UnsupportedCommand(String httpMethod, String commandString) {
		this.httpMethod = httpMethod;
		if (commandString == null)
			this.commandString = "Missing 'command' parameter.";
		else
			this.commandString = commandString;
	}

	public void execute(HttpServletRequest request, HttpServletResponse response) throws ParameterException, IOException {
		String text = "HTTP " + httpMethod + " method for '" + commandString + "' command is not supported.";
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

