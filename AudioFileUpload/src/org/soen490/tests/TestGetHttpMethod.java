package org.soen490.tests;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import org.apache.commons.io.IOUtils;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreProtocolPNames;


import junit.framework.TestCase;

public class TestGetHttpMethod extends TestCase {
	public void testDownloadGet() {
		String url = "http://localhost:8080/AudioFileUpload/capstone/download";
		String charset = "UTF-8";
		
		// The id of the audio message requested
		String messageID = "1";
		
		try {	
			String query = String.format("mid=%s", URLEncoder.encode(messageID, charset));
			
			HttpClient httpClient = new DefaultHttpClient();
			httpClient.getParams().setParameter(CoreProtocolPNames.PROTOCOL_VERSION, HttpVersion.HTTP_1_1);

			
			HttpGet httpGet = new HttpGet(url + "?" + query);
			HttpResponse response = httpClient.execute(httpGet);
			Header[] headers = response.getHeaders("Content-Disposition");
			
			String[] temp = headers[0].getValue().split("filename=");
			
			String fileName = temp[1].replace("\"", "");
						
			System.out.println(response.getStatusLine());
			
			InputStream in = response.getEntity().getContent();
			
			FileOutputStream fos = new FileOutputStream(new File("C:\\Users\\Soto\\Desktop\\" + fileName));
			
			byte[] buffer = new byte[4096];
			int length; 
			while((length = in.read(buffer)) > 0) {
				fos.write(buffer, 0, length);
			}
			
			
			//connection = new URL(url + "?" + query).openConnection();
			//connection.setRequestProperty("Accept-Charset", charset);
			
			//InputStream response = connection.getInputStream();
			
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
}
