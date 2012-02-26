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

import domain.user.mappers.UserInputMapper;

public class UserProxy implements IUser {
	private long uid;
	private User realUser;
	
	public UserProxy(long uid) {
		this.uid = uid;
		realUser = null;
	}
	
	/**
	 * Load the actual object to which the proxy refers.
	 * If the local reference to the object being proxied is null
	 * call the input mapper to locate (or create) and return the object.
	 * @return The user object to which the proxy refers.
	 * @throws IOException
	 */
	private User getRealUser() throws IOException	{
		if (realUser == null) {
			realUser = UserInputMapper.find(uid);
			if (realUser == null)
				throw new IOException("Can't read in user of id " + uid);
		}
		return realUser;
	}
	
	public String getEmail() throws IOException {
		return getRealUser().getEmail();
	}

	public String getPassword() throws IOException {
		return getRealUser().getPassword();
	}

	public UserType getType() throws IOException {
		return getRealUser().getType();
	}

	public long getUid() {
		return uid;
	}

	public int getVersion() throws IOException {
		return getRealUser().getVersion();
	}

	public void setEmail(String email) throws IOException {
		getRealUser().setEmail(email);
	}

	public void setPassword(String password) throws IOException {
		getRealUser().setPassword(password);
	}

	public void setType(UserType type) throws IOException {
		getRealUser().setType(type);
	}

	public void setVersion(int version) throws IOException {
		getRealUser().setVersion(version);
	}

	public void writeServer(DataOutputStream out) throws IOException {
		getRealUser().writeServer(out);
	}

	public void readServer(DataInputStream in) throws IOException {
		getRealUser().readServer(in);
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
