/**
 * SOEN 490
 * Capstone 2011
 * Test for GetMessagesCommand.
 * Team members: 	Sotirios Delimanolis
 * 			Filipe Martinho
 * 			Adam Harrison
 * 			Vahe Chahinian
 * 			Ben Crudo
 * 			Anthony Boyer
 * 
 * @author Capstone 490 Team Moving Target
 *
 */


package tests;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import junit.framework.TestCase;

public class GetMessagesCommandTest extends TestCase {
	public void testGetMessage()
	{
		/*String address = "http://localhost:8080/FrontController?command=GetMessages";
		try {
			URL testURL = new URL(address);
			HttpURLConnection testConnection = (HttpURLConnection)testURL.openConnection();
			testConnection.setRequestMethod("GET");
			testConnection.setDoOutput(true);
			testConnection.setReadTimeout(10000);
			testConnection.connect();
			BufferedReader testReader = new BufferedReader(new InputStreamReader(testConnection.getInputStream()));
			
		}
		catch (Exception E) {
			fail();
		}*/
	}
}
