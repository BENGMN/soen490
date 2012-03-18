package application.commands;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.LoggerFactory;

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
		String email, password, usertype;
		
		// Capture the request parameters
		email = (String) request.getParameter("email");
		password = request.getParameter("password");
		usertype = request.getParameter("usertype");
		
		// Perform some validation on the request parameters
		if (email == null) {
			throw new ParameterException("Missing 'email' parameter.");
		} else if (!validateEmail(email)) {
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
		if (usertype == null) {
			throw new ParameterException("Missing 'usertype' parameter.");
		} else if (!(usertype.equalsIgnoreCase("USER_NORMAL") || usertype.equalsIgnoreCase("USER_ADVERTISER"))) {
			throw new ParameterException("Invalid 'usertype' parameter provided");
		}
		
		// Since all of the validations succeeded let's make a new user
		UserType type = UserType.valueOf(usertype);
		User newUser = null;
		
		Logger logger = (Logger)LoggerFactory.getLogger("application");
		
		try {
			// Create the new User
			newUser = UserFactory.createNew(email, password, type);
			
			// Add the new user to the database
			UserOutputMapper.insert(newUser);
		} catch (NoSuchAlgorithmException e) {
			
			logger.error("No such algorithm exception thrown when trying to create user: {}", e);
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		} 
		
		logger.info("New User with ID {} was created.", newUser.getUid().toString());
		response.setStatus(HttpServletResponse.SC_OK);
	}
	
	/**
	 * Function used to ensure that a string conforms to the email address syntax.
	 * @param emailAddress String parameter containing the email address to be tested
	 * @return Returns true if the email address has valid syntax, false otherwise.
	 */
	private boolean validateEmail(String emailAddress) {  
		String email_regex ="^[\\w\\-]([\\.\\w])+[\\w]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";  
		Pattern pattern = Pattern.compile(email_regex, Pattern.CASE_INSENSITIVE);  
		Matcher matcher = pattern.matcher(emailAddress);
		return matcher.matches();
	}
	
}
