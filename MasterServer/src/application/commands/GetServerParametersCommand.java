package application.commands;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import application.ServerParameters;

import exceptions.MapperException;
import exceptions.ParameterException;
import exceptions.UnrecognizedUserException;

public class GetServerParametersCommand extends FrontCommand {

	private static String SERVER_PARAMETERS_JSP = "WebContent/WEB-INF/ServerConfigurationUtility.jsp";

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws MapperException, ParameterException, IOException, UnrecognizedUserException, NoSuchAlgorithmException, SQLException {		
		
		ServerParameters params = ServerParameters.getUniqueInstance();				
		request.setAttribute("serverConfiguration", params);
		
		RequestDispatcher view = request.getRequestDispatcher(SERVER_PARAMETERS_JSP);
		try {
			view.forward(request, response);
		} catch (ServletException e) {
			e.printStackTrace();
		}
	}

}
