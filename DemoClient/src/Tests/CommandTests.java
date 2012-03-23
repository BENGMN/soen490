package Tests;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.msgpack.MessagePack;
import org.msgpack.unpacker.Unpacker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import exceptions.CorruptStreamException;

public class CommandTests {
	private static final String HOST_NAME = "localhost";
	private static final String HOST_PORT = "8080";
	
	public static String testCreateUserCommand(String email, String password, String userType, String responseType) throws ClientProtocolException, IOException {
		HttpClient httpClient = new DefaultHttpClient();	
		HttpPost httpPost = new HttpPost("http://" + HOST_NAME + ":" + HOST_PORT + "/MasterServer/controller?command=createuser&email="
	                                  + email + "&password=" + password + "&usertype=" + userType + "&responsetype=" + responseType);
		HttpResponse response = httpClient.execute(httpPost);
		InputStream in = response.getEntity().getContent();
		
		if(response.getStatusLine().getStatusCode() == 200) {	
			BigInteger mid = null;		
			MessagePack messagePack = new MessagePack();
			Unpacker unpacker = messagePack.createUnpacker(in);
			mid = new BigInteger(unpacker.readString());		
			return mid.toString();
		}
		else {
	        return response.toString();
		}
	}
	
	public static String testGetMessageIdsCommand(String latitude, String longitude, String speed, String responseType) throws IOException {
		HttpClient httpgetIdClient = new DefaultHttpClient();
		HttpGet httpGet = new HttpGet("http://" + HOST_NAME + ":" + HOST_PORT + "/MasterServer/controller?command=getmessageids&latitude="
                                     + latitude + "&longitude=" + longitude + "&speed=" + speed + "&responsetype=" + responseType);

		HttpResponse response = httpgetIdClient.execute(httpGet);
        return response.toString();
        
        InputStream in = response.getEntity().getContent();
		
		if(response.getStatusLine().getStatusCode() == 200) {	
			BigInteger mid = null;
			List<BigInteger> ids = null;		
			MessagePack messagePack = new MessagePack();
			Unpacker unpacker = messagePack.createUnpacker(in);			
			int numberOfIDs = unpacker.readInt();
			ids = new ArrayList<BigInteger>(numberOfIDs);		
			for (int i = 0; i < numberOfIDs; i++) {
				mid = new BigInteger(unpacker.readString());
				ids.add(mid);
			}
			return ids.toString();
		}
		else {
	        return response.toString();
		}
	}
	
	public static String testReadMessageCommand(String messageId, String responseType) throws IOException {
		HttpClient httpgetMessageClient = new DefaultHttpClient();
		HttpGet readMessage = new HttpGet("http://" + HOST_NAME + ":" + HOST_PORT + "/MasterServer/controller?command=readmessage&messageid="+ messageId + "&responsetype=" + responseType);		
		HttpResponse readMessageResponse = httpgetMessageClient.execute(readMessage);
        return readMessageResponse.getStatusLine().toString();
	}
	
	
	public static String testUpVoteMessageCommand(String messageId) throws IOException {
		HttpClient client = new DefaultHttpClient();
		HttpPost httpPost = new HttpPost("http://" + HOST_NAME + ":" + HOST_PORT + "/MasterServer/controller?command=upvote&messageid="
                                     + messageId);

		HttpResponse response = client.execute(httpPost);
        return response.toString();
	}
	
	public static String testDownVoteMessageCommand(String messageId) throws IOException {
		HttpClient client = new DefaultHttpClient();
		HttpPost httpPost = new HttpPost("http://" + HOST_NAME + ":" + HOST_PORT + "/MasterServer/controller?command=upvote&messageid="
                					 + messageId);

		HttpResponse response = client.execute(httpPost);
        return response.toString();
	}
	
	public static String testReadUserCommand(String userId, String responseType) throws ClientProtocolException, IOException {
		HttpClient client = new DefaultHttpClient();
		HttpPost httpPost = new HttpPost("http://" + HOST_NAME + ":" + HOST_PORT + "/MasterServer/controller?command=readuser&userid=" + userId  + "&responsetype="
                					 + responseType);

		HttpResponse response = client.execute(httpPost);
        return response.toString();
	}
	
	public static String testCreateMessage(File file, String latitude, String longitude, String speed, String email) throws IOException {
		HttpClient httpClient = new DefaultHttpClient();	
		HttpPost httpPost = new HttpPost("http://" + HOST_NAME + ":" + HOST_PORT + "/MasterServer/controller?command=createmessage&latitude="
	                                  + latitude + "&longitude=" + longitude + "&speed=" + speed + "&email=" + email);
		
		MultipartEntity entity = new MultipartEntity();
		entity.addPart("bin", new FileBody(file, "audio/amr"));	
		httpPost.setEntity(entity);
		HttpResponse response = httpClient.execute(httpPost);
		InputStream in = response.getEntity().getContent();

		if(response.getStatusLine().getStatusCode() == 200) {	
			BigInteger mid = null;		
			MessagePack messagePack = new MessagePack();
			Unpacker unpacker = messagePack.createUnpacker(in);
			mid = new BigInteger(unpacker.readString());		
			return mid.toString();
		}
		else {
	        return response.toString();
		}
	}
	
	public static String testDeleteMessage(String messageId) throws ClientProtocolException, IOException {
		HttpClient client = new DefaultHttpClient();
		HttpDelete httpDelete = new HttpDelete("http://" + HOST_NAME + ":" + HOST_PORT + "/MasterServer/controller?command=deletemessage&messageid="
                					 + messageId);

		HttpResponse response = client.execute(httpDelete);
        return response.toString();
	}
	
	
	public static String testDeleteUserCommand(String userId, String version, String responseType) throws ClientProtocolException, IOException {
		HttpClient client = new DefaultHttpClient();
		HttpDelete httpDelete = new HttpDelete("http://" + HOST_NAME + ":" + HOST_PORT + "/MasterServer/controller?command=deleteuser&userid=" + userId  + "&version=" + version + "&responsetype="
                					 + responseType);

		HttpResponse response = client.execute(httpDelete);
        return response.toString();
	}
	
	public static String testGetServerParams() throws ClientProtocolException, IOException {
		HttpClient client = new DefaultHttpClient();
		HttpGet httpGet = new HttpGet("http://" + HOST_NAME + ":" + HOST_PORT + "/MasterServer/controller?command=getserverparameters");

		HttpResponse response = client.execute(httpGet);
        return response.toString();
	}
		
}
