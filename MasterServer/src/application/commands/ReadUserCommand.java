package application.commands;

import java.io.IOException;
import java.math.BigInteger;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import application.ServerParameters;

import exceptions.MapperException;
import exceptions.ParameterException;
import exceptions.UnrecognizedUserException;

public class ReadUserCommand extends FrontCommand {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response)
			throws MapperException, ParameterException, IOException,
			UnrecognizedUserException, SQLException, ServletException {
		
		ServerParameters params = ServerParameters.getUniqueInstance();
		
		BigInteger userID;
		String email, userType, responseType;
		
		email = request.getParameter("email");
		userID = new BigInteger(request.getParameter("userid"));
		
	}

}
