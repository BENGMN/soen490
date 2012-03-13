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

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;

public class FileTransfer {	
	//Values are for testing purposes
	private static final String LONGITUDE = "10";
	private static final String LATITUDE = "10";
	private static final String SPEED = "5";
	private static final String EMAIL = "test@test.com";
	private HttpClient httpClient;
	
	public FileTransfer( ) {
		httpClient = new DefaultHttpClient();		
	}

	
	public String uploadFile(File file) throws ClientProtocolException, IOException {
		HttpPut httpPut = new HttpPut("localhost:8080/MasterServer?command=createmessage&latitude="
	                                  + LATITUDE + "&longitude=" + LONGITUDE + "&speed=" + SPEED + "&email=" + EMAIL);
		
		MultipartEntity entity = new MultipartEntity();
		entity.addPart("bin", new FileBody(file));	
		entity.addPart("longitude", new StringBody(LONGITUDE));
		entity.addPart("latitude", new StringBody(LATITUDE));
		entity.addPart("speed", new StringBody(SPEED));
		entity.addPart("email", new StringBody(EMAIL));

		httpPut.setEntity(entity);
		HttpResponse response = httpClient.execute(httpPut);
		
		return response.getStatusLine().getReasonPhrase();
	}
	
	public Map<String, Message> downloadFiles() throws ClientProtocolException, IOException {
		
		//first we must get the message Ids
		HttpGet getIds = new HttpGet("localhost:8080/MasterServer?command=getmessageids&latitude="
                                     + LATITUDE + "&longitude=" + LONGITUDE + "&speed=" + SPEED);

		HttpResponse getMessageIdResponse = httpClient.execute(getIds);
		InputStream getMessageIdInputStream = getMessageIdResponse.getEntity().getContent();
		BufferedReader reader = new BufferedReader(new InputStreamReader(getMessageIdInputStream));
		String line = " ";
		StringBuilder messageIds = new StringBuilder();
		
		while((line = reader.readLine()) != null) {
			messageIds.append(line);
		}
		
		String ids = messageIds.toString();
		String[] idList = ids.split("\\|");
		// now that we have the message Ids we can retrieve the messages
		
		Map<String, Message> messages = new HashMap<String, Message>();
		HttpGet readMessage;
		HttpResponse readMessageResponse;
		InputStream inputStream;
		
		for(int i = 0; i < idList.length; i++) {
			readMessage = new HttpGet("localhost:8080/MasterServer?command=readmessage&readmessage="+ idList[i]);		
			readMessageResponse = httpClient.execute(readMessage);
			inputStream = readMessageResponse.getEntity().getContent();
			Message message = new Message(inputStream, String.valueOf(i), ".amr");
			messages.put(message.getMessage().getName(), message);
		}
		
		return messages;

	}
	
}
