package technical;

import java.util.HashMap;

public class DelegationConfiguration extends ServerConfiguration {

	HashMap<String, MasterConfiguration> masterServers;
	HashMap<String, DelegationConfiguration> delegationServers;

	DelegationConfiguration(String hostname) {
		super(hostname, defaultPort);
	}

	DelegationConfiguration(String hostname, int port) {
		super(hostname, port);
	}

	public EServerType getType() {
		return EServerType.SERVER_DELEGATION;
	}

	public boolean isResponsible(double latitude, double longitude) {
		return false;
	}

}
