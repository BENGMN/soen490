package tests.domain.serverparameter;

import java.sql.SQLException;
import java.util.Map;

import domain.serverparameter.ServerParameter;
import domain.serverparameter.mappers.ServerParameterOutputMapper;
import foundation.tdg.ServerParameterTDG;

import application.ServerParameters;
import junit.framework.TestCase;

public class ServerParametersTest extends TestCase {
	private static String paramName = "paramName";
	private static String description = "This is a test description of a server parameter.";
	private static String value = "0.0";
		
	public void testConstructor() {
		ServerParameter p = new ServerParameter(paramName, description, value);
		try {
			ServerParameterTDG.create();
			
			// insert
			int count = ServerParameterOutputMapper.insert(p);
			assertEquals(1, count);
			
			// should have one element
			Map<String, ServerParameter> params = ServerParameters.getUniqueInstance();
			((ServerParameters)params).updateParameters();
			
			assertEquals(1 + ServerParameterTDG.INSERTIONS.length, params.size());
			
			ServerParameter param = params.get(p.getParamName());
			assertTrue(param.equals(param));
			ServerParameterTDG.drop();
		} catch (SQLException e) {
			fail();
		} finally {
			try {
				ServerParameterTDG.drop();
			} catch (SQLException e) {
			}
		}
	}
	
	public void testSingleton() {
		ServerParameter p = new ServerParameter(paramName, description, value);
		try {
			ServerParameterTDG.create();
			
			// insert
			int count = ServerParameterOutputMapper.insert(p);
			assertEquals(1, count);
			
			// should have one element
			Map<String, ServerParameter> params = ServerParameters.getUniqueInstance();
			((ServerParameters)params).updateParameters();

			assertEquals(1 + ServerParameterTDG.INSERTIONS.length, params.size());
			
			ServerParameters params2 = ServerParameters.getUniqueInstance();
			
			// should be same object
			assertTrue(params == params2);
						
			ServerParameterTDG.drop();
		} catch (SQLException e) {
			fail();
		} finally {
			try {
				ServerParameterTDG.drop();
			} catch (SQLException e) {
			}
		}
	}
	
	public void testUpdate() {
		ServerParameter p = new ServerParameter(paramName, description, value);
		try {
			ServerParameterTDG.create();
			
			// insert
			int count = ServerParameterOutputMapper.insert(p);
			assertEquals(1, count);
			
			// should have one element
			Map<String, ServerParameter> params = ServerParameters.getUniqueInstance();
			((ServerParameters)params).updateParameters();

			assertEquals(1 + ServerParameterTDG.INSERTIONS.length, params.size());
			
			ServerParameter param = params.get(p.getParamName());
			assertTrue(param.equals(param));
			
			// update the parameter
			p.setDescription(p.getDescription() + "terminating string");
			ServerParameterOutputMapper.update(p);
			
			// update from database
			((ServerParameters)params).updateParameters();
			
			// compare
			assertFalse(param.equals(params.get(param.getParamName())));
						
			ServerParameterTDG.drop();
		} catch (SQLException e) {
			fail();
		} finally {
			try {
				ServerParameterTDG.drop();
			} catch (SQLException e) {
			}
		}
	}
	
	// differenc from previous one is that we don't call updateParameters() after changing the values
	public void testGet() {
		ServerParameter p = new ServerParameter(paramName, description, value);
		try {
			ServerParameterTDG.create();
			
			// insert
			int count = ServerParameterOutputMapper.insert(p);
			assertEquals(1, count);
			
			// should have one element
			Map<String, ServerParameter> params = ServerParameters.getUniqueInstance();
			((ServerParameters)params).updateParameters();

			assertEquals(1 + ServerParameterTDG.INSERTIONS.length, params.size());
			
			ServerParameter param = params.get(p.getParamName());
			assertTrue(param.equals(param));
			
			// update the parameter
			p.setDescription(p.getDescription() + "terminating string");
			ServerParameterOutputMapper.update(p);

			// don't do a updateParameters()
			// get pulls from database regardless
			
			// compare
			assertFalse(param.equals(params.get(param.getParamName())));
						
			ServerParameterTDG.drop();
		} catch (SQLException e) {
			fail();
		} finally {
			try {
				ServerParameterTDG.drop();
			} catch (SQLException e) {
			}
		}
	}
}
