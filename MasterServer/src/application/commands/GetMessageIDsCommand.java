package application.commands;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import exceptions.MapperException;
import exceptions.ParameterException;
import exceptions.UnrecognizedUserException;
import foundation.MessageFinder;

public class GetMessageIDsCommand extends FrontCommand{

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws MapperException, ParameterException, IOException, UnrecognizedUserException, NoSuchAlgorithmException, SQLException {
		
		double longitude = Double.parseDouble(request.getParameter("longitude"));
		double latitude = Double.parseDouble(request.getParameter("latitude"));
		double speed = Double.parseDouble(request.getParameter("speed"));
		
		// TODO I know this is wrong eventually we need to fix it so that it is pure layered architecture
		MessageFinder.findIdsInProximity(longitude, latitude, speed);		
		
	}

	
}
