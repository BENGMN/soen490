/**
 * SOEN 490
 * Capstone 2011
 * Team members: 	
 * 			Sotirios Delimanolis
 * 			Filipe Martinho
 * 			Adam Harrison
 * 			Vahe Chahinian
 * 			Ben Crudo
 * 			Anthony Boyer
 * 
 * @author Capstone 490 Team Moving Target
 *
 */
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

import application.ServerParameters;
import application.response.IOUtils;
import application.response.ResponseType;

import ch.qos.logback.classic.Logger;

import exceptions.MapperException;
import exceptions.ParameterException;
import exceptions.UnrecognizedUserException;
import domain.message.Message;
import domain.message.mappers.MessageInputMapper;

/**
 * Command for retrieving actual messages. The client provides the messageids he wants. The application retrieves and serializes them in the response type requested.
 * Request parameters: 
 *  - messageid List of unique message ids
 *  - responsetype The type of response the client is expecting 
 *
 */
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
		int maxMessages = Integer.parseInt(ServerParameters.getUniqueInstance().get("maxMessages").getValue());
		int count = 0;
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
			
			// if we reach the maximum amount of messages permitted, break this loop, we've returned enough
			count++;
			if (count >= maxMessages) 
				break;
		}
		
		if (messages.size() == 0)
			throw new MapperException("None of the requested messages were found.");
		
		DataOutputStream out = null;
		
		// Format response based on request type
		switch (type) {
		case XML:
			out = new DataOutputStream(response.getOutputStream());
			response.setContentType("text/xml");
			IOUtils.writeMessageListToXML(messages, out);
			break;
		case BIN:
			out = new DataOutputStream(response.getOutputStream());
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
