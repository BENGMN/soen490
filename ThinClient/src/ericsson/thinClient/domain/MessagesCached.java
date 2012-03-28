package ericsson.thinClient.domain;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.msgpack.MessagePack;
import org.msgpack.unpacker.Unpacker;

import ericsson.thinClient.technical.AndroidLocation;
import ericsson.thinClient.technical.AndroidLogging;
import ericsson.thinClient.technical.AndroidHttp;

public class MessagesCached {
	private static MessagesCached singleton = null;
	
	private static int maxCacheSize = 1000;
	private static int deletionSize = 500;
	private List<Message> cachedMessages;
	private Set<BigInteger> previousIds;
	
	private MessagesCached()
	{
		cachedMessages = new ArrayList<Message>();
		previousIds = new HashSet<BigInteger>();
	}
	
	public static MessagesCached getInstance()
	{
		if (singleton == null)
			singleton = new MessagesCached();
		return singleton;
	}
	
	private int retrieveMessages() throws IOException
	{
		InputStream in = AndroidHttp.getInstance().getMessageIDs(AndroidLocation.getInstance().getLongitude(),
				AndroidLocation.getInstance().getLatitude(),
				AndroidLocation.getInstance().getSpeed(),
				"created_at");
		if (in == null) {
			//AndroidLogging.getInstance().error("Failed to get list of messageids.");
			return 0;
		}
		AndroidLogging.getInstance().log("Get list of messages based on location.");
		Unpacker unpacker = (new MessagePack()).createUnpacker(in);
		int size = unpacker.readInt();
		List<BigInteger> filteredIds = new ArrayList<BigInteger>();
		for (int i = 0; i < size; ++i) {
			BigInteger id = unpacker.readBigInteger();
			if (previousIds.contains(id))
				continue;
			previousIds.add(id);
			filteredIds.add(id);
		}
		int totalSize = filteredIds.size() + cachedMessages.size();
		int newSize = totalSize;
		int slideIndex = 0;
		if (totalSize > maxCacheSize) {
			newSize -= deletionSize;
			slideIndex = deletionSize;
			ArrayList<Message> newMessages = new ArrayList<Message>(newSize);
			newMessages.addAll(cachedMessages.subList(deletionSize, cachedMessages.size()));
			cachedMessages = newMessages;
		}
		
		in = AndroidHttp.getInstance().getMessages(filteredIds);
		unpacker = (new MessagePack()).createUnpacker(in);
		size = unpacker.readInt();
		for (int i = 0; i < size; ++i)
			cachedMessages.add(Message.createFromResponse(in));
		return slideIndex;
	}
	
	private Message getMessage(int index) throws IOException
	{
		if (index < 0)
			index = cachedMessages.size();
		if (index > cachedMessages.size())
			index -= retrieveMessages();
		if (index > cachedMessages.size())
			return null;
		return cachedMessages.get(index);
	}
	
	public Message getNextMessage(Message in) throws IOException
	{
		int index = cachedMessages.indexOf(in)+1;
		return getMessage(index+1);
	}
	
	public Message getPrevMessage(Message in) throws IOException
	{
		int index = cachedMessages.indexOf(in);
		return getMessage(index-1);
	}
}
