package application;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.msgpack.MessagePack;
import org.msgpack.packer.Packer;
import org.msgpack.unpacker.Unpacker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.sun.org.apache.xml.internal.security.utils.Base64;

import domain.message.Message;
import domain.message.MessageFactory;
import domain.user.User;
import domain.user.UserFactory;
import domain.user.UserType;
import exceptions.CorruptStreamException;


public class IOUtils {

	/**
	 * Takes the given DataOutputStream and writes the give BigInteger to it using the msgpack library.
	 * @param mid BigInteger message id
	 * @param out A DataOutputStream to write to
	 * @throws IOException
	 */
	public static void writeMessageIDtoStream(BigInteger mid, DataOutputStream out) throws IOException {
		MessagePack messagePack = new MessagePack();
		Packer packer = messagePack.createPacker(out);
		packer.write(mid.toString());	
	}
	
	/**
	 * Takes the given DataInputStream, reads a String from it, and converts it to a BigInteger.
	 * This method should only be called on an Stream on which the corresponding 'set' method was called.
	 * @param in A DataInputStream to read from
	 * @return Returns a BigInteger, the Message id, read from the DataInputStream 
	 * @throws IOException
	 */
	public static BigInteger readMessageIDfromStream(DataInputStream in) throws IOException, NumberFormatException {
		BigInteger mid = null;
		
		MessagePack messagePack = new MessagePack();
		Unpacker unpacker = messagePack.createUnpacker(in);

		mid = new BigInteger(unpacker.readString());
		
		return mid;
	}
	
	/**
	 * Takes the given DataOutputStream and writes the ids contained in the given List of BigInteger.
	 * @param ids A List of BigInteger
	 * @param out A DataOutputStream to write to
	 * @throws IOException
	 */
	public static void writeListMessageIDsToStream(List<BigInteger> ids, DataOutputStream out) throws IOException {
		MessagePack messagePack = new MessagePack();
		Packer packer = messagePack.createPacker(out);
		// write the size
		packer.write(ids.size());
		for(BigInteger id: ids) {
			packer.write(id.toString());
		}
	}
	
	/**
	 * Takes the given DataInputStream and reads a List of BigInteger from it.
	 * @param in A DataInputStream to read from
	 * @return Returns a List of BigInteger read from the DataInputStream
	 * @throws IOException
	 * @throws CorruptStreamException 
	 */
	public static List<BigInteger> readListMessageIDsFromStream(DataInputStream in) throws IOException, CorruptStreamException {
		BigInteger mid = null;
		List<BigInteger> ids = null;
		
		MessagePack messagePack = new MessagePack();
		Unpacker unpacker = messagePack.createUnpacker(in);
		
		int numberOfIDs = unpacker.readInt();
		ids = new ArrayList<BigInteger>(numberOfIDs);
		
		try {
			for (int i = 0; i < numberOfIDs; i++) {
				mid = new BigInteger(unpacker.readString());
				ids.add(mid);
			}
		} catch (NumberFormatException e) {
			Logger logger = (Logger)LoggerFactory.getLogger("application");
			logger.error("NumberFormatException occurred when trying to read a Message id from the inputstream: {}", e);
			throw new CorruptStreamException("A NumberFormatException occurred when trying to read a message id from the input stream.");
		}
		
		return ids;
	}

	/**
	 * Takes the given DataOutputStream and writes the given Message to it.
	 * @param msg A Message to write
	 * @param out A DataOutputStream to write to
	 * @throws IOException
	 */
	public static void writeMessageToStream(Message msg, DataOutputStream out) throws IOException {
		MessagePack pack = new MessagePack();
		Packer packer = pack.createPacker(out);
		
		packer.write(msg.getMid().toString()); // write mid
		packer.write(msg.getOwner().getUid().toString()); // write uid of owner
		packer.write(msg.getMessage()); // write message contents (audio file)
		packer.write(msg.getSpeed()); // write speed
		packer.write(msg.getCreatedAt().getTime()); // write time created
		packer.write(msg.getLongitude()); // write longitude
		packer.write(msg.getLatitude()); // write latitude
		packer.write(msg.getUserRating()); // write user rating
	}
	
