package technical;

public class RegionalConfiguration extends ServerConfiguration {
	double minLatitude;
	double maxLatitude;
	double minLongitude;
	double maxLongitude;
	
	RegionalConfiguration(String hostname, int port, double minLatitude, double maxLatitude, double minLongitude, double maxLongitude)
	{
		super(hostname, port);
		this.minLatitude = minLatitude;
		this.maxLatitude = minLatitude;
		this.minLongitude = minLongitude;
		this.maxLongitude = maxLongitude;
	}
	
	public EServerType getType()
	{
		return EServerType.SERVER_REGIONAL;
	}
	
	public boolean isResponsible(double longitude, double latitude)
	{
		return (longitude >= minLongitude && longitude < maxLongitude &&
				latitude >= minLatitude && latitude < maxLatitude);
	}
	
}
