package application.commands;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import domain.user.User;
import domain.user.mappers.UserInputMapper;

import exceptions.MapperException;
import exceptions.ParameterException;
import exceptions.UnrecognizedUserException;

public class BrowseUsersCommand extends FrontCommand {

	private final static String BROWSE_USERS_JSP = "/WEB-INF/jsp/browseusers.jsp";
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response)throws MapperException, ParameterException, IOException, UnrecognizedUserException, SQLException, ServletException {
		List<User> users = UserInputMapper.findAll();
		
		request.setAttribute("users", users);
		
		RequestDispatcher view = request.getRequestDispatcher(BROWSE_USERS_JSP);
		view.forward(request, response);
	}

}
