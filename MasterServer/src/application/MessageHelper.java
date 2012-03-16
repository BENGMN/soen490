package application;

import java.io.DataOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.util.List;

import org.msgpack.MessagePack;
import org.msgpack.packer.Packer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import domain.message.Message;
import exceptions.MapperException;

public class MessageHelper {

	public static void setMessageIDToResponse(Message msg, DataOutputStream dataOutputStream) throws IOException {
		
		MessagePack messagePack = new MessagePack();
		Packer packer = messagePack.createPacker(dataOutputStream);
		packer.write(msg.getMid().toString());
		
	}

	public static void setMessageIDs(List<BigInteger> ids, DataOutputStream dataOutputStream) throws IOException {
		
		Packer packer = (new MessagePack()).createPacker(dataOutputStream);
		packer.write(ids.size());
		for(BigInteger id: ids) {
			packer.write(id.toString());
		}
		
	}

	// Allowing us to use MessagePack in our domain layer, outside of our application layer.
	public static void writeListClient(List<Message> messages, DataOutputStream out) throws IOException
	{
		Packer packer = (new MessagePack()).createPacker(out);
		packer.write(messages.size());
		for (Message message : messages)
			MessageHelper.writeClient(message,out);
	}

	public static void writeClient(Message msg, DataOutputStream out) throws IOException
	{
		MessagePack pack = new MessagePack();
		Packer packer = pack.createPacker(out);
		packer.write(msg.getMid().toString());
		try {
			packer.write(msg.getOwner().getEmail());
		} catch (MapperException e) {
			Logger logger = (Logger)LoggerFactory.getLogger("application");
			logger.error("MapperException occurred when writing to client: {}", e);
		}
		packer.write(msg.getMessage());
		packer.write(msg.getSpeed());
		packer.write(msg.getCreatedAt().getTime());
		packer.write(msg.getLongitude());
		packer.write(msg.getLatitude());
		packer.write(msg.getUserRating());
	}

}
