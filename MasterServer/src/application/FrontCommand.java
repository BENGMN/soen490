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

package application;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import technical.UnrecognizedUserException;

abstract class FrontCommand {
	public boolean responsible(HttpServletRequest request, HttpServletResponse response) { return true; }
	abstract public void execute(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, UnrecognizedUserException;
}
