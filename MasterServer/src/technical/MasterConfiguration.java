package technical;

import java.util.HashMap;

public class MasterConfiguration extends ServerConfiguration {

	static int defaultPort = 8080;
	
	HashMap<String, MasterConfiguration> masterServers;
	HashMap<String, RegionalConfiguration> regionalServers;
	HashMap<String, DelegationConfiguration> delegationServers;
	
	MasterConfiguration(String hostname) {
		super(hostname, defaultPort);
	}
	
	MasterConfiguration(String hostname, int port) {
		super(hostname, port);
	}

	public EServerType getType() {
		return EServerType.SERVER_MASTER;
	}

	public boolean isResponsible(double latitude, double longitude) {
		return true;
	}

}
