package ericsson.thinClient.technical;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

import android.media.MediaPlayer;

public class AndroidPlayer implements MediaPlayer.OnCompletionListener {
	static AndroidPlayer singleton = null;
	
	private MediaPlayer player;
	private ArrayList<Listener> listeners;
	
	public interface Listener {
		public void onStop();
	}
	
	public static AndroidPlayer getInstance()
	{
		if (singleton == null)
			singleton = new AndroidPlayer();
		return singleton;
	}
	
	private AndroidPlayer()
	{
		player = new MediaPlayer();
		listeners = new ArrayList<Listener>();
	}
	
	
	public void play(FileInputStream file) throws IllegalArgumentException, IllegalStateException, IOException
	{
		player.setDataSource(file.getFD());
		player.prepare();
		player.start();
	}
	
	public void stop()
	{
		if (player.isPlaying()) {
			player.stop();
			for (Listener listener : listeners)
				listener.onStop();
		}
	}

	public void onCompletion(MediaPlayer mp) {
		stop();
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
