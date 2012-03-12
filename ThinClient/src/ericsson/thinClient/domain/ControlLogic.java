package ericsson.thinClient.domain;

public class ControlLogic {
	private static ControlLogic singleton = null;
	
	public static ControlLogic getInstance()
	{
		if (singleton == null)
			singleton = new ControlLogic();
		return singleton;
	}
	
	enum EControlStatus {
		STATUS_PLAYING,
		STATUS_PAUSED
	}
	
	boolean recording;
	EControlStatus status;
	
	private ControlLogic() {
		status = EControlStatus.STATUS_PAUSED;
		recording = false;
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
	
	public void startRecording()
	{
		recording = true;
	}
	
	public void stopRecording()
	{
		recording = false;
	}
	
	public void record()
	{
		if (isRecording())
			startRecording();
		else
			stopRecording();
	}
	
	public void next()
	{
		assert(!isRecording());
	}
	
	public void prev()
	{
		assert(!isRecording());
	}
}