	/**
	 * Takes the give DataInputStream and reads a Message from it.
	 * @param in A DataInputStream to read from
	 * @return Returns a Message read from the DataInputStream
	 * @throws IOException
	 * @throws CorruptStreamException
	 */
	public static Message readMessageFromStream(DataInputStream in) throws IOException, CorruptStreamException {
		Message message = null;
		
		MessagePack messagePack = new MessagePack();
		Unpacker unpacker = messagePack.createUnpacker(in);
		
		BigInteger mid = new BigInteger(unpacker.readString()); // read mid
		BigInteger uid = new BigInteger(unpacker.readString()); // read uid
		byte[] audio = unpacker.readByteArray(); // read audio contents
		float speed = unpacker.readFloat(); // read speed
		Timestamp createAt = new Timestamp(unpacker.readLong()); // read time created
		double longitude = unpacker.readDouble(); // read longitude
		double latitude = unpacker.readDouble(); // read latitud
		int userRating = unpacker.readInt(); // read user rating
		
		message = MessageFactory.createClean(mid, uid, audio, speed, latitude, longitude, createAt, userRating);
		
		return message;
	}
	
	/**
	 * Takes the given DataInputStream and reads a List of Message from it
	 * @param in A DataInputStream to read from
	 * @return Returns a List of Message
	 * @throws IOException
	 * @throws CorruptStreamException
	 */
	public static List<Message> readMessageListFromStream(DataInputStream in) throws IOException, CorruptStreamException {
		List<Message> messages = null;
		
		MessagePack messagePack = new MessagePack();
		Unpacker unpacker = messagePack.createUnpacker(in);
		
		int numberOfMessages = unpacker.readInt();
		messages = new ArrayList<Message>(numberOfMessages);
		
		try {
			for (int i = 0; i < numberOfMessages; i++) {
				BigInteger mid = new BigInteger(unpacker.readString()); // read mid
				BigInteger uid = new BigInteger(unpacker.readString()); // read uid
				byte[] audio = unpacker.readByteArray(); // read audio contents
				float speed = unpacker.readFloat(); // read speed
				Timestamp createAt = new Timestamp(unpacker.readLong()); // read time created
				double longitude = unpacker.readDouble(); // read longitude
				double latitude = unpacker.readDouble(); // read latitude
				int userRating = unpacker.readInt(); // read user rating
				
				Message message = MessageFactory.createClean(mid, uid, audio, speed, latitude, longitude, createAt, userRating);
				
				messages.add(message);
			}
		} catch (NumberFormatException e) {
			Logger logger = (Logger)LoggerFactory.getLogger("application");
			logger.error("NumberFormatException occurred when trying to read a Message from the inputstream: {}", e);
			
			throw new CorruptStreamException("A NumberFormatException occurred when trying to read a message from the input stream.");
		}
		
		return messages;
	}
	
	/**
	 * Takes the given DataOutputStream and writes the List of Message to it.
	 * @param messages A List of Message to write
	 * @param out A DataOutputStream to write to
	 * @throws IOException
	 */
	public static void writeMessageListToStream(List<Message> messages, DataOutputStream out) throws IOException {
		MessagePack messagePack = new MessagePack();
		Packer packer = messagePack.createPacker(out);
		// Write the size
		packer.write(messages.size());
		for (Message message : messages)
			IOUtils.writeMessageToStream(message,out);
	}

