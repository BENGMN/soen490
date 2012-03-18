/**
 * SOEN 490
 * Capstone 2011
 * Delete message command; allows a user to delete a message.
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

import java.math.BigInteger;
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
	public void execute(HttpServletRequest request, HttpServletResponse response) throws MapperException, ParameterException, UnrecognizedUserException, SQLException {
		String strId = request.getParameter("messageid");
		
		if ("".equals(strId))
			throw new ParameterException("Missing 'messageid' parameter.");
		
		BigInteger mid = null;
		
		try {
			mid = new BigInteger(strId);
		} catch (NumberFormatException e) {
			throw new ParameterException("Parameter 'messageid' is badly formatted, not a valid integer.");
		}
		
		// Throws MapperException if mid doesn't exist
		Message message = MessageInputMapper.find(mid);
		
		MessageOutputMapper.delete(message);
		
		// TODO possibly return success message
		response.setStatus(HttpServletResponse.SC_OK);
	}

}
