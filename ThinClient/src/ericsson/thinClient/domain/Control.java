package ericsson.thinClient.domain;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Timer;
import java.util.TimerTask;

import ericsson.thinClient.technical.AndroidLocation;
import ericsson.thinClient.technical.AndroidRecorder;
import ericsson.thinClient.technical.HttpInterface;
import ericsson.thinClient.technical.AndroidPlayer;
import ericsson.thinClient.view.ThinClientActivity;

// Apparently actual assertions don't work for some strange reason.
// So we have to use the junit ones.
import static junit.framework.Assert.*;

public class Control extends TimerTask implements AndroidPlayer.Listener {
	private static Control singleton = null;
	private static long recordingTime = 30 * 1000;
	private Timer recordingTimer;
	
	// We can have one recorded message stored while we're deciding what to do with it.
	private File recordedMessage;
	
	public static Control getInstance()
	{
		if (singleton == null)
			singleton = new Control();
		return singleton;
	}
	
	enum EControlStatus {
		STATUS_PLAYING,
		STATUS_PAUSED,
		STATUS_RECORDING
	}
	
	private Message selectedMessage;
	private EControlStatus status;
	
	private Control() {
		status = EControlStatus.STATUS_PAUSED;
		selectedMessage = null;
		recordingTimer = new Timer();
	}
	
	public boolean isPlaying() {
		return status == EControlStatus.STATUS_PLAYING;
	}
	
	public boolean isPaused() {
		return status == EControlStatus.STATUS_PAUSED;
	}
	
	public boolean isRecording() {
		return status == EControlStatus.STATUS_RECORDING;
	}
	
	public Message getSelectedMessage() {
		return selectedMessage;
	}
	
	private void startPlaying()	{
		assertTrue(canStartPlaying());
		status = EControlStatus.STATUS_PLAYING;
		try {
			if (selectedMessage == null)
				selectedMessage = MessagesCached.getInstance().getNextMessage(selectedMessage);
			AndroidPlayer.getInstance().play(ThinClientActivity.getInstance().openFileInput(selectedMessage.getFile().getAbsolutePath()));
		}
		catch (IOException e) {
			e.printStackTrace();
			stopPlaying();
		}
			
	}
	
	private void stopPlaying()
	{
		assertTrue(canStopPlaying());
		AndroidPlayer.getInstance().stop();
		status = EControlStatus.STATUS_PAUSED;
	}
	
	public void action()
	{
		if (isPlaying())
			stopPlaying();
		else if (isPaused())
			startPlaying();
	}
	
	public void startRecording()
	{
		assertTrue(canStartRecording());
		status = EControlStatus.STATUS_RECORDING;
		try {
			AndroidRecorder.getInstance().start();
			recordingTimer.schedule(this, recordingTime);
		}
		catch (IOException e) {
			e.printStackTrace();
			stopRecording();
		}
	}
	
	// This method is our cutoff for the recording.
	public void run()
	{
		stopRecording();
	}
	
	public void stopRecording()
	{
		assertTrue(canStopRecording());
		try {
			recordedMessage = AndroidRecorder.getInstance().stop();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		finally {
			status = EControlStatus.STATUS_PAUSED;
			recordingTimer.cancel();
		}
	}
	
	public void record() throws IllegalStateException, IOException
	{
		if (!isRecording())
			startRecording();
		else
			stopRecording();
	}
	
	public void prev() throws IOException
	{
		assertTrue(canPrev());
		selectedMessage = MessagesCached.getInstance().getPrevMessage(selectedMessage);
	}
	
	public void next() throws IOException
	{
		assertTrue(canNext());
		selectedMessage = MessagesCached.getInstance().getNextMessage(selectedMessage);
	}
	
	public void upvoteSelected() throws IOException
	{
		assertTrue(canUpvote());
		HttpInterface.getInstance().downvoteMessage(selectedMessage.getMid());
	}
	
	public void downvoteSelected() throws IOException
	{
		assertTrue(canDownvote());
		HttpInterface.getInstance().upvoteMessage(selectedMessage.getMid());
	}
	
	public void uploadRecording() throws UnsupportedEncodingException, IOException
	{
		assertTrue(recordedMessage != null);
		HttpInterface.getInstance().uploadMessage(recordedMessage,
				AndroidLocation.getInstance().getLongitude(),
				AndroidLocation.getInstance().getLatitude(),
				AndroidLocation.getInstance().getSpeed(),
				User.getLocalUser().getEmail());
	}
	
	public void discardRecording()
	{
		assertTrue(recordedMessage != null);
		recordedMessage.delete();
		recordedMessage = null;
	}

	public void onStop() {
		status = EControlStatus.STATUS_PAUSED;
	}
	
	public boolean canVote() {
		return selectedMessage != null && !selectedMessage.getRatingModified();
	}
	
	public boolean canUpvote() {
		return canVote();
	}
	
	public boolean canDownvote() {
		return canVote();
	}
	
	public boolean canNext() {
		return !isRecording() && selectedMessage != null;
	}
	
	public boolean canPrev() {
		return !isRecording() && selectedMessage != null;
	}
	
	public boolean canStopPlaying() {
		return status == EControlStatus.STATUS_PLAYING;
	}
	
	public boolean canStartPlaying() {
		return status == EControlStatus.STATUS_PAUSED;
	}
	
	public boolean canStartRecording() {
		return status == EControlStatus.STATUS_PAUSED || status == EControlStatus.STATUS_PLAYING;
	}
	
	public boolean canStopRecording() {
		return status == EControlStatus.STATUS_RECORDING;
	}
}
