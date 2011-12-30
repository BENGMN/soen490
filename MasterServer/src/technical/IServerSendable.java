package technical;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public interface IServerSendable {
	// Protocol for interchange between internal servers.
	public void writeServer(DataOutputStream out) throws IOException;
	public void readServer(DataInputStream in) throws IOException;
}