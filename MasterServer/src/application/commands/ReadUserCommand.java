package application.commands;

import java.io.IOException;
import java.math.BigInteger;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import domain.user.User;
import domain.user.mappers.UserInputMapper;

import application.IOUtils;
import application.ServerParameters;

import exceptions.MapperException;
import exceptions.ParameterException;
import exceptions.UnrecognizedUserException;

public class ReadUserCommand extends FrontCommand {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response)
			throws MapperException, ParameterException, IOException,
			UnrecognizedUserException, SQLException, ServletException {
		
		// Get the server parameters
		ServerParameters params = ServerParameters.getUniqueInstance();
		
		// Create some local variables to store the request/response parameters
		BigInteger userID = null;
		String email, responseType;
		
		// Get the parameters from the request
		email = request.getParameter("email");
		responseType = request.getParameter("responsetype");
		
		if (request.getParameter("userid") != null)
			userID = new BigInteger(request.getParameter("userid"));
		
		// Make sure that an email or userid parameter was supplied
		if (!(email != null || userID != null))
			throw new ParameterException("Missing parameters 'email' and 'userid' in request");
		
		// Check the response type
		if (responseType == null) {
			throw new ParameterException("Missing 'responsetype' parameter.");
		} else if (!(responseType.equals("JSP") || responseType.equals("XML") || responseType.equals("BIN"))) {
			throw new ParameterException("Invalid 'responseType' parameter provided");
		}
				
		// Create a variable to store the user to be retrieved
		User user = null;
		// If the email parameter is supplied use that to search on
		if (email != null) {
			user = UserInputMapper.findByEmail(email);
		} else if (userID != null) {
			user = UserInputMapper.find(userID);
		}
		
		// WRITE A RESPONSE BASED ON THE RESPONSE TYPE

	}

}
