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

package tests.foundation;

import java.text.DecimalFormat;
import java.util.List;

import technical.Coordinate;
import technical.GeoSpatialSearch;
import junit.framework.TestCase;


/**	
 * Location 	Latitude / Longitude
 * Montreal 	45.523, -73.556
   TO:
 * New York	40.726, -74.004 (533 Km)
  	Lat: 40.72638372930166
	Lon: -80.40221689356889
	Lat: 50.31961627069835
	Lon: -66.7097831064311
 * 
 * Paris 48.8609, 2.351 (5521 Km)
 	Lat: -4.162025197984157
	Lon: -144.47150369492277
	Lat: 95.20802519798417
	Lon: -2.640496305077235
 */


public class GeoSpatialSearchTest extends TestCase {

	private Coordinate coordinates = new Coordinate(45.523, -73.556);
	private double radius_meters = 533000; // Distance to NY
	
	public void testConvertPointToRectangle() {
		List<Coordinate> rectangle = GeoSpatialSearch.convertPointToRectangle(coordinates, radius_meters);
		for (Coordinate co : rectangle) {
			System.out.println("Lat: "+co.getLatitude()+"\nLon: "+co.getLongitude());
		}
		DecimalFormat threeZeroes = new DecimalFormat("#.###");
		assertEquals(Double.valueOf(threeZeroes.format(rectangle.get(0).getLatitude())), 40.726);		
	}
}
