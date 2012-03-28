package ericsson.thinClient.technical;

import java.util.ArrayList;
import java.util.List;

public class AndroidLogging {
	private static ThreadLocal<AndroidLogging> singleton = new ThreadLocal<AndroidLogging>();
	private List<Listener> listeners;
	
	public interface Listener {
		public void log(String message);
		public void error(String message);
	}
	
	private AndroidLogging()
	{
		listeners = new ArrayList<Listener>();
	}
	
	public static AndroidLogging getInstance()
	{
		if (singleton.get() == null)
			singleton.set(new AndroidLogging());
		return singleton.get();
	}
	
	public void log(String message)
	{
		for (Listener listener : listeners)
			listener.log(message);
	}
	
	public void error(String message)
	{
		for (Listener listener : listeners)
			listener.error(message);
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
