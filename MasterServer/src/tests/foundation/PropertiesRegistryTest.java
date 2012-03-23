package tests.foundation;

import foundation.registry.PropertiesRegistry;
import exceptions.PropertyNotFoundException;
import junit.framework.TestCase;

public class PropertiesRegistryTest extends TestCase {
	private final static String STRING_KEY = "stringKey";
	private final static String INT_KEY = "intKey";
	private final static String DOUBLE_KEY = "doubleKey";
	public void testGetProperty() {
		// should find the property
		try {
			String propertyValue = PropertiesRegistry.getProperty(STRING_KEY);
			assertTrue(propertyValue.equals("stringKey"));
		} catch (PropertyNotFoundException e) {
			fail();
		}
		
		// should not find the property, throws PropertyNotFound
		try {
			PropertiesRegistry.getProperty("randomstringthatdoesntexistanywhereeverasdq12321wasad");
			fail();
		} catch (PropertyNotFoundException e) {
		}
	}
	
	public void testGetInt() {
		// should find the property
		try {
			int propertyValue = PropertiesRegistry.getInt(INT_KEY);
			assertTrue(propertyValue == 0);
		} catch (PropertyNotFoundException e) {
			fail();
		}
		
		// should not find the property, throws PropertyNotFound
		try {
			PropertiesRegistry.getInt("randomstringthatdoesntexistanywhereeverasdq12321wasad");
			fail();
		} catch (PropertyNotFoundException e) {
		}
	}
	
	public void testGetDouble() {
		// should find the property
		try {
			double propertyValue = PropertiesRegistry.getDouble(DOUBLE_KEY);
			assertTrue(propertyValue == 0.0d);
		} catch (PropertyNotFoundException e) {
			fail();
		}
		
		// should not find the property, throws PropertyNotFound
		try {
			PropertiesRegistry.getDouble("randomstringthatdoesntexistanywhereeverasdq12321wasad");
			fail();
		} catch (PropertyNotFoundException e) {
		}
	}
}
