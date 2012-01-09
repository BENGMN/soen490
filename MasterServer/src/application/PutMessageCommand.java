/**
 * SOEN 490
 * Capstone 2011
 * Put message command; allows a user to store a message on the server.
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

import java.io.IOException;

import java.util.Calendar;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import technical.UnrecognizedUserException;

import domain.message.Message;
import domain.message.MessageFactory;
import domain.message.MessageOutputMapper;
import domain.user.User;
import domain.user.UserInputMapper;

public class PutMessageCommand extends RegionalCommand
{
	// 50 K is our max upload size, for now.
	public static int maximumUploadSize = 50 * 1024;
	
	public void execute(HttpServletRequest request, HttpServletResponse response)
	{
		MultipartResolver resolver = new CommonsMultipartResolver();
		// Make sure our request is multi-part; if it's not, then it's not properly formatted.
		if (!resolver.isMultipart(request)) {
			try {
				response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Error: Put requests must be multi-part, as in RFC1867.");
			}
			catch (IOException e) {
				e.printStackTrace();
			}
			return;
		}
		MultipartHttpServletRequest multipartRequest = resolver.resolveMultipart(request); 
		// If java is smart, it will allocate this on the stack.
		MultipartFile multipartFile = multipartRequest.getFile("bin");
		if (multipartFile == null) {
			try {
				response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Error: Put requests must have 'bin' as a multipart file upload.");
			}
			catch (IOException e) {
				e.printStackTrace();
			}
			return;
		}
		byte[] messageBytes = null;
		try {
			messageBytes = multipartFile.getBytes();
		}
		catch (IOException e1) {
			try {
				response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error: " + e1 + ".");
			}
			catch (IOException e2) {
				e2.printStackTrace();
			}
			return;
		}
		
		// After we have our file bytes, let's check out all this stuff.
		double longitude = Double.parseDouble(multipartRequest.getParameter("longitude"));
		double latitude = Double.parseDouble(multipartRequest.getParameter("latitude"));
		float speed = Float.parseFloat(multipartRequest.getParameter("speed"));
		// They told us to keep the user system light; this is about as light as it gets; we're not authenticating, just passing in the email as a parameter.
		// TODO Will obviously have to change this in future, but will work for now.
		String email = multipartRequest.getParameter("email");
		User user = UserInputMapper.findByEmail(email);
		try
		{
			if (user == null)
				throw new UnrecognizedUserException();
			Message message = MessageFactory.createNew(user.getUid(), messageBytes, speed, latitude, longitude, Calendar.getInstance(), 0);
			MessageOutputMapper.insert(message);
			response.setStatus(HttpServletResponse.SC_ACCEPTED);
		}
		catch (Exception e1)
		{
			try	{
				response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error: " + e1);
			}
			catch (IOException e2) {
				e1.printStackTrace();
			}
		}
	}

}
