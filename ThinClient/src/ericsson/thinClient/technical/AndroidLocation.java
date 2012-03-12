package ericsson.thinClient.technical;

import ericsson.thinClient.view.ThinClientActivity;
import android.content.Context;
import android.location.LocationManager;
import android.location.GpsStatus;

public class AndroidLocation implements GpsStatus.Listener {

	static AndroidLocation singleton = null;
	
	LocationManager locationManager;
	
	public static AndroidLocation getInstance()
	{
		if (singleton == null)
			singleton = new AndroidLocation();
		return singleton;
	}
	
	private AndroidLocation()
	{
		locationManager = (LocationManager)ThinClientActivity.getInstance().getSystemService(Context.LOCATION_SERVICE);
	}
	
	public float getSpeed()
	{
		return 1.0f;
	}
	
	public double getLongitude()
	{
		return 0.0;
	}
	
	public double getLatitude()
	{
		return 0.0;
	}
	
	public void onGpsStatusChanged(int event)
	{
		
	}
}
