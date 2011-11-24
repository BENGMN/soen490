/**
 * SOEN 490
 * Capstone 2011
 * Get Messages Command; gets messages for a particular user.
 * Team members: 	Sotirios Delimanolis
 * 			Filipe Martinho
 * 			Adam Harrison
 * 			Vahe Chahinian
 * 			Ben Crudo
 * 			Anthony Boyer
 * 
 * @author Capstone 490 Team Moving Target
 *
 */

package application;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import domain.message.Message;
import domain.message.MessageInputMapper;

public class GetMessagesCommand extends FrontCommand {
	
	private List<Message> messages = null;
	
	private final double EARTH_RADIUS_METERS = 6378137;
	private final double DEFAULT_USER_RADIUS_METERS = 500;
	
	public GetMessagesCommand() {}
	
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		
		double longitude = Float.parseFloat(request.getParameter("longitude"));
		double latitude = Float.parseFloat(request.getParameter("latitude"));
		
		double radius = DEFAULT_USER_RADIUS_METERS;
		
		try {
			 messages = MessageInputMapper.findAll();
		} catch (SQLException e) {
			// TODO do some error handling
			e.printStackTrace();
		}
		
		getInProximity(longitude, latitude, radius);
		
		
		/*
		try {
			
			ObjectOutputStream responseStream = new ObjectOutputStream(response.getOutputStream());
			responseStream.write(messages.length);
			for (int i = 0; i < messages.length; ++i)
				messages[i].writeObject(responseStream);
			
		} catch (Exception e1) {
			try	{
				response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error: " + e1);
			} catch (IOException e2) {
				e2.printStackTrace();
			}
		}*/
	}
	
	/**
	 * Sets the messages list to the messages within the bounds centered at (longitude,latitude)
	 * @param longitude User longitude
	 * @param latitude User latitude
	 * @param radius Bounding radius
	 */
	private void getInProximity(double longitude, double latitude, double radius) {
		// Haversine 
		// dist = arccos(sin(lat1) · sin(lat2) + cos(lat1) · cos(lat2) · cos(lon1 - lon2)) · R
		// R = radius of sphere
		
		List<Message> messagesInProximity = new LinkedList<Message>();
		
		for(Message m: messages) {
			Double distance = EARTH_RADIUS_METERS * Math.acos(Math.sin(Math.toRadians(latitude)) * Math.sin(Math.toRadians(m.getLatitude()) + Math.cos(Math.toRadians(latitude)) * Math.cos(Math.toRadians(m.getLatitude()) * Math.cos(Math.toRadians(longitude) - Math.toRadians(m.getLongitude())))));
			if (distance <= radius)
				messagesInProximity.add(m);			
		}

		messages = messagesInProximity;
	}
	
	/**
	 * Returns the messages retrieved by the command.
	 * @return Messages retrieved by the command.
	 */
	public List<Message> getMessages() {
		return messages;
	}
	
	// Longitude delta degrees: 0.007397276 = 500 meters
	// Latitude delta degrees: 0.00450269 = 500 meters
}
