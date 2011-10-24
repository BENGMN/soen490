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

package domain;

import domain.User.UserType;

public interface IUser {

	public long getUid();
	public void setUid(long uid);
	public String getPassword();
	public void setPassword(String password);
	public String getEmail();
	public void setEmail(String email);
	public UserType getType();
	public void setType(UserType type);
	public int getVersion();
	public void setVersion(int version);
}
