package ericsson.thinClient.technical;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import ericsson.thinClient.view.ThinClientActivity;
import android.content.Context;
import android.media.MediaRecorder;

public class AndroidRecorder {
	static AndroidRecorder singleton = null;
	static String temporaryPath;
	final static float maxTime = 30.0f;
	
	MediaRecorder recorder;
	
	public static AndroidRecorder getInstance()
	{
		if (singleton == null)
			singleton = new AndroidRecorder();
		return singleton;
	}
	
	private AndroidRecorder()
	{
		recorder = null;
		File cache = ThinClientActivity.getInstance().getCacheDir();
		temporaryPath = cache.getAbsolutePath() + "/recording.amr";
	}
	
	public void start() throws IllegalStateException, IOException
	{	
		FileOutputStream stream = ThinClientActivity.getInstance().openFileOutput(temporaryPath, Context.MODE_PRIVATE);
		recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
		recorder.setOutputFormat(MediaRecorder.OutputFormat.AMR_WB);
		recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_WB);
		recorder.setOutputFile(stream.getFD());
		recorder.prepare();
		recorder.start();		
	}
	
	public byte[] stop() throws IOException
	{
		recorder.stop();
		recorder.release();
		File file = new File(temporaryPath);
		FileInputStream stream = ThinClientActivity.getInstance().openFileInput(temporaryPath);
		long size = file.length();
		if (size <= 0)
			return null;
		byte[] bytes = new byte[(int)size];
		stream.read(bytes);
		stream.close();
		file.delete();
		return bytes;
	}
}
