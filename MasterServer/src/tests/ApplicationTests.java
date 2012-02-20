package tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import tests.application.*;

@RunWith(Suite.class)

@SuiteClasses({
		FrontControllerTest.class, 
		HttpMessageTest.class,
		UnknownCommandTest.class,
		MessageCommandTest.class		 
})

public class ApplicationTests {

}
