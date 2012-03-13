/**
 * SOEN 490
 * Capstone 2011
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

package domain.user;


import java.math.BigInteger;

import exceptions.MapperException;

public interface IUser {

	public BigInteger getUid();
	public String getPassword() throws MapperException;
	public void setPassword(String password) throws MapperException;
	
	public String getEmail() throws MapperException;
	public void setEmail(String email) throws MapperException;
	
	public UserType getType() throws MapperException;
	public void setType(UserType type) throws MapperException;
	
	public int getVersion() throws MapperException;
	public void setVersion(int version) throws MapperException;
	
}
