package application.commands;

import java.io.DataOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.LoggerFactory;

import application.IOUtils;
import application.ResponseType;

import ch.qos.logback.classic.Logger;

import exceptions.MapperException;
import exceptions.ParameterException;
import exceptions.UnrecognizedUserException;
import domain.message.Message;
import domain.message.mappers.MessageInputMapper;

public class ReadMessageCommand extends FrontCommand {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws MapperException, ParameterException, IOException, UnrecognizedUserException, SQLException {

		String[] individualIDs;
		String responseType;
		ResponseType type;
		
		// Validation
		if ((individualIDs = request.getParameterValues("messageid")) == null)
			throw new ParameterException("Missing 'messageid' parameter.");
		
		if ((responseType = request.getParameter("responsetype")) == null)
			throw new ParameterException("Missing 'responsetype' parameter.");
		
		try {
			type = ResponseType.valueOf(responseType.toUpperCase());
		} catch (IllegalArgumentException e) {
			throw new ParameterException("Invalid 'responsetype' parameter provided.");
		}
		// End of Validation
		
		BigInteger mid = null;
		
		// Max array of length
		List<Message> messages = new ArrayList<Message>(individualIDs.length);
		
		for (String id: individualIDs) {
			try {
				mid = new BigInteger(id);
				Message message = MessageInputMapper.find(mid);
				messages.add(message);
			} catch (MapperException e) {
				Logger logger = (Logger)LoggerFactory.getLogger("application");
				logger.debug("Message with id {} was not found.", mid.toString());
				// ID was not found, ignore it, on to the next one
				continue;
			} catch (NumberFormatException e) {
				throw new ParameterException("Parameter 'messageid' is badly formatted.");
			}
		}
		
		DataOutputStream out = new DataOutputStream(response.getOutputStream());
		
		// Format response based on request type
		switch (type) {
		case XML:
			response.setContentType("text/xml");
			IOUtils.writeMessageListToXML(messages, out);
			break;
		case BIN:
			response.setContentType("application/octet-stream");
			IOUtils.writeMessageListToStream(messages, out);
			break;			
		case JSP:
			// TODO possible for jsp
			break;
		}

		response.setStatus(HttpServletResponse.SC_OK);
	}

}
