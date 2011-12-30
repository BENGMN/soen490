package technical;

public class MasterConfiguration extends ServerConfiguration {

	static int defaultPort = 8080;
	
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
