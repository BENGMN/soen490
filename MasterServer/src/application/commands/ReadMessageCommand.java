package application.commands;

import java.io.DataOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import exceptions.MapperException;
import exceptions.ParameterException;
import exceptions.UnrecognizedUserException;
import domain.message.Message;
import domain.message.mappers.MessageInputMapper;

public class ReadMessageCommand extends FrontCommand {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws MapperException, ParameterException, IOException, UnrecognizedUserException, NoSuchAlgorithmException, SQLException {
		// Get parameter of pipe ('|') separated message id values
		String separatedIDs = request.getParameter("messageid");
		// Split the ids
		String[] individualIDs = separatedIDs.split("|");
		
		// Max array of length
		List<Message> messages = new ArrayList<Message>(individualIDs.length);
		
		for (String id: individualIDs) {
			BigInteger mid = new BigInteger(id);
			try {
				Message message = MessageInputMapper.find(mid);
				messages.add(message);
			} catch (MapperException e) {
				// TODO Log that a message was not found
				// but continue
				continue;
			}	
		}
		
		// TODO write message list to response but not the way we are doing it below
		Message.writeListClient(messages, new DataOutputStream(response.getOutputStream()));
		response.setStatus(HttpServletResponse.SC_OK);
	}

}
