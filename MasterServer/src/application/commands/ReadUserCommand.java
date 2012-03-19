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

		// Get the parameters from the request
		userid = request.getParameter("userid");

		responseType = request.getParameter("responsetype");
		
		// Validation
		if (userid == null) 
			throw new ParameterException("Missing 'userid' parameter.");
		
		if (responseType == null) 
			throw new ParameterException("Missing 'responsetype' parameter.");
		
		if (!(responseType.equals("jsp") || responseType.equals("xml") || responseType.equals("bin"))) 
			throw new ParameterException("Invalid 'responseType' parameter provided, should be 'jsp', 'xml', or 'bin'.");
		// End of Val
		
		userID = new BigInteger(request.getParameter("userid"));
		
		// Create a variable to store the user to be retrieved
		User user = UserInputMapper.find(userID);
		
		// Format response based on request response type
		if (responseType.equals("jsp")) {
			
			request.setAttribute("user", user);
			RequestDispatcher view = request.getRequestDispatcher(USER_JSP);
			view.forward(request, response);
			
		} else if (responseType.equals("xml")) {
			
			response.setContentType("application/xml");
			IOUtils.writeUserToXML(user, new DataOutputStream(response.getOutputStream()));
			response.setStatus(HttpServletResponse.SC_OK);
			
		} else if (responseType.equals("bin")) {
			
			response.setContentType("application/octet-stream");
			IOUtils.writeUserToStream(user, new DataOutputStream(response.getOutputStream()));
			response.setStatus(HttpServletResponse.SC_OK);
		}
	}
}
