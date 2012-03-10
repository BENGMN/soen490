package application.commands;

import java.io.IOException;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import exceptions.MapperException;
import exceptions.ParameterException;
import exceptions.UnrecognizedUserException;
import domain.message.Message;
import domain.message.mappers.MessageInputMapper;
import domain.message.mappers.MessageOutputMapper;

public class DeleteMessageCommand extends FrontCommand {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws MapperException, ParameterException, IOException, UnrecognizedUserException, NoSuchAlgorithmException, SQLException {
		
		BigInteger mid = new BigInteger(request.getParameter("messageid"));
		
		// Throws MapperException if mid doesn't exist
		Message message = MessageInputMapper.find(mid);
		
		// TODO possible check for threading issues, delete returns 0 if no message was found
		MessageOutputMapper.delete(message);
		
		response.setStatus(HttpServletResponse.SC_OK);
	}

}
