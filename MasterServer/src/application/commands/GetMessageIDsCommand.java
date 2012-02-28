package application.commands;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import domain.message.mappers.MessageInputMapper;

import exceptions.MapperException;
import exceptions.ParameterException;
import exceptions.UnrecognizedUserException;

public class GetMessageIDsCommand extends FrontCommand{

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws MapperException, ParameterException, IOException, UnrecognizedUserException, NoSuchAlgorithmException, SQLException {
		
		String stringLongitude;
		String stringLatitude;
		String stringSpeed;
		
		// Get message longitude from request object
		if ((stringLongitude = request.getParameter("longitude")) == null)
			throw new ParameterException("Must pass in the mid to downvote.");
		
		// Get message latitude from request object
		if ((stringLatitude = request.getParameter("latitude")) == null)
			throw new ParameterException("Must pass in the mid to downvote.");
		
		double speed = 0;
		// Get speed from request object
		if ((stringSpeed = request.getParameter("speed")) != null)
			speed = Double.parseDouble(stringSpeed);
				
		double longitude = Double.parseDouble(stringLongitude);
		double latitude = Double.parseDouble(stringLatitude);
		
		MessageInputMapper.findIdsInProximity(longitude, latitude, speed);		
		
	}

	
}
