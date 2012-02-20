package tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import tests.application.FrontControllerTest;
import tests.application.HttpMessageTest;
import tests.application.UnknownCommandTest;
import tests.domain.message.MessageCommandTest;
import tests.domain.message.MessageIdentityMapTest;
import tests.domain.message.MessageMapperTest;
import tests.domain.message.MessageTest;
import tests.domain.user.UserFactoryTest;
import tests.domain.user.UserIdentityMapTest;
import tests.domain.user.UserInputMapperTest;
import tests.domain.user.UserOutputMapperTest;
import tests.domain.user.UserProxyTest;
import tests.domain.user.UserTest;
import tests.foundation.DatabaseTest;
import tests.foundation.MessageTDGTest;
import tests.foundation.UserTDGTest;

@RunWith(Suite.class)

@SuiteClasses({
		FrontControllerTest.class, 
		HttpMessageTest.class,
		UnknownCommandTest.class,
		MessageCommandTest.class,
		 MessageIdentityMapTest.class,
		 MessageMapperTest.class,
		 MessageTest.class,
		 UserFactoryTest.class,
		 UserIdentityMapTest.class,
		 UserInputMapperTest.class,
		 UserOutputMapperTest.class,
		 UserProxyTest.class,
		 UserTest.class,
		 DatabaseTest.class,
		 MessageTDGTest.class,
		 UserTDGTest.class
		 
})

public class AllTests {

}
