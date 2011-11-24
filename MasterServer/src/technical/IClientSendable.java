package technical;

import java.io.DataOutputStream;
import java.io.IOException;


public interface IClientSendable {
	// For interchange with client; DataOutputStream because client may or may not use java.
	public void writeClient(DataOutputStream out) throws IOException;
}
