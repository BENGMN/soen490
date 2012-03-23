/**
 * SOEN 490
 * Capstone 2011
 * Proxy for user.
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

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.sql.SQLException;

import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.Logger;

import domain.user.mappers.UserInputMapper;
import exceptions.MapperException;

public class UserProxy implements IUser {
	private BigInteger uid;
	private User realUser;
	
	public UserProxy(BigInteger uid) {
		this.uid = uid;
		realUser = null;
	}
	
	/**
	 * Load the actual object to which the proxy refers.
	 * If the local reference to the object being proxied is null
	 * call the input mapper to locate (or create) and return the object.
	 * @return The user object to which the proxy refers.
	 * @throws SQLException 
	 * @throws MapperException 
	 * @throws IOException
	 */
	private User getRealUser() throws MapperException {
		if (realUser == null) {
			try {
				realUser = UserInputMapper.find(uid);
			} catch (SQLException e) {
				Logger logger = (Logger) LoggerFactory.getLogger("application");
				logger.error("SQLException occurred when unfolding proxy: {}", e);
			}
		} 
		return realUser;
	}
	
	public String getEmail() throws MapperException  {
		return getRealUser().getEmail();
	}

	public String getPassword() throws MapperException  {
		return getRealUser().getPassword();
	}

	public UserType getType() throws MapperException {
		return getRealUser().getType();
	}

	public BigInteger getUid() {
		return uid;
	}

	public int getVersion() throws MapperException {
		return getRealUser().getVersion();
	}

	public void setEmail(String email) throws MapperException {
		getRealUser().setEmail(email);
	}

	public void setPassword(String password) throws MapperException {
		getRealUser().setPassword(password);
	}

	public void setType(UserType type) throws MapperException {
		getRealUser().setType(type);
	}

	public void setVersion(int version) throws MapperException {
		getRealUser().setVersion(version);
	}

	@Override
	public boolean equals(Object o) {
		// If the object is not null and is of the type User
		if ((o != null) && (o.getClass().equals(this.getClass()))) {
			// Test all of the particulars
				return (
						((UserProxy)o).getUid() == this.getUid()
						);
	        }
			return false; // if we made it here we failed above
	}
}
