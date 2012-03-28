package ericsson.thinClient.technical;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import ericsson.thinClient.view.ThinClientActivity;
import android.content.Context;
import android.media.MediaRecorder;

public class AndroidRecorder {
	static AndroidRecorder singleton = null;
	final static float maxTime = 30.0f;
	
	private MediaRecorder recorder;
	private File recordingFile;
	private boolean recording;
	
	public static AndroidRecorder getInstance()
	{
		if (singleton == null)
			singleton = new AndroidRecorder();
		return singleton;
	}
	
	private AndroidRecorder()
	{
		recorder = new MediaRecorder();
		recordingFile = null;
		recording = false;
	}
	
	public void start() throws IllegalStateException, IOException
	{	
		//File outputDir = ThinClientActivity.getInstance().getCacheDir();
		//recordingFile = File.createTempFile("ericssonMessage", ".amr", outputDir);
		recorder.reset();
		FileOutputStream stream = ThinClientActivity.getInstance().openFileOutput("ericssonMessage.amr", Context.MODE_PRIVATE);
		try {
			recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
		}
		catch (RuntimeException e) {
			throw e;
		}
		recorder.setOutputFormat(MediaRecorder.OutputFormat.RAW_AMR);
		recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
		recorder.setOutputFile(stream.getFD());
		recorder.prepare();
		recorder.start();
		recording = true;
	}
	
	public File stop() throws IOException
	{
		if (recording) {
			recorder.stop();
			return recordingFile;
		}
		return null;
	}
}
