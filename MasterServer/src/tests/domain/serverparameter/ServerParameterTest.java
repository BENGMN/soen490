package tests.domain.serverparameter;

import junit.framework.TestCase;
import domain.serverparameter.ServerParameter;

public class ServerParameterTest extends TestCase {
	
	private static String paramName = "paramName";
	private static String description = "This is a test description of a server parameter.";
	private static String value = "0.0";
	
	public void testGetters() {
		ServerParameter param = new ServerParameter(paramName, description, value);
		assertTrue(paramName.equals(param.getParamName()));
		assertTrue(description.equals(param.getDescription()));
		assertTrue(value.equals(param.getValue()));
	}
	
	public void testSetters() {
		ServerParameter param = new ServerParameter(paramName, description, value);
		String changedDescription = "Description changed.";
		String changedValue = "10.0";
		
		param.setDescription(changedDescription);
		param.setValue(changedValue);
		
		assertTrue(paramName.equals(param.getParamName()));
		assertTrue(changedDescription.equals(param.getDescription()));
		assertTrue(changedValue.equals(param.getValue()));
	}
	
	public void testEquals() {
		ServerParameter param1 = new ServerParameter(paramName, description, value);
		ServerParameter param2 = new ServerParameter(paramName, description, value);

		assertTrue(param1.equals(param2));
		param2.setDescription(param2.getDescription() + " Now a different description.");
		assertFalse(param1.equals(param2));
		assertTrue(!param1.equals(param2));
	}
}
