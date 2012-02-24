package tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import tests.application.*;
import tests.domain.message.*;
import tests.domain.user.*;
import tests.foundation.*;
import tests.technical.GeoSpatialSearchTest;

@RunWith(Suite.class)

@SuiteClasses({
		FrontControllerTest.class, 
		HttpMessageTest.class,
		UnknownCommandTest.class,
		MessageCommandTest.class,
		 MessageIdentityMapTest.class,
		 MessageMapperTest.class,
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
		 GeoSpatialSearchTest.class

})

public class AllTests {

}