	/**
	 * Takes the given DataOutputStream and writes the given User to it.
	 * @param user A User to write
	 * @param out A DataOutputStream to write to
	 * @throws IOException
	 */
	public static void writeUserToStream(User user, DataOutputStream out) throws IOException {
		MessagePack messagePack = new MessagePack();
		Packer packer = messagePack.createPacker(out);
		
		packer.write(user.getUid().toString()); // write user id
		packer.write(user.getPassword()); // write password
		packer.write(user.getEmail()); // write email
		packer.write(user.getType().toString()); // write user type
		packer.write(user.getVersion()); // write version
	}
	
	/**
	 * Takes the given DataInputStream and reads a User from it
	 * @param in DataInputStream to read from
	 * @return Returns a User read from the given DataInputStream
	 * @throws IOException
	 * @throws CorruptStreamException
	 */
	public static User readUserFromStream(DataInputStream in) throws IOException, CorruptStreamException {
		User user;
		
		MessagePack messagePack = new MessagePack();
		Unpacker unpacker = messagePack.createUnpacker(in);
		BigInteger uid = null;
		
		try {
			uid = new BigInteger(unpacker.readString()); // read user id
		} catch (NumberFormatException e) {
			Logger logger = (Logger)LoggerFactory.getLogger("application");
			logger.error("NumberFormatException occurred when trying to read a user from the inputstream: {}", e);
			
			throw new CorruptStreamException("A NumberFormatException occurred when trying to read a user from the input stream.");
		}
		String password = unpacker.readString(); // read password
		String email = unpacker.readString(); // read email
		String userType = unpacker.readString(); // read user type
		UserType type = UserType.valueOf(userType);
		int version = unpacker.readInt();
		
		user = UserFactory.createClean(uid, email, password, type, version);
		return user;
	}
	
	/**
	 * Takes the given DataOutputStream and writes the List of Message as XML to it
	 * @param messages A List of Message to write as XML
	 * @param out A DataOutputStream to write to
	 * @throws IOException
	 */
	public static void writeMessageListToXML(List<Message> messages, DataOutputStream out) throws IOException {
		try {
			DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			Document doc = builder.newDocument();
			// create the root element
			Element root = doc.createElement("Messages");
			
			// for each Message add a node to the XML
			for (Message message : messages) {
				Element messageRoot = doc.createElement("Message");
			
				// create and append all the children
				XMLCreateAndAppend(doc, "mid", message.getMid().toString(), messageRoot);
				XMLCreateAndAppend(doc, "owner", message.getOwner().getUid().toString(), messageRoot);
				XMLCreateAndAppend(doc, "message", Base64.encode(message.getMessage()), messageRoot);
				XMLCreateAndAppend(doc, "speed", "" + message.getSpeed(), messageRoot);
				XMLCreateAndAppend(doc, "longitude", message.getLongitude() + "", messageRoot);
				XMLCreateAndAppend(doc, "latitude", "" + message.getLatitude(), messageRoot);
				XMLCreateAndAppend(doc, "createdAt", message.getCreatedAt().getTime() + "", messageRoot);
				XMLCreateAndAppend(doc, "userRating", message.getUserRating() + "" , messageRoot);
				
				root.appendChild(messageRoot);
			}
			
			doc.appendChild(root);
			
			// write to the outputstream
			DOMSource domSource = new DOMSource(doc);
			TransformerFactory factory = TransformerFactory.newInstance();
			
			Transformer transformer = factory.newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
			
			transformer.transform(domSource, new StreamResult(out));
		} catch (Exception e) {
			Logger logger = (Logger) LoggerFactory.getLogger("application");
			logger.error("Could not properly serialize Message to XML. The following exception occurred: {}", e);
			throw new IOException("Could not write a Message to XML.");
		}
	}
	
