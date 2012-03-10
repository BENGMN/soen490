package application.commands;

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

import exceptions.MapperException;
import exceptions.ParameterException;
import exceptions.UnrecognizedUserException;
import domain.message.Message;
import domain.message.MessageFactory;
import domain.message.MessageIdentityMap;
import domain.message.mappers.MessageOutputMapper;
import domain.user.User;
import domain.user.mappers.UserInputMapper;

public class CreateMessageCommand extends FrontCommand {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws MapperException, ParameterException, IOException, UnrecognizedUserException, NoSuchAlgorithmException, SQLException {
		MultipartResolver resolver = new CommonsMultipartResolver();
		
		// Make sure our request is multi-part; if it's not, then it's not properly formatted.
		if (!resolver.isMultipart(request))
			throw new ParameterException("Put requests must be multi-part, as in RFC1867.");
		
		MultipartHttpServletRequest multipartRequest = resolver.resolveMultipart(request); 
		
		// If java is smart, it will allocate this on the stack.
		MultipartFile multipartFile = multipartRequest.getFile("bin");
		
		if (multipartFile == null)
			throw new ParameterException("Put requests must have 'bin' as a multipart file upload.");
		
		if (multipartFile.getSize() > (Long)request.getSession(true).getServletContext().getAttribute("MAX_UPLOAD_SIZE")) {
			// TODO throw some exception on size
		}
				
		byte[] messageBytes = null;
		messageBytes = multipartFile.getBytes();
		
		// Get location information
		double longitude = Double.parseDouble(multipartRequest.getParameter("longitude"));
		double latitude = Double.parseDouble(multipartRequest.getParameter("latitude"));
		float speed = Float.parseFloat(multipartRequest.getParameter("speed"));
		
		String email = multipartRequest.getParameter("email");
		
		if (email == null)
			throw new ParameterException("Must pass in the email to validate user.");
		
		User user = UserInputMapper.findByEmail(email);
		
		if (user == null)
			throw new UnrecognizedUserException("Unable to find user.");
		
		Message msg = MessageFactory.createNew(user.getUid(), messageBytes, speed, latitude, longitude, new Timestamp(GregorianCalendar.getInstance().getTimeInMillis()), 0);
		
		// Put the new message in the identity map and insert into db
		MessageIdentityMap.put(msg.getMid(), msg);		 
		MessageOutputMapper.insert(msg);
		
		//TODO change this sht
		// Write the id of the newly created message to the http response
		MessagePack messagePack = new MessagePack();
		Packer packer = messagePack.createPacker(response.getOutputStream());
		packer.write(msg.getMid().toString());
		response.setStatus(HttpServletResponse.SC_OK);
	}
	
}
