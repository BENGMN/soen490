/**
 * SOEN 490
 * Capstone 2011
 * Rate message command; allows a user to thumbs up/thumbs down a command.
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

/**
 * Command for downvoting a message. It finds the message and decrements its rating, then updates it in the database.
 *
 */
public class DownvoteMessageCommand extends FrontCommand {

	public void execute(HttpServletRequest request, HttpServletResponse response) throws MapperException, ParameterException, IOException, UnrecognizedUserException, SQLException {
		String strId;
		
		// Get message id from request object
		if ((strId = request.getParameter("messageid")) == null)
			throw new ParameterException("Missing 'messageid' parameter.");
		
		BigInteger mid = null;
		
		try {
			mid = new BigInteger(strId);
		} catch (NumberFormatException e) {
			throw new ParameterException("Parameter 'messageid' is badly formatted.");
		}
		
		// get the requested message
		// the input mapper will either pull or put the message in the identity map
		// so we are always affecting the same message
		Message message = MessageInputMapper.find(mid);
		
		// decrement the message user rating
		synchronized(message) {
			message.setUserRating(message.getUserRating() - 1);
		}
		
		// update the message in the database
		MessageOutputMapper.update(message);
		
		// TODO possibly return success message
		response.setStatus(HttpServletResponse.SC_OK);
	}
}
