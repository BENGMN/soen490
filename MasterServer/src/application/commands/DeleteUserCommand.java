package application.commands;

import java.io.IOException;
import java.math.BigInteger;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import application.ServerParameters;
import domain.user.User;
import domain.user.mappers.UserInputMapper;
import domain.user.mappers.UserOutputMapper;

import exceptions.MapperException;
import exceptions.ParameterException;
import exceptions.UnrecognizedUserException;

public class DeleteUserCommand extends FrontCommand {	
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response)
			throws MapperException, ParameterException, IOException,
			UnrecognizedUserException, SQLException, ServletException {
		
		// Create some local variables to store the request/response parameters
		BigInteger userID = null;
		String userid, version, responseType;
		int intVersion;
		
		
		// Get the parameters from the request
		userid = request.getParameter("userid");
		version = request.getParameter("version");
		responseType = request.getParameter("responsetype");
		
		// Validation
		if (userid == null)
			throw new ParameterException("Missing 'userid' parameter.");
			
		if (version == null)
			throw new ParameterException("Missing 'version' parameter.");
		
		try {
			userID = new BigInteger(userid);
		} catch (NumberFormatException e) {
			throw new ParameterException("Parameter 'userid' is badly formatted, not a valid integer.");
		}
		
		try {
			intVersion = Integer.parseInt(version);
		} catch (NumberFormatException e) {
			throw new ParameterException("Parameter 'version' is badly formatted, not a valid integer.");
		}
		// End Validation
		
		// Check the response type
		if (responseType == null) {
			throw new ParameterException("Missing 'responsetype' parameter.");
		} 
		else if (!(responseType.equals("jsp") || responseType.equals("xml") || responseType.equals("bin"))){
			throw new ParameterException("Invalid 'responseType' parameter provided. Choose from 'jsp', 'xml', 'bin'.");
		}
				
		User user = UserInputMapper.find(userID);
		
		// Set the version of the object to that which the client provided
		user.setVersion(intVersion);
					
		// Delete the user from the database
		try {
			UserOutputMapper.delete(user);
		} catch (LostUpdateException e) {
			if (responseType.equals("jsp")) {
				request.setAttribute("error", e.getMessage());
				request.setAttribute("user", user);
			}
		}

		if (responseType.equals("jsp")) {
			response.setStatus(HttpServletResponse.SC_OK);
		}
		// WRITE A RESPONSE BASED ON THE RESPONSE TYPE

	}


}
