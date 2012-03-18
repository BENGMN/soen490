package application.commands;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.Logger;

import domain.user.User;
import domain.user.UserFactory;
import domain.user.UserType;
import domain.user.mappers.UserOutputMapper;
import exceptions.ParameterException;

public class CreateUserCommand extends FrontCommand {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ParameterException {
		
		// Create some local variables to store the request parameters
		String email, password, user_type;
		
		// Capture the request parameters
		email = (String) request.getParameter("email");
		password = request.getParameter("password");
		user_type = request.getParameter("user_type");
		
		// Perform some validation on the request parameters
		if (email == null) {
			throw new ParameterException("Missing 'email address' parameter");
		}
		else if (!validateEmail(email)) {
			throw new ParameterException("Invalid 'email address' parameter provided");
		}
			
		if (password == null) {
			throw new ParameterException("Missing 'password' parameter");
		}
		else if (password.length() > 0) {
			throw new ParameterException("Invalid 'password' parameter, too short.");
		}

		if (user_type == null) {
			throw new ParameterException("Missing 'user type' parameter");
		}
		else if (!(user_type.equalsIgnoreCase("USER_NORMAL") || user_type.equalsIgnoreCase("USER_ADVERTISER"))) {
			throw new ParameterException("Invalid 'user type' parameter provided");
		}
		
		// Since all of the validations succeeded let's make a new user
		UserType type = UserType.valueOf(user_type);
		User newUser = null;
		
		try {
			// Create the new User
			newUser = UserFactory.createNew(email, password, type);
			
			// Add the new user to the database
			UserOutputMapper.insert(newUser);
		} catch (NoSuchAlgorithmException e) {
			// TODO
			// NOTE This error will be thrown by the uniqueIDGenerator in the UserFactory
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		} catch (SQLException e) {
			// TODO
			// NOTE This error will be thrown if the user cannot be inserted into the database
			// normally this is because of a duplicate primary key
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		}
		
		Logger logger = (Logger)LoggerFactory.getLogger("application");
		logger.debug("New User with ID {} was created.", newUser.getUid().toString());
		response.setStatus(HttpServletResponse.SC_OK);
	}
	
	/**
	 * Function used to ensure that a string conforms to the email address syntax.
	 * @param emailAddress String parameter containing the email address to be tested
	 * @return True is returned if the email address has valid syntax, false otherwise.
	 */
	public boolean validateEmail(String emailAddress) {  
		String email_regex ="^[\\w\\-]([\\.\\w])+[\\w]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";  
		Pattern pattern = Pattern.compile(email_regex, Pattern.CASE_INSENSITIVE);  
		Matcher matcher = pattern.matcher(emailAddress);
		return matcher.matches();
	}
	
}
