/**
 * SOEN 490
 * Capstone 2011
 * IUser interface for the User Domain Object and its proxy.
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

package domain.user;

import java.io.IOException;

import technical.IServerSendable;

public interface IUser extends IServerSendable {

	public long getUid();
	public String getPassword() throws IOException;
	public void setPassword(String password) throws IOException;
	public String getEmail() throws IOException;
	public void setEmail(String email) throws IOException;
	public UserType getType() throws IOException;
	public void setType(UserType type) throws IOException;
	public int getVersion() throws IOException;
	public void setVersion(int version) throws IOException;
}
