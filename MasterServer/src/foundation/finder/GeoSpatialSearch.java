/**
 * SOEN 490
 * Capstone 2011
 * Table Data Gateway for the User Domain Object
 * Team members: 	
 * 			Sotirios Delimanolis
 * 			Filipe Martinho
 * 			Adam Harrison
 * 			Vahe Chahinian
 * 			Ben Crudo
 * 			Anthony Boyer
 * 
 * @author Capstone 490 Team Moving Target
 *
 */

package foundation.finder;

import java.util.ArrayList;
import java.util.List;


public class GeoSpatialSearch {
	
	/**
	 * Get a rectangle of a specified radius around a certain point
	 * @param coordinates Coordinates in degrees the rectangle should be built around
	 * @param radius Specify the radius in Meters
	 * @return A linked list contain
	 */
	public static List<Coordinate> convertPointToRectangle(Coordinate coordinates, double radius) {
		// Convert the coordinates from degrees to radians
		double latitude_radians = Math.toRadians(coordinates.getLatitude());

		
		// Approximate the number of meters per latitude (Source: http://en.wikipedia.org/wiki/Latitude)
		// Earth's Radius, R, is equal to 6371 km or 3959 miles.
		// No higher accuracy is appropriate for R since higher precision results necessitate an ellipsoid model.
		// With this value for R the meridian length of 1 degree of latitude on the sphere is 111.2 km or 69 miles.
		final double metersPerLatitude = 111120.0;//110400.0;
		final double metersPerLongitude = Math.abs(Math.cos(latitude_radians)*metersPerLatitude);
		
		// Get the coordinates by adding and subtracting the half-size to the initial coordinates
		double lat1, lat2, lon1, lon2;
		lon1 = coordinates.getLongitude() - radius / metersPerLongitude;
		lon2 = coordinates.getLongitude() + radius / metersPerLongitude;
		lat1 = coordinates.getLatitude() - radius / metersPerLatitude;
		lat2 = coordinates.getLatitude() + radius / metersPerLatitude;
		
		// Create a new list to return  the results in
		List<Coordinate> rectangle = new ArrayList<Coordinate>(2);
		
		// Add the results to the list
		rectangle.add(new Coordinate(lat1, lon1));
		rectangle.add(new Coordinate(lat2, lon2));

		// return the list of coordinates that form the rectangle
		return rectangle;
	}
}