	/**
	 * Takes the given DataOutputStream writes a Message as XML to it.
	 * @param message A Message to write as XML
	 * @param out A DataOutputStream to write to
	 * @throws IOException
	 */
	public static void writeMessageToXML(Message message, DataOutputStream out) throws IOException {
		try {
			DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			Document doc = builder.newDocument();
			// create the root element
			Element root = doc.createElement("Message");
			
			// create and append all the children
			XMLCreateAndAppend(doc, "mid", message.getMid().toString(), root);
			XMLCreateAndAppend(doc, "owner", message.getOwner().getUid().toString(), root);
			XMLCreateAndAppend(doc, "message", Base64.encode(message.getMessage()), root);
			XMLCreateAndAppend(doc, "speed", "" + message.getSpeed(), root);
			XMLCreateAndAppend(doc, "longitude", message.getLongitude() + "", root);
			XMLCreateAndAppend(doc, "latitude", "" + message.getLatitude(), root);
			XMLCreateAndAppend(doc, "createdAt", message.getCreatedAt().getTime() + "", root);
			XMLCreateAndAppend(doc, "userRating", message.getUserRating() + "" , root);
			
			doc.appendChild(root);
			
			// write to the outputstream
			DOMSource domSource = new DOMSource(doc);
			TransformerFactory factory = TransformerFactory.newInstance();
			
			Transformer transformer = factory.newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
			
			transformer.transform(domSource, new StreamResult(out));
		} catch (Exception e) {
			Logger logger = (Logger) LoggerFactory.getLogger("application");
			logger.error("Could not properly serialize Message to XML. The following exception occurred: {}", e);
			throw new IOException("Could not write a Message to XML.");
		}
	}
	
	/**
	 * Takes a given DataInputStream and reads a List of Message from XML
	 * @param in A DataInputStream to read from
	 * @return Returns a List of Message
	 * @throws IOException
	 */
	public static List<Message> readMessageListFromXML(DataInputStream in) throws IOException {
		List<Message> messages = null;

		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc = builder.parse(in);
			
			// Gets all the message nodes
			NodeList n = doc.getElementsByTagName("Message");
			
			Message msg = null;
			
			int size = n.getLength();
			
			messages = new ArrayList<Message>(size);
			
			// for each Message node, create and add a Message to the list
			for (int i = 0; i < size; i++) {
				Node node = n.item(i);
				
				BigInteger mid = new BigInteger(getElementValue(node, "mid"));
				BigInteger uid = new BigInteger(getElementValue(node, "owner"));
				byte[] message = Base64.decode(getElementValue(node, "message"));
				float speed = Float.parseFloat(getElementValue(node, "speed"));
				double longitude = Double.parseDouble(getElementValue(node, "longitude"));
				double latitude = Double.parseDouble(getElementValue(node, "latitude"));
				Timestamp createdAt = new Timestamp(Long.parseLong(getElementValue(node, "createdAt")));
				int userRating = Integer.parseInt(getElementValue(node, "userRating"));
			
				msg = MessageFactory.createClean(mid, uid, message, speed, latitude, longitude, createdAt, userRating);
				messages.add(msg);
			}
		} catch (Exception e) {
			Logger logger = (Logger) LoggerFactory.getLogger("application");
			logger.error("Could not properly deserialize Message to XML. The following exception occurred: {}", e);
			throw new IOException("Could not read a Message from XML.");
		} 
		
