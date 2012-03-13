package tests;

import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;

import junit.framework.TestCase;

public class TestLoggin extends TestCase {
	public void testApplicationLogger() {

		String s = null;
		assertFalse("".equals(s));
		
		String str;
		
		if(!(str = "asdsadqwe").equals(""))
			assertTrue(true);
		else fail();
	}
}
