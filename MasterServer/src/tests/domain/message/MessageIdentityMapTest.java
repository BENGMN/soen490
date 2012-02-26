/**
 * SOEN 490
 * Capstone 2011
 * Test for MessageIdentityMapTest.
 * Team members: 	Sotirios Delimanolis
 * 			Filipe Martinho
 * 			Adam Harrison
 * 			Vahe Chahinian
 * 			Ben Crudo
 * 			Anthony Boyer
 * 
 * @author Capstone 490 Team Moving Target
 *
 */

package tests.domain.message;

import static org.junit.Assert.*;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.GregorianCalendar;

import org.junit.Test;
import domain.message.Message;
import domain.message.MessageFactory;
import domain.message.MessageIdentityMap;

public class MessageIdentityMapTest {
	
	private byte[] messageData = { 1, 2, 3, 4, 5, 6 };
	private BigInteger mid = new BigInteger("0");
	@Test
	public void testFunctionality()
	{
		MessageIdentityMap map = MessageIdentityMap.getUniqueInstance();
		Message message = MessageFactory.createClean(mid, 0, messageData, 10.0f, 10.0, 10.0, new Timestamp(GregorianCalendar.getInstance().getTimeInMillis()),0);
		
		// Make sure the map put method works
		map.put(mid, message);
		assertEquals(map.get(mid), message);
		
		// Make sure the remove method works
		map.remove(mid);
		assertNull(map.get(mid));
	}
}
