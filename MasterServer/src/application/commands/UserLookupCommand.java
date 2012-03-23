package application.commands;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import exceptions.MapperException;
import exceptions.ParameterException;
import exceptions.UnrecognizedUserException;

public class UserLookupCommand extends FrontCommand {

	private static String JSP_PAGE = "/WEB-INF/jsp/UserLookup.jsp";

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws MapperException, ParameterException, IOException, UnrecognizedUserException, SQLException, ServletException {		
		
		response.setStatus(HttpServletResponse.SC_OK);
		
		// Get and forward to the jsp pages
		RequestDispatcher view = request.getRequestDispatcher(JSP_PAGE);
		view.forward(request, response);
		
	}

}