		return messages;
	}
	
	/**
	 * Takes the given DataOutputStream writes a User as XML to it.
	 * @param user A User to write as XML
	 * @param out A DataOutputStream to write to
	 * @throws IOException
	 */
	public static void writeUserToXML(User user, DataOutputStream out) throws IOException {
		try {
			DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			Document doc = builder.newDocument();
			// create the root element
			Element root = doc.createElement("User");
			
			// create and append all the children
			XMLCreateAndAppend(doc, "uid", user.getUid().toString(), root);
			XMLCreateAndAppend(doc, "password", user.getPassword(), root);
			XMLCreateAndAppend(doc, "email", user.getEmail(), root);
			XMLCreateAndAppend(doc, "usertype", "" + user.getType(), root);
			XMLCreateAndAppend(doc, "version", user.getVersion() + "", root);
			
			doc.appendChild(root);
			
			// write to the outputstream
			DOMSource domSource = new DOMSource(doc);
			TransformerFactory factory = TransformerFactory.newInstance();
			
			Transformer transformer = factory.newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
			
			transformer.transform(domSource, new StreamResult(out));
		} catch (Exception e) {
			Logger logger = (Logger) LoggerFactory.getLogger("application");
			logger.error("Could not properly serialize User to XML. The following exception occurred: {}", e);
			throw new IOException("Could not write a User to XML.");
		}
	}
	
	/**
	 * Takes the given DataOutputStream and reads a List of User as XML from it
	 * @param user A User to read as XML
	 * @param out A DataOutputStream to read from
	 * @throws IOException
	 */
	public static List<User> readUserFromXML(DataInputStream in) throws IOException {
		List<User> users = null;

		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc = builder.parse(in);
			
			// Gets all the message nodes
			NodeList n = doc.getElementsByTagName("User");
			
			User user = null;
			
			int size = n.getLength();
			
			users = new ArrayList<User>(size);
			
			// for each Message node, create and add a Message to the list
			for (int i = 0; i < size; i++) {
				Node node = n.item(i);
				
				BigInteger uid = new BigInteger(getElementValue(node, "uid"));
				String password = getElementValue(node, "password");
				String email = getElementValue(node, "email");
				UserType usertype = UserType.valueOf(getElementValue(node, "usertype"));
				int version = Integer.parseInt(getElementValue(node, "version"));
				
			
				user = UserFactory.createClean(uid, email, password, usertype, version);
				users.add(user);
			}
		} catch (Exception e) {
			Logger logger = (Logger) LoggerFactory.getLogger("application");
			logger.error("Could not properly deserialize User from XML. The following exception occurred: {}", e);
			throw new IOException("Could not read a User from XML.");
		} 
		
		return users;
	}
	
	/**
	 * Creates a node with the given element name and value and append it to the root
	 * @param doc Document
	 * @param elementName Name of element to create
	 * @param nodeValue Value of the created element
	 * @param root Element to append the new one to
	 */
	private static void XMLCreateAndAppend(Document doc, String elementName, String nodeValue, Element root) {
		Element e = doc.createElement(elementName);
		e.setTextContent(nodeValue);
		root.appendChild(e);
	}
	
	/**
	 * Gets the value of the element with the given name at the given node
	 * @param node Node to inspect
	 * @param elementName Name of element from which to retrieve value
	 * @return
	 */
	private static String getElementValue(Node node, String elementName) {
		Node n = ((Element)node).getElementsByTagName(elementName).item(0);
		return ((Element)n).getTextContent();
	}
	
	/**
	 * Function used to ensure that a string conforms to the email address syntax.
	 * @param emailAddress String parameter containing the email address to be tested
	 * @return Returns true if the email address has valid syntax, false otherwise.
	 */
	public static boolean validateEmail(String emailAddress) {  
		String email_regex ="^[\\w\\-]([\\.\\w])+[\\w]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";  
		Pattern pattern = Pattern.compile(email_regex, Pattern.CASE_INSENSITIVE);  
		Matcher matcher = pattern.matcher(emailAddress);
		return matcher.matches();
	}

	/*
	 * XML schema for Message
	 * 	<Messages>
	 *		<Message>
	 *			<mid></mid>
	 *			<owner></owner>
	 *			<message></message>
	 *			<speed></speed>
	 *			<longitude></longitude>
	 *			<latitude></latitude>
	 *			<createdAt></createdAt>
	 *			<userRating></userRating>
	 *		</Message>
	 *	</Messages>	
	 *
	 *	XML schema for User
	 *	<User>
	 *		<uid></uid>
	 *		<password></password
	 *		<email></email>
	 *		<usertype></usertype>
	 *		<version></version>
	 *	</User>
	 *		
	 */
}
