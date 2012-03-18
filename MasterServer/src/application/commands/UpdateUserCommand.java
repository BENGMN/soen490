package application.commands;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import exceptions.MapperException;
import exceptions.ParameterException;
import exceptions.UnrecognizedUserException;

public class UpdateUserCommand extends FrontCommand {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response)
			throws MapperException, ParameterException, IOException,
			UnrecognizedUserException, SQLException, ServletException {
		
		response.setStatus(HttpServletResponse.SC_OK);
		
	}

}
