package Tests;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

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

public class CommandTests {
	private static final String HOST_NAME = "localhost";
	private static final String HOST_PORT = "8080";
	
	public static String testCreateUserCommand(String email, String password, String userType, String responseType) throws ClientProtocolException, IOException {
		HttpClient httpClient = new DefaultHttpClient();	
	    String uri = "http://" + HOST_NAME + ":" + HOST_PORT + "/MasterServer/controller?command=createuser&email="
                + email + "&password=" + password + "&usertype=" + userType + "&responsetype=" + responseType;
		System.out.println(uri);
		HttpPost httpPost = new HttpPost(uri);
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
	
	public static String testGetMessageIdsCommand(String latitude, String longitude, String speed, String responseType, String sortType) throws IOException {
		HttpClient httpgetIdClient = new DefaultHttpClient();
		String uri = "http://" + HOST_NAME + ":" + HOST_PORT + "/MasterServer/controller?command=getmessageids&latitude="
                + latitude + "&longitude=" + longitude + "&speed=" + speed + "&responsetype=" + responseType + "&sorttype=" + sortType;
		System.out.println(uri);
		HttpGet httpGet = new HttpGet(uri);
		HttpResponse response = httpgetIdClient.execute(httpGet);        
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
		String uri = "http://" + HOST_NAME + ":" + HOST_PORT + "/MasterServer/controller?command=readmessage&messageid="+ messageId + "&responsetype=" + responseType;
		System.out.println(uri);
		HttpGet readMessage = new HttpGet(uri);		
		HttpResponse readMessageResponse = httpgetMessageClient.execute(readMessage);
        return readMessageResponse.getStatusLine().toString();
	}
	
	
	public static String testUpvoteMessageCommand(String messageId) throws IOException {
		HttpClient client = new DefaultHttpClient();
		String uri = "http://" + HOST_NAME + ":" + HOST_PORT + "/MasterServer/controller?command=upvote&messageid=" + messageId;
		System.out.println(uri);
		HttpPost httpPost = new HttpPost(uri);
		HttpResponse response = client.execute(httpPost);
        return response.toString();
	}
	
	public static String testDownvoteMessageCommand(String messageId) throws IOException {
		HttpClient client = new DefaultHttpClient();
		String uri = "http://" + HOST_NAME + ":" + HOST_PORT + "/MasterServer/controller?command=upvote&messageid="+ messageId;
		System.out.println(uri);
		HttpPost httpPost = new HttpPost(uri);
		HttpResponse response = client.execute(httpPost);
        return response.toString();
	}
	
	public static String testReadUserCommand(String userId, String responseType) throws ClientProtocolException, IOException {
		HttpClient client = new DefaultHttpClient();
		String uri = "http://" + HOST_NAME + ":" + HOST_PORT + "/MasterServer/controller?command=readuser&userid=" + userId  + "&responsetype=" + responseType;
		System.out.println(uri);
		HttpPost httpPost = new HttpPost(uri);
		HttpResponse response = client.execute(httpPost);
        return response.toString();
	}
	
	public static String testCreateMessage(File file, String latitude, String longitude, String speed, String email) throws IOException {
		HttpClient httpClient = new DefaultHttpClient();
		String uri = "http://" + HOST_NAME + ":" + HOST_PORT + "/MasterServer/controller?command=createmessage&latitude="
                + latitude + "&longitude=" + longitude + "&speed=" + speed + "&email=" + email;
		HttpPost httpPost = new HttpPost(uri);
		System.out.println(uri);
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
		String uri = "http://" + HOST_NAME + ":" + HOST_PORT + "/MasterServer/controller?command=deletemessage&messageid=" + messageId;
		HttpDelete httpDelete = new HttpDelete(uri);
		System.out.println(uri);
		HttpResponse response = client.execute(httpDelete);
        return response.toString();
	}
	
	
	public static String testDeleteUserCommand(String userId, String version, String responseType) throws ClientProtocolException, IOException {
		HttpClient client = new DefaultHttpClient();
		String uri = "http://" + HOST_NAME + ":" + HOST_PORT + "/MasterServer/controller?command=deleteuser&userid=" + userId  + "&version=" + version + "&responsetype=" + responseType;
		System.out.println(uri);
		HttpDelete httpDelete = new HttpDelete(uri);
		HttpResponse response = client.execute(httpDelete);
        return response.toString();
	}
	
	public static String testGetServerParametersCommand() throws ClientProtocolException, IOException {
		HttpClient client = new DefaultHttpClient();
		String uri = "http://" + HOST_NAME + ":" + HOST_PORT + "/MasterServer/controller?command=getserverparameters";
		System.out.println(uri);
		HttpGet httpGet = new HttpGet(uri);
		HttpResponse response = client.execute(httpGet);
        return response.toString();
	}
	
	public static String testPingCommand() throws ClientProtocolException, IOException {
		HttpClient client = new DefaultHttpClient();
		String uri = "http://" + HOST_NAME + ":" + HOST_PORT + "/MasterServer/controller?command=ping";
		HttpGet httpGet = new HttpGet(uri);
		System.out.println(uri);
		HttpResponse response = client.execute(httpGet);
        return response.toString();		
	}

	public static String testUnsupportedCommand(String command) throws ClientProtocolException, IOException {
		HttpClient client = new DefaultHttpClient();
		String uri = "http://" + HOST_NAME + ":" + HOST_PORT + "/MasterServer/controller?command=" + command;
		System.out.println(uri);
		HttpGet httpGet = new HttpGet(uri);
		HttpResponse response = client.execute(httpGet);
        return response.toString();	
	}
	
	public static String testUpdateUserCommand(String uid, String password, String userType, String responseType, String version) throws ClientProtocolException, IOException {
		HttpClient client = new DefaultHttpClient();
		String uri = "http://" + HOST_NAME + ":" + HOST_PORT + "/MasterServer/controller?command=updateuser&password=" + password + 
                "&usertype=" + userType + "&responsetype=" + responseType + "&userid=" + uid + "&version=" + version;
		System.out.println(uri);
		HttpPost httpPost = new HttpPost(uri);
		HttpResponse response = client.execute(httpPost);
        return response.toString();
	}
}
