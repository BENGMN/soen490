package application.commands;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.math.BigInteger;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.msgpack.MessagePack;
import org.msgpack.packer.Packer;

import application.MessageHelper;

import domain.message.mappers.MessageInputMapper;

import exceptions.MapperException;
import exceptions.ParameterException;
import exceptions.UnrecognizedUserException;

public class GetMessageIDsCommand extends FrontCommand{

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws MapperException, ParameterException, UnrecognizedUserException, SQLException, IOException {
		
		String stringLongitude;
		String stringLatitude;
		String stringSpeed;
		
		float speed = 0;
		double longitude;
		double latitude;
		
		// Get message longitude from request object
		if ((stringLongitude = request.getParameter("longitude")) == null)
			throw new ParameterException("Missing 'longitude' parameter.");
		
		// Get message latitude from request object
		if ((stringLatitude = request.getParameter("latitude")) == null)
			throw new ParameterException("Missing 'latitude' parameter.");
		
		try {
			// Get speed from request object
			if ((stringSpeed = request.getParameter("speed")) != null)
				speed = Float.parseFloat(stringSpeed);
			
			longitude = Double.parseDouble(stringLongitude);
			latitude = Double.parseDouble(stringLatitude);
		} catch (NumberFormatException e) {
			// TODO Log this shit
			throw new ParameterException("Longitude, latitude, and/or speed number format exception.", e);
		}
		
		List<BigInteger> ids = MessageInputMapper.findIdsInProximity(longitude, latitude, speed);	
		
		response.setContentType("text/plain");
		response.setStatus(HttpServletResponse.SC_OK);
		
		MessageHelper.setMessageIDs(ids, new DataOutputStream(response.getOutputStream()));
	}

	
}
