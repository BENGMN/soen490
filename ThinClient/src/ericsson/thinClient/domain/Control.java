package ericsson.thinClient.domain;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import ericsson.thinClient.technical.AndroidLocation;
import ericsson.thinClient.technical.AndroidRecorder;
import ericsson.thinClient.technical.HttpInterface;
import ericsson.thinClient.technical.AndroidPlayer;
import ericsson.thinClient.view.ThinClientActivity;

public class Control extends TimerTask implements AndroidPlayer.Listener {
	private static Control singleton = null;
	private static long recordingTime = 30 * 1000;
	public Timer recordingTimer;
	
	public interface Listener {
		public void updatePlayButton();
		public void updateRecordButton();
		public void updateVotingButtons();
	}
	
	private ArrayList<Listener> listeners;
	
	// We can have one recorded message stored while we're deciding what to do with it.
	public File recordedMessage;
	
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
		listeners = new ArrayList<Listener>();
	}
	
	public boolean isPlaying() {
		return status == EControlStatus.STATUS_PLAYING;
	}
	
	public boolean isPaused() {
		return status == EControlStatus.STATUS_PAUSED;
	}
	
	public boolean isRecording() {
		return recording;
	}
	
	private void startPlaying() throws IllegalArgumentException, IllegalStateException, FileNotFoundException, IOException	{
		status = EControlStatus.STATUS_PLAYING;
		if (selectedMessage == null)
			selectedMessage = MessagesCached.getInstance().getNextMessage(selectedMessage);
		AndroidPlayer.getInstance().play(ThinClientActivity.getInstance().openFileInput(selectedMessage.getFile().getAbsolutePath()));
		for (Listener listener : listeners)
			listener.updatePlayButton();
	}
	
	private void stopPlaying()
	{
		assert(status == EControlStatus.STATUS_PLAYING);
		AndroidPlayer.getInstance().stop();
		for (Listener listener : listeners)
			listener.updatePlayButton();
	}
	
	public void action() throws IllegalArgumentException, IllegalStateException, FileNotFoundException, IOException
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
		for (Listener listener : listeners)
			listener.updateRecordButton();
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
		for (Listener listener : listeners)
			listener.updateVotingButtons();
	}
	
	public void downvoteSelected() throws IOException
	{
		assert(!isRecording() && selectedMessage != null);
		HttpInterface.getInstance().upvoteMessage(selectedMessage.getMid());
		for (Listener listener : listeners)
			listener.updateVotingButtons();
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
		assert(recordedMessage != null);
		recordedMessage.delete();
		recordedMessage = null;
	}

	public void onStop() {
		status = EControlStatus.STATUS_PAUSED;
	}
	
	public void addListener(Listener listener)
	{
		listeners.add(listener);
	}
	
	public void removeListener(Listener listener)
	{
		listeners.remove(listener);
	}
}