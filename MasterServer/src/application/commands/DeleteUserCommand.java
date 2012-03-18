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
		
		// Get the server parameters
		ServerParameters params = ServerParameters.getUniqueInstance();
		
		// Create some local variables to store the request/response parameters
		BigInteger userID = null;
		String email, version, responseType;
		
		// Get the parameters from the request
		email = request.getParameter("email");
		version = request.getParameter("version");
		responseType = request.getParameter("responsetype");
		
		if (request.getParameter("userid") != null)
			userID = new BigInteger(request.getParameter("userid"));
		
		if (request.getParameter("version") != null)
			version = request.getParameter("version");
		
		// Make sure that an email or userid parameter was supplied
		if (!(email != null || userID != null))
			throw new ParameterException("Missing parameters 'email' and 'userid' in request");
		
		// Check the response type
		if (responseType == null) {
			throw new ParameterException("Missing 'responsetype' parameter.");
		} 
		else if (!(responseType.equals("JSP") || responseType.equals("XML") || responseType.equals("BIN"))){
			throw new ParameterException("Invalid 'responseType' parameter provided");
		}
		
		User user = null;
		
		if (email != null) {
			// If the email parameter is supplied use that to locate the object with
			user = UserInputMapper.findByEmail(email);
			
		} else if (userID != null) {
			// If the userID parameter is supplied use that to locate the object with
			user = UserInputMapper.find(userID);
		}
		
		// Set the version of the object to that which the client provided
		user.setVersion(Integer.parseInt(version));
					
		// Delete the user from the database
		int rows_affected = UserOutputMapper.delete(user);
		
		// WRITE A RESPONSE BASED ON THE RESPONSE TYPE

	}


}
