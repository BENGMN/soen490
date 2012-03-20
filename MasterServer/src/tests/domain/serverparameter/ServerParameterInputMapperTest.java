package tests.domain.serverparameter;

import java.sql.SQLException;
import java.util.List;

import junit.framework.TestCase;
import domain.serverparameter.ServerParameter;
import domain.serverparameter.mappers.ServerParameterInputMapper;
import domain.serverparameter.mappers.ServerParameterOutputMapper;
import exceptions.MapperException;
import foundation.tdg.ServerParameterTDG;

public class ServerParameterInputMapperTest extends TestCase {
	
	private static String paramName = "paramName";
	private static String description = "This is a test description of a server parameter.";
	private static String value = "0.0";
	
	public void testFind() {
		
		ServerParameter p = new ServerParameter(paramName, description, value);
		try {
			ServerParameterTDG.create();
			
			ServerParameterOutputMapper.insert(p);
			ServerParameter param =	ServerParameterInputMapper.find(paramName);
			
			assertTrue(p.equals(param));
			ServerParameterTDG.drop();
		} catch (SQLException e) {
			e.printStackTrace();
			fail();
		} catch (MapperException e) {
			fail();
		} finally {
			try {
				ServerParameterTDG.drop();
			} catch (SQLException e) {
			}
		}
	}
	
	public void testFindAll() {
		ServerParameter p = new ServerParameter(paramName, description, value);
		ServerParameter p2 = new ServerParameter(paramName + "2", description, value);
		ServerParameter p3 = new ServerParameter(paramName + "3", description, value);

		try {
			ServerParameterTDG.create();
			List<ServerParameter> param =	ServerParameterInputMapper.findAll();
			assertFalse(param.isEmpty());
			ServerParameterOutputMapper.insert(p);
			ServerParameterOutputMapper.insert(p2);
			ServerParameterOutputMapper.insert(p3);
			
			param =	ServerParameterInputMapper.findAll();
			assertFalse(param.isEmpty());
			
			assertEquals(3 + ServerParameterTDG.INSERTIONS.length, param.size());

			ServerParameterTDG.drop();
		} catch (SQLException e) {
			e.printStackTrace();
			fail();
		} finally {
			try {
				ServerParameterTDG.drop();
			} catch (SQLException e) {
			}
		}
	}
	
}
