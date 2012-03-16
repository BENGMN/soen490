package tests.domain.serverparameter;

import java.sql.SQLException;

import junit.framework.TestCase;

import domain.serverparameter.ServerParameter;
import domain.serverparameter.mappers.ServerParameterInputMapper;
import domain.serverparameter.mappers.ServerParameterOutputMapper;
import exceptions.MapperException;
import foundation.tdg.ServerParameterTDG;

public class ServerParameterOutputMapperTest extends TestCase {
	private static String paramName = "paramName";
	private static String description = "This is a test description of a server parameter.";
	private static String value = "0.0";
	
	public void testInsert() {
		
		ServerParameter p = new ServerParameter(paramName, description, value);
		try {
			ServerParameterTDG.create();
			
			//insert
			int count = ServerParameterOutputMapper.insert(p);
			assertEquals(1, count);
			
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
	
	public void testUpdate() {
		ServerParameter p = new ServerParameter(paramName, description, value);
		try {
			ServerParameterTDG.create();
			
			// insert
			int count = ServerParameterOutputMapper.insert(p);
			assertEquals(1, count);
			// update it
			p.setDescription(p.getDescription() + "some terminating string");
			// updating should affect 1 row
			count = ServerParameterOutputMapper.update(p);
			assertEquals(1, count);
			
			// try updating one that isn't in database
			p = new ServerParameter("other", description, value);
			count = ServerParameterOutputMapper.update(p);
			assertEquals(0, count);
			
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
	
	public void testDelete() {
		ServerParameter p = new ServerParameter(paramName, description, value);
		try {
			ServerParameterTDG.create();
			
			// insert
			int count = ServerParameterOutputMapper.insert(p);
			assertEquals(1, count);

			// delete
			count = ServerParameterOutputMapper.delete(p);
			assertEquals(1, count);
			
			// try updating one that isn't in database
			count = ServerParameterOutputMapper.delete(p);
			assertEquals(0, count);
			
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
