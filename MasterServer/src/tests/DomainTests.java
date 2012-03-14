package tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import tests.domain.message.*;
import tests.domain.user.*;

@RunWith(Suite.class)

@SuiteClasses({ 
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
		 UserTest.class
})

public class DomainTests {

}