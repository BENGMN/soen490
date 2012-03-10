package application.commands;

import java.io.IOException;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.List;

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
		double longitude;
		double latitude;
		
		try {
			// Get speed from request object
			if ((stringSpeed = request.getParameter("speed")) != null)
				speed = Double.parseDouble(stringSpeed);
			
			longitude = Double.parseDouble(stringLongitude);
			latitude = Double.parseDouble(stringLatitude);
		} catch (NumberFormatException e) {
			// TODO Log this shit
			throw new ParameterException("Longitude, latitude, and/or speed number format exception.", e);
		}
		
		List<BigInteger> ids = MessageInputMapper.findIdsInProximity(longitude, latitude, speed);		
		
		// TODO write these ids to the response stream
	}

	
}
