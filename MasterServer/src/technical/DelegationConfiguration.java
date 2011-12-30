package technical;

public class DelegationConfiguration extends ServerConfiguration {

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
