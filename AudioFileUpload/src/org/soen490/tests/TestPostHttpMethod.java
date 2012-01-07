package org.soen490.tests;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.ContentBody;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.util.EntityUtils;

import junit.framework.TestCase;

public class TestPostHttpMethod extends TestCase{
	public void testUploadFile() {
		File audioFile = new File("C:\\Users\\Soto\\Desktop\\test recording.amr");
		
		String url = "http://localhost:8080/AudioFileUpload/capstone/upload";
		String charset = "UTF-8";
		
		// random values
		String latitude = "145";
		String longitude = "132";
		String speed = "0";
		
		
		String query;
		try {
			query = String.format("latitude=%s&longitude=%s&speed=%s", URLEncoder.encode(latitude, charset), URLEncoder.encode(longitude, charset), URLEncoder.encode(speed, charset));
		} catch (UnsupportedEncodingException e) {
			query = String.format("latitude=%s&longitude=%s&speed=%s", latitude, longitude, speed);
		}
		
		HttpClient httpClient = new DefaultHttpClient();
		httpClient.getParams().setParameter(CoreProtocolPNames.PROTOCOL_VERSION, HttpVersion.HTTP_1_1);
		
		HttpPost httpPost = new HttpPost(url + "?" + query);
		
		MultipartEntity mpEntity = new MultipartEntity();
		ContentBody cbFile = new FileBody(audioFile, "audio/AMR");
		mpEntity.addPart("audioFile", cbFile);
		
		httpPost.setEntity(mpEntity);
		
		HttpResponse response = null;
		try {
			response = httpClient.execute(httpPost);
			HttpEntity responseEntity = response.getEntity();
			System.out.println(response.getStatusLine());
			
			if(responseEntity != null) 
				System.out.println(EntityUtils.toString(responseEntity));
			
			if(responseEntity != null) {
				EntityUtils.consume(responseEntity);
			}
			
			httpClient.getConnectionManager().shutdown();
			
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
