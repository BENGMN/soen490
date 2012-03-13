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
		File outputDir = ThinClientActivity.getInstance().getCacheDir();
		recordingFile = File.createTempFile("ericssonMessage", "amr", outputDir);
		FileOutputStream stream = ThinClientActivity.getInstance().openFileOutput(recordingFile.getAbsolutePath(), Context.MODE_PRIVATE);
		recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
		recorder.setOutputFormat(MediaRecorder.OutputFormat.AMR_WB);
		recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_WB);
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
