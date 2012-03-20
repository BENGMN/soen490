package tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import tests.application.*;
import tests.domain.message.*;
import tests.domain.serverparameter.ServerParameterInputMapperTest;
import tests.domain.serverparameter.ServerParameterOutputMapperTest;
import tests.domain.serverparameter.ServerParameterTest;
import tests.domain.serverparameter.ServerParametersTest;
import tests.domain.user.*;
import tests.foundation.*;
import tests.technical.GeoSpatialSearchTest;

@RunWith(Suite.class)

@SuiteClasses({
		 FrontControllerTest.class,
		 
		 IOUtilsTest.class,
		 
		 HttpMessageTest.class,
		 
		 UnsupportedCommandTest.class,
		 
		 MessageCommandTest.class,
		 MessageInputMapperTest.class,
		 MessageOutputMapperTest.class,
		 MessageTest.class,
		 MessageFactoryTest.class,
		 MessageTDGTest.class,
		 MessageFinderTest.class,
		 
		 UserFactoryTest.class,
		 UserInputMapperTest.class,
		 UserOutputMapperTest.class,
		 UserProxyTest.class,
		 UserTest.class,		 
		 UserTDGTest.class,
		 UserFinderTest.class,
		 
		 DatabaseTest.class,

		 GeoSpatialSearchTest.class,
		 
		 ServerParameterInputMapperTest.class,
		 ServerParameterOutputMapperTest.class,
		 ServerParametersTest.class,
		 ServerParameterTest.class,
		 ServerParameterTDGTest.class,
		 ServerParameterFinderTest.class,
		 
		 ServerListTDGTest.class,
		 
})

public class AllTests {

}