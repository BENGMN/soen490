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

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.LoggerFactory;

import application.IOUtils;
import application.ServerParameters;

import ch.qos.logback.classic.Logger;

import domain.user.User;
import domain.user.UserFactory;
import domain.user.UserType;
import domain.user.mappers.UserOutputMapper;
import exceptions.ParameterException;

public class CreateUserCommand extends FrontCommand {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ParameterException, SQLException {
		ServerParameters params = ServerParameters.getUniqueInstance();
		// Create some local variables to store the request parameters
		String email, password, userType, responseType;
		
		// Capture the request parameters
		email = (String) request.getParameter("email");
		password = request.getParameter("password");
		userType = request.getParameter("usertype").toUpperCase();
		responseType = request.getParameter("responsetype").toUpperCase();
		
		// Perform some validation on the request parameters
		if (email == null) {
			throw new ParameterException("Missing 'email' parameter.");
		} else if (!IOUtils.validateEmail(email)) {
			throw new ParameterException("Invalid 'email' parameter provided");
		} // These could totally be removed
		else if (email.length() < Integer.getInteger(params.get("minEmailLength").getValue())) {
			throw new ParameterException("Invalid 'email' parameter, too short.");
		} else if (email.length() > Integer.getInteger(params.get("maxEmailLength").getValue())) {
			throw new ParameterException("Invalid 'email' parameter, too long.");
		}
	
		// Check the password length, should be validated in jsp as well	
		if (password == null) {
			throw new ParameterException("Missing 'password' parameter.");
		} else if (password.length() < Integer.getInteger(params.get("minPasswordLength").getValue())) {
			throw new ParameterException("Invalid 'password' parameter, too short.");
		}

		// Check the type of user account
		if (userType == null) {
			throw new ParameterException("Missing 'usertype' parameter.");
		} else if (!(userType.equals("USER_NORMAL") || userType.equals("USER_ADVERTISER"))) {
			throw new ParameterException("Invalid 'usertype' parameter provided");
		}
		
		// Check the response type
		if (responseType == null) {
			throw new ParameterException("Missing 'responsetype' parameter.");
		} else if (!(responseType.equals("JSP") || responseType.equals("XML") || responseType.equals("BIN"))) {
			throw new ParameterException("Invalid 'responseType' parameter provided");
		}
		
		// Since all of the validations succeeded let's make a new user
		UserType type = UserType.valueOf(userType);
		User newUser = null;
		
		Logger logger = (Logger)LoggerFactory.getLogger("application");
		
		try {
			// Create the new User
			newUser = UserFactory.createNew(email, password, type);
			
			// Add the new user to the database
			UserOutputMapper.insert(newUser);
		} catch (NoSuchAlgorithmException e) {
			if (responseType.equals("JSP"))
				request.setAttribute("error", "User could not be created to due to NoAlgorithmException being thrown buy the ID generator");
			
			logger.error("No such algorithm exception thrown when trying to create user: {}", e);
			
			if (responseType.equals("JSP")) {
				request.setAttribute("user", newUser);
			} else if (responseType.equals("XML")) {

			} else if (responseType.equals("BIN")) {
				
			}
			
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		}
		
		logger.info("New User with ID {} was created.", newUser.getUid().toString());
		response.setStatus(HttpServletResponse.SC_OK);
	}
	
}
