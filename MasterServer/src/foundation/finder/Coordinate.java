package foundation.finder;

import technical.Location;

public class Coordinate implements Location {

	private double latitude, longitude;
	
	public Coordinate(double latitude, double longitude) {
		this.latitude = latitude;
		this.longitude = longitude;
	}
	
	@Override
	public double getLongitude() {
		return longitude;
	}

	@Override
	public void setLongitude(double longitude) {
		this.longitude = longitude;
		
	}

	@Override
	public double getLatitude() {
		return latitude;
	}

	@Override
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

}
