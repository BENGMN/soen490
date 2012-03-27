/**
 * SOEN 490
 * Capstone 2011
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
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.LoggerFactory;

import application.ServerParameters;
import application.response.IOUtils;
import application.response.ResponseType;
import ch.qos.logback.classic.Logger;
import domain.user.User;
import domain.user.UserType;
import domain.user.mappers.UserInputMapper;
import domain.user.mappers.UserOutputMapper;

import exceptions.LostUpdateException;
import exceptions.MapperException;
import exceptions.ParameterException;
import exceptions.UnrecognizedUserException;

/**
 * Command for updating user information 
 * Request parameters: 
 *  - userid Unique id of user to update
 *  - verison User version, throws LostUpdateException if the user requested is modified before this command executes
 *  - password New password 
 *  - usertype The type of user to change to
 *  - responsetype The response type the client is expecting
 */
public class UpdateUserCommand extends FrontCommand {

	private static String SUCCESS_CREATE_USER = "/WEB-INF/jsp/success.jsp";

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws MapperException, ParameterException, IOException,	UnrecognizedUserException, SQLException, ServletException {
		
		ServerParameters params = ServerParameters.getUniqueInstance();
		
		// Create some local variables to store the request parameters
		String password, userType, responseType, userid;
		String version;
		int intVersion;
		UserType type;
		ResponseType resp;
		BigInteger uid;
		
		// Capture the request parameters
		password = request.getParameter("password");
		userType = request.getParameter("usertype");
		responseType = request.getParameter("responsetype");
		userid = request.getParameter("userid");
		version = request.getParameter("version");

		// Validation
		if (userid == null) {
			throw new ParameterException("Missing 'userid' parameter.");
		}
		
		try {
			uid = new BigInteger(userid);
		} catch (NumberFormatException e) {
			throw new ParameterException("Invalid 'userid' parameter provided.");
		}
			
		// Check the password length, should be validated in jsp as well	
		if (password == null) {
			throw new ParameterException("Missing 'password' parameter.");
		} else if (password.length() < Integer.parseInt(params.get("minPasswordLength").getValue())) {
			throw new ParameterException("Invalid 'password' parameter, too short.");
		}
		
		if(version == null) {
			throw new ParameterException("Missing 'version' parameter.");
		}
		
		try {
			intVersion = Integer.parseInt(version);
		} catch (NumberFormatException e) {
			throw new ParameterException("Invalid 'version' parameter provided.");
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
		
		User user = UserInputMapper.find(uid);
		
		Logger logger = (Logger)LoggerFactory.getLogger("application");
		
		DataOutputStream out = null;

		try {
			String hashedPass = IOUtils.hashPassword(password);
			
			user.setPassword(hashedPass);
			user.setType(type);
			user.setVersion(intVersion);
			
			// update user to the database
			UserOutputMapper.update(user);
			
			// write success message to response based on the requested response type
			response.setStatus(HttpServletResponse.SC_OK);
			
			String message = "User with id: " + user.getUid().toString() + " was updated successfully.";

			// Format the response based on requested response type
			switch (resp) {
			case JSP:
				request.setAttribute("success", message);
				request.setAttribute("user", user);
				
				RequestDispatcher view = request.getRequestDispatcher(SUCCESS_CREATE_USER);
				view.forward(request, response);
				break;
			case XML:
				out = new DataOutputStream(response.getOutputStream());
				response.setContentType("text/xml");
				IOUtils.writeUserToXML(user, out);
				IOUtils.writeStatusMessageToXML("success", message, out);
				break;
			case BIN:
				out = new DataOutputStream(response.getOutputStream());
				response.setContentType("application/octet-stream");
				IOUtils.writeUserToStream(user, out);
				IOUtils.writeStatusMessageToStream(message, out);
				break;
			}
			
			logger.info("User with ID {} was updated.", user.getUid().toString());
			
		}
		catch (NoSuchAlgorithmException e) {			
			logger.error("No such algorithm exception thrown when trying to create user: {}", e);
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		} // The password hashing didn't work 
		catch (UnsupportedEncodingException e) {
			logger.error("Unsupported encoding exception thrown when trying to create user: {}", e);
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		} // Updating the user didn't work
		catch (LostUpdateException e) {
			logger.error("Lost Update exception thrown when updating user: {}", e);
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		} 
	}
	


}
