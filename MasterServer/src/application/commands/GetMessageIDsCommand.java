package application.commands;

import java.io.DataOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import application.IOUtils;
import application.ServerParameters;
import application.strategy.MissingRetrievalStrategyException;
import application.strategy.RetrievalStrategy;
import application.strategy.RetrievalStrategyFactory;

import exceptions.MapperException;
import exceptions.ParameterException;
import exceptions.UnrecognizedUserException;

public class GetMessageIDsCommand extends FrontCommand{

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws MapperException, ParameterException, UnrecognizedUserException, SQLException, IOException {
		
		String stringLongitude;
		String stringLatitude;
		String stringSpeed;
		String sort = "";
		String isAdvertiser = "";
		String stringLimit;
		
		float speed = 0;
		double longitude;
		double latitude;
		int limit;
		
		// Get longitude from request object
		if ((stringLongitude = request.getParameter("longitude")) == null)
			throw new ParameterException("Missing 'longitude' parameter.");
		
		// Get latitude from request object
		if ((stringLatitude = request.getParameter("latitude")) == null)
			throw new ParameterException("Missing 'latitude' parameter.");

		if ((sort = request.getParameter("sort")) == null)
			throw new ParameterException("Missing 'sort' parameter.");
		
		if ((isAdvertiser = request.getParameter("advertiser")) == null)
			isAdvertiser = Boolean.toString(Boolean.parseBoolean(isAdvertiser));
		
		if ((stringLimit = request.getParameter("limit")) == null)
			limit = Integer.parseInt(ServerParameters.getUniqueInstance().get("minMessages").getValue());
		
		try {
			// Get speed from request object
			if ((stringSpeed = request.getParameter("speed")) != null)
				speed = Float.parseFloat(stringSpeed);
			
			longitude = Double.parseDouble(stringLongitude);
			latitude = Double.parseDouble(stringLatitude);
			limit = Integer.parseInt(stringLimit);
		} catch (NumberFormatException e) {
			throw new ParameterException("Longitude, latitude, speed, and/or limit number format exception.", e);
		}
		
		RetrievalStrategyFactory factory = RetrievalStrategyFactory.getUniqueInstance();
		RetrievalStrategy strategy = null;
		
		try {
			strategy = factory.createStrategy(sort, isAdvertiser);
		} catch (MissingRetrievalStrategyException e) {
			throw new ParameterException(e.getMessage());
		}
		
		List<BigInteger> ids = strategy.retrieve(longitude, latitude, speed, limit);
		
		response.setContentType("application/octet-stream");
		response.setStatus(HttpServletResponse.SC_OK);
		
		IOUtils.writeListMessageIDsToStream(ids, new DataOutputStream(response.getOutputStream()));
	}

	
}
