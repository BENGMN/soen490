package ericsson.thinClient.technical;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.util.List;

import org.apache.http.client.HttpClient;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.params.BasicHttpParams;

import android.net.http.AndroidHttpClient;

public class HttpInterface {
	private static final String clientName = "Ericsson Client"; 
	private static final String entrypointHostname = "localhost";
	private static final int entrypointPort = 8080;
	private HttpClient client;
	
	private static HttpInterface singleton = null;
	
	private HttpInterface()
	{
		client = AndroidHttpClient.newInstance(clientName);
	}
	
	static public HttpInterface getInstance()
	{
		if (singleton == null)
			singleton = new HttpInterface();
		return singleton;
	}
	
	public InputStream getMessageIDs(double longitude, double latitude, float speed) throws IOException
	{
		final String url = "http://" + entrypointHostname + ":" + entrypointPort + "/frontController/getmessageids";
		HttpGet request = new HttpGet(url);
		BasicHttpParams params = new BasicHttpParams();
		params.setDoubleParameter("longitude", longitude);
		params.setDoubleParameter("latitude", latitude);
		params.setDoubleParameter("speed", speed);
		request.setParams(params);
		
		HttpResponse response = client.execute(request);
		if (response.getStatusLine().getStatusCode() == 200)
			return response.getEntity().getContent();
		return null;
	}
	
	public InputStream getMessages(List<BigInteger> ids) throws IOException
	{
		final String url = "http://" + entrypointHostname + ":" + entrypointPort + "/frontController/getmessages";
		HttpGet request = new HttpGet(url);
		BasicHttpParams params = new BasicHttpParams();
		request.setParams(params);
		
		HttpResponse response = client.execute(request);
		if (response.getStatusLine().getStatusCode() == 200)
			return response.getEntity().getContent();
		return null;
	}
	
	public boolean uploadMessage(File file, double longitude, double latitude, float speed, String email) throws UnsupportedEncodingException, IOException
	{
		final String url = "http://" + entrypointHostname + ":" + entrypointPort + "/frontController/createmessage";
		HttpPut request = new HttpPut(url);
		
		MultipartEntity entity = new MultipartEntity();
		entity.addPart("bin", new FileBody(file, "bin"));	
		entity.addPart("longitude", new StringBody(String.valueOf(longitude)));
		entity.addPart("latitude", new StringBody(String.valueOf(latitude)));
		entity.addPart("speed", new StringBody(String.valueOf(speed)));
		entity.addPart("email", new StringBody(email));
		
		HttpResponse response = client.execute(request);
		return response.getStatusLine().getStatusCode() == 202;
	}
	
	public boolean upvoteMessage(BigInteger id) throws IOException
	{
		final String url = "http://" + entrypointHostname + ":" + entrypointPort + "/frontController/upvote";
		HttpPost request = new HttpPost(url);
		BasicHttpParams params = new BasicHttpParams();
		params.setParameter("mid", id);
		request.setParams(params);
		
		HttpResponse response = client.execute(request);
		return response.getStatusLine().getStatusCode() == 202;
	}
	
	public boolean downvoteMessage(BigInteger id) throws IOException
	{
		final String url = "http://" + entrypointHostname + ":" + entrypointPort + "/frontController/downvote";
		HttpPost request = new HttpPost(url);
		BasicHttpParams params = new BasicHttpParams();
		params.setParameter("mid", id);
		request.setParams(params);
		
		HttpResponse response = client.execute(request);
		return response.getStatusLine().getStatusCode() == 202;
	}
}

