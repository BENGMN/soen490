package tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import tests.domain.message.*;
import tests.domain.user.*;

@RunWith(Suite.class)

@SuiteClasses({ 
		 MessageIdentityMapTest.class,
		 MessageMapperTest.class,
		 MessageTest.class,
		 UserFactoryTest.class,
		 UserIdentityMapTest.class,
		 UserInputMapperTest.class,
		 UserOutputMapperTest.class,
		 UserProxyTest.class,
		 UserTest.class
})

public class DomainTests {

}