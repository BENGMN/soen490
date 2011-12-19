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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

abstract class FrontCommand {
	abstract public boolean execute(HttpServletRequest request, HttpServletResponse response);
}
