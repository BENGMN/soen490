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

public class UserProxy implements IUser {
	private long uid;
	private User realUser;
	
	public UserProxy(long uid) {
		this.uid = uid;
		realUser = null;
	}
	
	// TODO: This method does not seem to load an object into the identity map
	// once it has been loaded from the input Mapper
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

	@Override
	public void readServer(DataInputStream in) throws IOException {
		getRealUser().readServer(in);
	}
}
