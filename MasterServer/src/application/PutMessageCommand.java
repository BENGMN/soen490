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

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.GregorianCalendar;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.msgpack.MessagePack;
import org.msgpack.packer.Packer;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import technical.UnrecognizedUserException;

import domain.message.Message;
import domain.message.MessageFactory;
import domain.user.User;
import domain.user.UserInputMapper;

public class PutMessageCommand extends RegionalCommand
{
	// 50 K is our max upload size, for now.
	public static int maximumUploadSize = 50 * 1024;
	
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException, UnrecognizedUserException, ParameterException, NoSuchAlgorithmException
	{
		MultipartResolver resolver = new CommonsMultipartResolver();
		// Make sure our request is multi-part; if it's not, then it's not properly formatted.
		if (!resolver.isMultipart(request))
			throw new ParameterException("Put requests must be multi-part, as in RFC1867.");
		MultipartHttpServletRequest multipartRequest = resolver.resolveMultipart(request); 
		// If java is smart, it will allocate this on the stack.
		MultipartFile multipartFile = multipartRequest.getFile("bin");
		if (multipartFile == null)
			throw new ParameterException("Put requests must have 'bin' as a multipart file upload.");
		byte[] messageBytes = null;
		messageBytes = multipartFile.getBytes();
		
		// After we have our file bytes, let's check out all this stuff.
		double longitude = Double.parseDouble(multipartRequest.getParameter("longitude"));
		double latitude = Double.parseDouble(multipartRequest.getParameter("latitude"));
		float speed = Float.parseFloat(multipartRequest.getParameter("speed"));
		// They told us to keep the user system light; this is about as light as it gets; we're not authenticating, just passing in the email as a parameter.
		// TODO Will obviously have to change this in future, but will work for now.
		String email = multipartRequest.getParameter("email");
		if (email == null)
			throw new ParameterException("Must pass in the email to validate user.");
		User user = UserInputMapper.findByEmail(email);
		if (user == null)
			throw new UnrecognizedUserException();
		Message message = MessageFactory.createNew(user.getUid(), messageBytes, speed, latitude, longitude, new Timestamp(GregorianCalendar.getInstance().getTimeInMillis()), 0);
		MessagePack messagePack = new MessagePack();
		Packer packer = messagePack.createPacker(response.getOutputStream());
		packer.write(message.getMid());
		response.setStatus(HttpServletResponse.SC_ACCEPTED);
	}

}
