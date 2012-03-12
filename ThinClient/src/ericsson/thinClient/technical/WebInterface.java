package ericsson.thinClient.technical;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.client.methods.HttpGet;

import ericsson.thinClient.domain.Message;

import android.net.http.AndroidHttpClient;

public class WebInterface {
	private static WebInterface singleton = null;
	private static final String clientName = "Ericsson Client"; 
	private static final String entrypointHostname = "localhost";
	private static final int entrypointPort = 80;
	private AndroidHttpClient client;
	
	private WebInterface()
	{
		client = AndroidHttpClient.newInstance(clientName);
	}
	
	public static WebInterface getInstance()
	{
		if (singleton == null)
			singleton = new WebInterface();
		return singleton;
	}
	
	public List<BigInteger> getMessageIDs()
	{
		return new ArrayList<BigInteger>();
	}
	
	public byte[] getMessages(List<BigInteger> messageIDs)
	{
		HttpGet getRequest = new HttpGet();
		return null;
	}
}
