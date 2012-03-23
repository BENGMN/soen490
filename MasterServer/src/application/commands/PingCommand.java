package application.commands;

import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import exceptions.MapperException;
import exceptions.ParameterException;
import exceptions.UnrecognizedUserException;

/** 
 * This Command represents a null transaction for monitoring. It is used by the Load Balancer to check if the server is still active.
 */
public class PingCommand extends FrontCommand {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws MapperException, ParameterException, UnrecognizedUserException, SQLException, ServletException {
		response.setStatus(HttpServletResponse.SC_NO_CONTENT);
	}

}
