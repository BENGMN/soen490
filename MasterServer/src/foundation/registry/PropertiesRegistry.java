/**
 * SOEN 490
 * Capstone 2011
 * Team members: 	
 * 			Sotirios Delimanolis
 * 			Filipe Martinho
 * 			Adam Harrison
 * 			Vahe Chahinian
 * 			Ben Crudo
 * 			Anthony Boyer
 * 
 * @author Capstone 490 Team Moving Target
 *
 */
package foundation.registry;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.Logger;
import exceptions.PropertyNotFoundException;

/** 
 * This class serves as a accessor for all the properties in the properties file.
 * Influenced by Stuart Thiel's SoenEA2 library. 
 */
public class PropertiesRegistry {
	private static Properties properties = null;	
	
	// File path to properties file
	private static final String FILENAME = "MyProperties.properties";
	
	static {
		Logger logger = (Logger) LoggerFactory.getLogger("application");		
		
		try {
			InputStream in = PropertiesRegistry.class.getClassLoader().getResourceAsStream(FILENAME);
			// Loads properties file
			properties = new Properties();
			properties.load(in);
			
			logger.info("Properties file opened in PropertiesRegistry static block.");

		} catch (IOException e) {
			String error = "File '" + FILENAME + "' could not be found.";
			logger.error(error);
		} catch (Exception e) {
			logger.error("Something unexpected occurred.");
		}
	}
	
	public static String getProperty(String key) throws PropertyNotFoundException {
		String property = null;
		property = properties.getProperty(key);
		if (property == null) 
			throw new PropertyNotFoundException("Property '" + key + "' was not found.");
		return property;
	}
	
	public static Integer getInt(String key) throws NumberFormatException, PropertyNotFoundException {
		return Integer.parseInt(getProperty(key));
	}
	
	public static Double getDouble(String key) throws NumberFormatException, PropertyNotFoundException {
		return Double.parseDouble(getProperty(key));
	}
	
}
