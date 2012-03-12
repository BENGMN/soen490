package ericsson.thinClient.domain;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import ericsson.thinClient.technical.WebInterface;

public class Recordings {
	private static Recordings singleton = null;
	
	private List<Message> cachedMessages;
	private Set<BigInteger> listenedIDs;
	
	private Recordings()
	{
		cachedMessages = new ArrayList<Message>();
		listenedIDs = new HashSet<BigInteger>();
	}
	
	public static Recordings getInstance()
	{
		if (singleton == null)
			singleton = new Recordings();
		return singleton;
	}
	
	public void clearCache()
	{
		cachedMessages.clear();
	}
	
	void populateCache()
	{
		List<BigInteger> ids = WebInterface.getInstance().getMessageIDs();
	}
}
