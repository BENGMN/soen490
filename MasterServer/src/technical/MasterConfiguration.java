package technical;

import java.util.List;

public class MasterConfiguration extends ServerConfiguration {
	double minLatitude;
	double maxLatitude;
	double minLongitude;
	double maxLongitude;
	
	MasterConfiguration parent;
	MasterConfiguration grandParent;
	List<MasterConfiguration> children;
	
	MasterConfiguration(String hostname, int port, double minLatitude, double maxLatitude, double minLongitude, double maxLongitude)
	{
		super(hostname, port);
		this.minLatitude = minLatitude;
		this.maxLatitude = minLatitude;
		this.minLongitude = minLongitude;
		this.maxLongitude = maxLongitude;
	}
	
	public EServerType getType()
	{
		return EServerType.SERVER_MASTER;
	}
	
	public boolean isResponsible(double longitude, double latitude)
	{
		boolean responsible = true;
		if (longitude >= minLongitude && longitude < maxLongitude &&
				latitude >= minLatitude && latitude < maxLatitude){
			for(MasterConfiguration child : children){
				if(longitude >= child.minLongitude && longitude < child.maxLongitude &&
						latitude >= child.minLatitude && latitude < child.maxLatitude){
					responsible = false;
					break;
				}
			}
		}
		return responsible;
	}
	
}
