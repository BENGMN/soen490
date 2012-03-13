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
import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.msgpack.MessagePack;
import org.msgpack.unpacker.Unpacker;

public class FileTransfer {	
	//Values are for testing purposes
	private static final String LONGITUDE = "45.546050";
	private static final String LATITUDE = "-73.679810";
	private static final String SPEED = "10000000000";
	private static final String EMAIL = "test@test.com";
	private static final String HOST_NAME = "localhost";
	private static final String HOST_PORT = "8080";

	public int uploadFile(File file) throws IOException {
		HttpClient httpClient = new DefaultHttpClient();	
		HttpPost httpPost = new HttpPost("http://" + HOST_NAME + ":" + HOST_PORT + "/MasterServer/controller?command=createmessage&latitude="
	                                  + LATITUDE + "&longitude=" + LONGITUDE + "&speed=" + SPEED + "&email=" + EMAIL);
		
		MultipartEntity entity = new MultipartEntity();
		entity.addPart("bin", new FileBody(file, "audio/amr"));	
		httpPost.setEntity(entity);
		System.out.println(entity.getContentType());
		HttpResponse response = httpClient.execute(httpPost);	
		return response.getStatusLine().getStatusCode();
	}
	
	public Map<String, Message> downloadFiles() throws ClientProtocolException, IOException {
		HttpClient httpgetIdClient = new DefaultHttpClient();
		//first we must get the message Ids
		HttpGet getIds = new HttpGet("http://" + HOST_NAME + ":" + HOST_PORT + "/MasterServer/controller?command=getmessageids&latitude="
                                     + LATITUDE + "&longitude=" + LONGITUDE + "&speed=" + SPEED);

		HttpResponse getMessageIdResponse = httpgetIdClient.execute(getIds);
		System.out.println(getMessageIdResponse.toString());

		InputStream getMessageIdInputStream = getMessageIdResponse.getEntity().getContent();
		
		Unpacker unpacker = (new MessagePack()).createUnpacker(getMessageIdInputStream);
		int size = unpacker.readInt();
		List<String> messageIdList = new LinkedList<String>();
		for(int i = 0; i < size; i++) {
			String id = unpacker.readString();
			messageIdList.add(id);
		}
	 
		System.out.println(messageIdList);
		
	//	BufferedReader reader = new BufferedReader(new InputStreamReader(getMessageIdInputStream));
	//	String line = " ";
	//	StringBuilder messageIds = new StringBuilder();
		
		
	//	while((line = reader.readLine()) != null) {
	//		messageIds.append(line);
	//	}
		
	//	System.out.println("IDS: " +  messageIds.toString());
      //  System.out.println("Content Length: " + getMessageIdResponse.getEntity().getContentLength());
		
	//	String ids = messageIds.toString();
	//	String[] idList = ids.split("\\|");
	//	for(int i = 0; i< idList.length; i++) {
			//System.out.println("ID" + i + ": " + idList[i]);
	//	}
		// now that we have the message Ids we can retrieve the messages	
		Map<String, Message> messages = new HashMap<String, Message>();
		HttpGet readMessage;
		HttpResponse readMessageResponse;
		InputStream inputStream;
/*
		for(int i = 0; i < idList.length; i++) {
			HttpClient httpgetMessageClient = new DefaultHttpClient();
			readMessage = new HttpGet("http://" + HOST_NAME + ":" + HOST_PORT + "/MasterServer/controller?command=readmessage&readmessage="+ idList[i]);		
			readMessageResponse = httpgetMessageClient.execute(readMessage);
			inputStream = readMessageResponse.getEntity().getContent();
			Message message = Message.createMessage(inputStream, String.valueOf(i), ".amr");
			messages.put(message.getMessage().getName(), message);
		}
		*/
		return messages;

	}
	
}
