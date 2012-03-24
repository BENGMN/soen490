package application.commands;

import java.io.DataOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import domain.user.User;
import domain.user.mappers.UserInputMapper;

import application.IOUtils;
import application.ResponseType;
import exceptions.MapperException;
import exceptions.ParameterException;
import exceptions.UnrecognizedUserException;

public class ReadUserCommand extends FrontCommand {
	private static String USER_JSP = "/WEB-INF/jsp/UserManager.jsp";

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws MapperException, ParameterException, UnrecognizedUserException, SQLException, ServletException, IOException {
		
		// Create some local variables to store the request/response parameters
		BigInteger userID = null;
		String userid, responseType;
		ResponseType type;
		// Get the parameters from the request
		userid = request.getParameter("userid");

		responseType = request.getParameter("responsetype");
		
		// Validation
		if (userid == null) 
			throw new ParameterException("Missing 'userid' parameter.");
		
		if (responseType == null) 
			throw new ParameterException("Missing 'responsetype' parameter.");
		
		try {
			type = ResponseType.valueOf(responseType.toUpperCase());
		} catch (IllegalArgumentException e) {
			throw new ParameterException("Invalid 'responseType' parameter provided.");
		}
		// End of Validation
		
		try {
			userID = new BigInteger(userid);
		} catch (NumberFormatException e) {
			throw new ParameterException("Invalid 'userid' parameter provided.");
		}
		
		// Create a variable to store the user to be retrieved
		User user = UserInputMapper.find(userID);
		DataOutputStream out = null;
		// Format response based on request response type
		switch (type) {
		case JSP:
			request.setAttribute("user", user);
			RequestDispatcher view = request.getRequestDispatcher(USER_JSP);
			view.forward(request, response);
			break;
			
		case XML:
			out = new DataOutputStream(response.getOutputStream());
			response.setContentType("application/xml");
			IOUtils.writeUserToXML(user, out);
			break;
			
		case BIN:
			out = new DataOutputStream(response.getOutputStream());
			response.setContentType("application/octet-stream");
			IOUtils.writeUserToStream(user, out);
			break;
		}
		
		response.setStatus(HttpServletResponse.SC_OK);
	}
}
