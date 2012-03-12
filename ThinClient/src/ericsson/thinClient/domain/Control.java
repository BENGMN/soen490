package ericsson.thinClient.domain;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Timer;
import java.util.TimerTask;

import ericsson.thinClient.technical.AndroidLocation;
import ericsson.thinClient.technical.AndroidRecorder;
import ericsson.thinClient.technical.HttpInterface;

public class Control extends TimerTask {
	private static Control singleton = null;
	private static long recordingTime = 30 * 1000;
	public Timer recordingTimer;
	
	// We can have one recorded message stored while we're deciding what to do with it.
	public byte[] recordedMessage;
	
	public static Control getInstance()
	{
		if (singleton == null)
			singleton = new Control();
		return singleton;
	}
	
	enum EControlStatus {
		STATUS_PLAYING,
		STATUS_PAUSED
	}
	
	boolean recording;
	Message selectedMessage;
	EControlStatus status;
	
	private Control() {
		status = EControlStatus.STATUS_PAUSED;
		recording = false;
		selectedMessage = null;
		recordingTimer = new Timer();
	}
	
	public boolean isPlaying()
	{
		return status == EControlStatus.STATUS_PLAYING;
	}
	
	public boolean isPaused()
	{
		return status == EControlStatus.STATUS_PAUSED;
	}
	
	public boolean isRecording()
	{
		return recording;
	}
	
	private void startPlaying()
	{
		status = EControlStatus.STATUS_PLAYING;
	}
	
	private void stopPlaying()
	{
		status = EControlStatus.STATUS_PAUSED;
	}
	
	public void action()
	{
		if (isPlaying())
			stopPlaying();
		else if (isPaused())
			startPlaying();
	}
	
	public void startRecording() throws IllegalStateException, IOException
	{
		assert(!recording);
		recording = true;
		recordingTimer.schedule(this, recordingTime);
		AndroidRecorder.getInstance().start();
	}
	
	// This method is our cutoff for the recording.
	public void run()
	{
		try {
			stopRecording();
		}
		catch (Exception e) {
			
		}
	}
	
	public void stopRecording() throws IOException
	{
		assert(recording);
		recording = false;
		recordingTimer.cancel();
		recordedMessage = AndroidRecorder.getInstance().stop();
	}
	
	public void record() throws IllegalStateException, IOException
	{
		if (isRecording())
			startRecording();
		else
			stopRecording();
	}
	
	public void prev() throws IOException
	{
		assert(!isRecording() && selectedMessage != null);
		selectedMessage = MessagesCached.getInstance().getPrevMessage(selectedMessage);
	}
	
	public void next() throws IOException
	{
		assert(!isRecording() && selectedMessage != null);
		selectedMessage = MessagesCached.getInstance().getNextMessage(selectedMessage);
	}
	
	public void upvoteSelected() throws IOException
	{
		assert(!isRecording() && selectedMessage != null);
		HttpInterface.getInstance().downvoteMessage(selectedMessage.getMid());
	}
	
	public void downvoteSelected() throws IOException
	{
		assert(!isRecording() && selectedMessage != null);
		HttpInterface.getInstance().upvoteMessage(selectedMessage.getMid());
	}
	
	public void uploadRecording() throws UnsupportedEncodingException, IOException
	{
		assert(recordedMessage != null);
		HttpInterface.getInstance().uploadMessage(recordedMessage,
				AndroidLocation.getInstance().getLongitude(),
				AndroidLocation.getInstance().getLatitude(),
				AndroidLocation.getInstance().getSpeed(),
				User.getLocalUser().getEmail());
	}
	
	public void discardRecording()
	{
		recordedMessage = null;
	}
}
