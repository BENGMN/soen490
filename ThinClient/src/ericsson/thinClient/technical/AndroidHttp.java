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
import org.apache.http.conn.HttpHostConnectException;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.params.BasicHttpParams;

import android.net.http.AndroidHttpClient;

public class AndroidHttp {
	private static final String clientName = "Ericsson Client"; 
	public String entrypointHostname;
	public int entrypointPort;
	private HttpClient client;
	
	private static AndroidHttp singleton = null;
	
	private AndroidHttp()
	{
		entrypointHostname = "localhost";
		entrypointPort = 80;
		client = AndroidHttpClient.newInstance(clientName);
	}
	
	static public AndroidHttp getInstance()
	{
		if (singleton == null)
			singleton = new AndroidHttp();
		return singleton;
	}
	
	public InputStream getMessageIDs(double longitude, double latitude, float speed, String sorttype) throws IOException
	{
		final String url = "http://" + entrypointHostname + ":" + entrypointPort + "/controller?command=getmessageids";
		HttpGet request = new HttpGet(url);
		BasicHttpParams params = new BasicHttpParams();
		params.setDoubleParameter("longitude", longitude);
		params.setDoubleParameter("latitude", latitude);
		params.setDoubleParameter("speed", speed);
		params.setParameter("sorttype", sorttype);
		request.setParams(params);
		
		HttpResponse response = null;
		try {
			response = client.execute(request);
		}
		catch (HttpHostConnectException e) {
			AndroidLogging.getInstance().error("HTTP Problem: " + e);
			return null;
		}
		if (response.getStatusLine().getStatusCode() == 200)
			return response.getEntity().getContent();
		AndroidLogging.getInstance().error("HTTP Problem: Got errror code " + response.getStatusLine().getStatusCode());
		return null;
	}
	
	public InputStream getMessages(List<BigInteger> ids) throws IOException
	{
		final String url = "http://" + entrypointHostname + ":" + entrypointPort + "/controller?command=readmessage";
		HttpGet request = new HttpGet(url);
		BasicHttpParams params = new BasicHttpParams();
		params.setParameter("messageid", ids);
		request.setParams(params);
		
		HttpResponse response = null;
		try {
			response = client.execute(request);
		}
		catch (HttpHostConnectException e) {
			AndroidLogging.getInstance().error("HTTP Problem: " + e);
			return null;
		}
		if (response.getStatusLine().getStatusCode() == 200)
			return response.getEntity().getContent();
		AndroidLogging.getInstance().error("HTTP Problem: Got errror code " + response.getStatusLine().getStatusCode());
		return null;
	}
	
	public boolean uploadMessage(File file, double longitude, double latitude, float speed, String email) throws UnsupportedEncodingException, IOException
	{
		final String url = "http://" + entrypointHostname + ":" + entrypointPort + "/controller?command=createmessage";
		HttpPost request = new HttpPost(url);
		
		MultipartEntity entity = new MultipartEntity();
		entity.addPart("bin", new FileBody(file, "bin"));	
		entity.addPart("longitude", new StringBody(String.valueOf(longitude)));
		entity.addPart("latitude", new StringBody(String.valueOf(latitude)));
		entity.addPart("speed", new StringBody(String.valueOf(speed)));
		entity.addPart("email", new StringBody(email));
		
		HttpResponse response = null;
		try {
			response = client.execute(request);
		}
		catch (HttpHostConnectException e) {
			AndroidLogging.getInstance().error("HTTP Problem: " + e);
			return false;
		}
		if (response.getStatusLine().getStatusCode() == 200)
			return true;
		AndroidLogging.getInstance().error("HTTP Problem: Got errror code " + response.getStatusLine().getStatusCode());
		return false;
	}
	
	public boolean upvoteMessage(BigInteger id) throws IOException
	{
		final String url = "http://" + entrypointHostname + ":" + entrypointPort + "/controller?command=upvote";
		HttpPost request = new HttpPost(url);
		BasicHttpParams params = new BasicHttpParams();
		params.setParameter("messageid", id);
		request.setParams(params);
		
		HttpResponse response = null;
		try {
			response = client.execute(request);
		}
		catch (HttpHostConnectException e) {
			AndroidLogging.getInstance().error("HTTP Problem: " + e);
			return false;
		}
		if (response.getStatusLine().getStatusCode() == 200)
			return true;
		AndroidLogging.getInstance().error("HTTP Problem: Got errror code " + response.getStatusLine().getStatusCode());
		return false;
	}
	
	public boolean downvoteMessage(BigInteger id) throws IOException
	{
		final String url = "http://" + entrypointHostname + ":" + entrypointPort + "/controller?command=upvote";
		HttpPost request = new HttpPost(url);
		BasicHttpParams params = new BasicHttpParams();
		params.setParameter("messageid", id);
		request.setParams(params);
		
		HttpResponse response = null;
		try {
			response = client.execute(request);
		}
		catch (HttpHostConnectException e) {
			AndroidLogging.getInstance().error("HTTP Problem: " + e);
			return false;
		}
		if (response.getStatusLine().getStatusCode() == 200)
			return true;
		AndroidLogging.getInstance().error("HTTP Problem: Got errror code " + response.getStatusLine().getStatusCode());
		return false;
	}
}

