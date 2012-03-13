package tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import foundation.finder.GeoSpatialSearch;

import technical.*;

@RunWith(Suite.class)

@SuiteClasses({ 
		 GeoSpatialSearch.class,
})

public class TechnicalTests {

}