package application;
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


import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Scanner;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import exceptions.MapperException;
import exceptions.ParameterException;
import application.commands.FrontCommand;
import application.commands.UnsupportedCommand;
import application.commands.UpdateConfigCommand;
import foundation.Database;
import foundation.ServletInformation;



public class ConfigurationController extends HttpServlet {

	private static final long serialVersionUID = 8691536805973858130L;
	
	// map that holds all the command to be executed depending on the passed command string in the request url
	private HashMap<String, FrontCommand> commandMap = null;
		
	// Overridden to make sure that we have a database.
	public void init() throws ServletException {
		try {
			ServletInformation.getInstance().setServletContext(getServletContext());
			if (!Database.isDatabaseCreated())
				Database.createDatabase();
		}
		catch (Exception E) {
			//throw new ServletException(E);
		}
	}

	/**
	 * Constructor
	 * Initialises the commands in the command map. It will be used to determine the right command to execute depending on the command parameter in the query string
	 */
	public ConfigurationController() {
		
		commandMap = new HashMap<String, FrontCommand>();
		
		// This should be called before readmessage
		commandMap.put("POST.updateServerConfiguration", new UpdateConfigCommand());		
	}
	
	/**
	 * This method returns the command that is mapped for the specified command string.
	 * @param commandString
	 * @return Returns the command mapped to the passed command string or an unknow command if there is no mapping
	 */
	private FrontCommand getCommand(String httpMethod, String commandString) {
		FrontCommand command = commandMap.get(httpMethod + "." + commandString);
		if (command == null)
			command = new UnsupportedCommand(httpMethod, commandString);
		return command;
	}
	
	// TODO log some errors
	
	/**
	 * This method handles all request s
	 */
	private void handleRequest(HttpServletRequest request, HttpServletResponse response, String httpMethod) throws ServletException {
		Connection conn = null;
				
		// TODO might not need transaction
		try {
			conn = Database.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement ps = conn.prepareStatement("START TRANSACTION;");
			ps.execute();
			ps.close();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		
		// Get the command string from the request object (url ex.: www.webserver.com?command=downvote&messageid=12433512343524683542)
		String commandString = request.getParameter("command");

		// Get the command from the command string
		FrontCommand command = getCommand(httpMethod, commandString);
		
		try {
			try {				
				
				// Execute the command
				command.execute(request, response);
			} catch (SQLException e) {
				response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.toString());
			} catch (IOException e) {
				response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.toString());
			} catch (ParameterException e) {
				response.sendError(HttpServletResponse.SC_BAD_REQUEST, e.toString());
				// TODO would the below be bad requests as well
			} catch (NoSuchAlgorithmException e) {
				response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.toString());
			} catch (MapperException e) {
				response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.toString());
			}
		} catch (IOException e) {
			throw new ServletException(e);
		}
		
		try {
			conn = Database.getConnection();
			PreparedStatement ps = conn.prepareStatement("COMMIT;");
			ps.execute();
			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				Database.freeConnection();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}	
	}
	
	/*
	 * Methods for handling different types of HTTP method calls.
	 * They all call handleRequest() by passing in their HTTP method name.
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException {
		handleRequest(request, response, "POST");
	}
}
