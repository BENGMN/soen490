/**
 * SOEN 490
 * Capstone 2011
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

import java.io.File;
import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;

public class FileTransfer {

	private static final String URL = " ";
	private HttpClient httpClient;
	
	public FileTransfer( ) {
		httpClient = new DefaultHttpClient();		
	}
	
	public String uploadFile(File file) throws ClientProtocolException, IOException {
		HttpPut httpPut = new HttpPut(URL);
		
		MultipartEntity entity = new MultipartEntity();
		entity.addPart("bin", new FileBody(file));	
		entity.addPart("longitude", new StringBody("10"));
		entity.addPart("latitude", new StringBody("10"));
		entity.addPart("speed", new StringBody("15"));
		entity.addPart("email", new StringBody("test@test.com"));

		httpPut.setEntity(entity);
		HttpResponse response = httpClient.execute(httpPut);
		
		return response.getStatusLine().getReasonPhrase();
	}
	
	public void downloadFIle() {
	
	}
	
}
