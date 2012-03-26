/**
 * SOEN 490
 * Capstone 2011
 * Create user command; allows the creation of a user from a client.
 * Team members: 	
 * 			Sotirios Delimanolis
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

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.LoggerFactory;

import application.IOUtils;
import application.ResponseType;
import application.ServerParameters;

import ch.qos.logback.classic.Logger;

import domain.user.User;
import domain.user.UserFactory;
import domain.user.UserType;
import domain.user.mappers.UserInputMapper;
import domain.user.mappers.UserOutputMapper;
import exceptions.MapperException;
import exceptions.ParameterException;

public class CreateUserCommand extends FrontCommand {
	private static String SUCCESS_CREATE_USER = "/WEB-INF/jsp/success.jsp";
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ParameterException, SQLException, ServletException, IOException, MapperException {
		ServerParameters params = ServerParameters.getUniqueInstance();
		
		// Create some local variables to store the request parameters
		String email, password, userType, responseType;
		UserType type;
		ResponseType resp;
		
		// Capture the request parameters
		email = (String) request.getParameter("email");
		password = request.getParameter("password");
		userType = request.getParameter("usertype");
		responseType = request.getParameter("responsetype");
				
		// Validation
		if (email == null) {
			throw new ParameterException("Missing 'email' parameter.");
		} else if (!IOUtils.validateEmail(email)) {
			throw new ParameterException("Invalid 'email' parameter provided");
		} // These could totally be removed
		else if (email.length() < Integer.parseInt(params.get("minEmailLength").getValue())) {
			throw new ParameterException("Invalid 'email' parameter, too short.");
		} else if (email.length() > Integer.parseInt(params.get("maxEmailLength").getValue())) {
			throw new ParameterException("Invalid 'email' parameter, too long.");
		}
		
		//Checking to see if the user already exists
		try{
			UserInputMapper.findByEmail(email);
			throw new ParameterException("User with email '" + email + "' already exists.");
		} catch (MapperException e) {
		}
		
		// Check the password length, should be validated in jsp as well	
		if (password == null) {
			throw new ParameterException("Missing 'password' parameter.");
		} else if (password.length() < Integer.parseInt(params.get("minPasswordLength").getValue())) {
			throw new ParameterException("Invalid 'password' parameter, too short.");
		}

		// Check the type of user account
		if (userType == null) {
			throw new ParameterException("Missing 'usertype' parameter.");
		} 
		
		try {
			// Get the type of user while validating
			type = UserType.valueOf(userType.toUpperCase());
		} catch (IllegalArgumentException e) {
			throw new ParameterException("Invalid 'usertype' parameter provided.");
		}
		
		// Check the response type
		if (responseType == null) {
			throw new ParameterException("Missing 'responsetype' parameter.");
		} 
		
		try {
			resp = ResponseType.valueOf(responseType.toUpperCase());
		} catch (IllegalArgumentException e) {
			throw new ParameterException("Invalid 'responsetype' parameter provided.");
		}		
		// End of Validation
		
		User newUser = null;
		
		Logger logger = (Logger)LoggerFactory.getLogger("application");
		
		try {
			String hashedPass = IOUtils.hashPassword(password);
			
			newUser = UserFactory.createNew(email, hashedPass, type);
			
			// add the new user to the database
			UserOutputMapper.insert(newUser);
			
			// write success message to response based on the requested response type
			response.setStatus(HttpServletResponse.SC_OK);
			
			String message = "User with id: " + newUser.getUid().toString() + " was created successfully.";
			DataOutputStream out;
			// Format the response based on requested response type
			switch (resp) {
			case JSP:
				request.setAttribute("success", message);
				request.setAttribute("user", newUser);
				
				RequestDispatcher view = request.getRequestDispatcher(SUCCESS_CREATE_USER);
				view.forward(request, response);
				break;
			case XML:
				out = new DataOutputStream(response.getOutputStream());
				response.setContentType("text/xml");
				IOUtils.writeUserToXML(newUser, out);
				IOUtils.writeStatusMessageToXML("success", message, out);
				break;
			case BIN:
				out = new DataOutputStream(response.getOutputStream());
				response.setContentType("application/octet-stream");
				IOUtils.writeUserToStream(newUser, out);
				IOUtils.writeStatusMessageToStream(message, out);
				break;
			}
			
			logger.info("New User with ID {} was created.", newUser.getUid().toString());
			
		} // The unique id generating didn't work 
		catch (NoSuchAlgorithmException e) {			
			logger.error("No such algorithm exception thrown when trying to create user: {}", e);
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		} // The password hashing didn't work 
		catch (UnsupportedEncodingException e) {
			logger.error("Unsupported encoding exception thrown when trying to create user: {}", e);
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		}
	}	
}
