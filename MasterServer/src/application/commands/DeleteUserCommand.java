package application.commands;

import java.io.DataOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.Logger;

import application.IOUtils;
import application.ResponseType;

import domain.user.User;
import domain.user.mappers.UserInputMapper;
import domain.user.mappers.UserOutputMapper;

import exceptions.LostUpdateException;
import exceptions.MapperException;
import exceptions.ParameterException;
import exceptions.UnrecognizedUserException;

public class DeleteUserCommand extends FrontCommand {	
	private static final String SUCCESS_DELETE_USER = "/WEB-INF/jsp/success.jsp";
	private static final String FAIL_DELETE_USER = "/WEB-INF/jsp/error.jsp";
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws MapperException, ParameterException, IOException, UnrecognizedUserException, SQLException, ServletException {
		
		// Create some local variables to store the request/response parameters
		BigInteger userID = null;
		String userid, version, responseType;
		ResponseType type;
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
		
		// Check the response type
		if (responseType == null) {
			throw new ParameterException("Missing 'responsetype' parameter.");
		} 
		
		try {
			type = ResponseType.valueOf(responseType.toUpperCase());
		} catch (IllegalArgumentException e) {
			throw new ParameterException("Invalid 'responsetype' parameter provided.");
		}
		// End Validation
		
		User user = UserInputMapper.find(userID);
		
		// Set the version of the object to that which the client provided
		user.setVersion(intVersion);
					
		// Delete the user from the database
		try {
			UserOutputMapper.delete(user);
			
			response.setStatus(HttpServletResponse.SC_OK);
			
			switch (type) {
			case JSP:
				String message = "User with id: " + user.getUid().toString() + " deleted successfully.";
				request.setAttribute("message", message);
				
				RequestDispatcher view = request.getRequestDispatcher(SUCCESS_DELETE_USER);
				view.forward(request, response);
				break;
				
			// For below, can add success response message. For now, simply send 200 OK.
			case XML:
			case BIN:
				break;
			}
			
		} // LostUpdateException occurs if the version of the local user is different than the one in the database
		catch (LostUpdateException e) {
			String error = e.getMessage();
			DataOutputStream out = null;
			response.setStatus(HttpServletResponse.SC_CONFLICT);
			switch (type) {
			case JSP:
				// HTTP 409 Status Code, request could not be completed with the state of the user
				request.setAttribute("error", error);
				request.setAttribute("user", user);
				
				RequestDispatcher view = request.getRequestDispatcher(FAIL_DELETE_USER);
				view.forward(request, response);
				break;
				
			case XML:
				out = new DataOutputStream(response.getOutputStream());
				// HTTP 409 Status Code, request could not be completed with the state of the user
				response.setContentType("text/xml");
				IOUtils.writeUserToXML(user, out);
				IOUtils.writeStatusMessageToXML("error", error, out);
				break;
				
			case BIN:
				out = new DataOutputStream(response.getOutputStream());
				// HTTP 409 Status Code, request could not be completed with the state of the user
				response.setContentType("application/octet-stream");
				IOUtils.writeUserToStream(user, out);
				IOUtils.writeStatusMessageToStream(error, out);
				break;
			}

		}
		
		Logger logger = (Logger) LoggerFactory.getLogger("application");
		logger.info("User with ID {} was deleted.", user.getUid());
	}
}
