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
		 HttpMessageTest.class,
		 UnsupportedCommandTest.class,
		 MessageCommandTest.class,
		 MessageIdentityMapTest.class,
		 MessageInputMapperTest.class,
		 MessageOutputMapperTest.class,
		 MessageTest.class,
		 MessageFactoryTest.class,
		 UserFactoryTest.class,
		 UserIdentityMapTest.class,
		 UserInputMapperTest.class,
		 UserOutputMapperTest.class,
		 UserProxyTest.class,
		 UserTest.class,
		 DatabaseTest.class,
		 MessageTDGTest.class,
		 UserTDGTest.class,
		 GeoSpatialSearchTest.class,
		 ServerParameterInputMapperTest.class,
		 ServerParameterOutputMapperTest.class,
		 ServerParametersTest.class,
		 ServerParameterTest.class,
		 ServerParameterTDGTest.class,
		 ServerParameterFinderTest.class
		 
		 
})

public class AllTests {

}