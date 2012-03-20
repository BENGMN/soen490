package tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import foundation.finder.GeoSpatialSearch;

import tests.foundation.*;

@RunWith(Suite.class)

@SuiteClasses({ 
		 DatabaseTest.class,
		 MessageTDGTest.class,
		 UserTDGTest.class,
		 ServerListTDGTest.class,
		 GeoSpatialSearch.class,
})

public class FoundationTests {

}
