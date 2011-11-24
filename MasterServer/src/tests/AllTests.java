package tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses( { 	DatabaseTest.class,
						FrontControllerTest.class,
						GetMessagesCommandTest.class,
						MessageIdentityMapTest.class,
						MessageMapperTest.class,
						MessageTDGTest.class,
						PutMessageCommandTest.class,
						RateMessageCommandTest.class,
						UnknownCommandTest.class,
						UserIdentityMapTest.class,
						UserMapperTest.class,
						UserProxyTest.class,
						UserTDGTest.class,
						UserTest.class})
public class AllTests {
}
