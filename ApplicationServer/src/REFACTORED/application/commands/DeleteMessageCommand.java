package REFACTORED.application.commands;

import java.io.IOException;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import REFACTORED.MapperException;
import REFACTORED.application.ParameterException;
import REFACTORED.application.UnrecognizedUserException;
import REFACTORED.domain.message.Message;
import REFACTORED.domain.message.mappers.MessageInputMapper;
import REFACTORED.domain.message.mappers.MessageOutputMapper;

public class DeleteMessageCommand extends FrontCommand {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws MapperException, ParameterException, IOException, UnrecognizedUserException, NoSuchAlgorithmException, SQLException {
		
		BigInteger mid = new BigInteger(request.getParameter("messageid"));
		
		// TODO make mapper throw mapper exception if mid doesn't exist
		// Throws MapperException if mid doesn't exist
		Message message = MessageInputMapper.find(mid);
		
		// TODO possible check for threading issues, delete returns 0 if no message was found
		MessageOutputMapper.delete(message);
		
		response.setStatus(HttpServletResponse.SC_OK);
	}

}
