/**
 * SOEN 490
 * Capstone 2011
 * Front command; simple interface shared by all front commands.
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

package REFACTORED.application.commands;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import REFACTORED.MapperException;
import REFACTORED.application.ParameterException;
import REFACTORED.application.UnrecognizedUserException;

public abstract class FrontCommand {
	abstract public void execute(HttpServletRequest request, HttpServletResponse response) throws MapperException, ParameterException, IOException, UnrecognizedUserException, NoSuchAlgorithmException, SQLException;
}
