package Tests;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.msgpack.MessagePack;
import org.msgpack.unpacker.Unpacker;

public class CommandTests {
	private static final String HOST_NAME = "localhost";
	private static final String HOST_PORT = "8080";
	
	public static String testCreateUserCommand(String email, String password, String userType, String responseType) throws ClientProtocolException, IOException {
		HttpClient httpClient = new DefaultHttpClient();	
		HttpPost httpPost = new HttpPost("http://" + HOST_NAME + ":" + HOST_PORT + "/MasterServer/controller?command=createuser&email="
	                                  + email + "&password=" + password + "&usertype=" + userType + "&responsetype=" + responseType);
		HttpResponse response = httpClient.execute(httpPost);
        return response.getStatusLine().toString();
	}
	
	public static String testGetMessageIdsCommand(String latitude, String longitude, String speed, String responseType) throws IOException {
		HttpClient httpgetIdClient = new DefaultHttpClient();
		HttpGet httpGet = new HttpGet("http://" + HOST_NAME + ":" + HOST_PORT + "/MasterServer/controller?command=getmessageids&latitude="
                                     + latitude + "&longitude=" + longitude + "&speed=" + speed + "&responsetype=" + responseType);

		HttpResponse response = httpgetIdClient.execute(httpGet);
        return response.toString();
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
		HttpGet httpGet = new HttpGet("http://" + HOST_NAME + ":" + HOST_PORT + "/MasterServer/controller?command=upvote&userid=" + userId  + "&responseType="
                					 + responseType);

		HttpResponse response = client.execute(httpGet);
        return response.toString();
	}
}
