
package com.example.currFair.api.util;

import java.net.URL;
import java.util.Properties;

import org.apache.commons.configuration.ConfigurationConverter;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.log4j.Logger;

public class PropertiesUtil {
	
	static final Logger logger = Logger.getLogger(PropertiesUtil.class);
   
	private static PropertiesUtil instance = new PropertiesUtil();

	private PropertiesUtil() {  }

	public static PropertiesUtil getInstance() {
		return instance;
	}

	public Properties loadProperties(String propsName) {
		PropertiesConfiguration configuration = null;
		try {
			URL url = getClass().getResource(propsName);
			if (url == null)
				configuration = new PropertiesConfiguration(propsName); // propsName is an absolute path to the file.
			else
				configuration = new PropertiesConfiguration(url); // propsName is a path to the file and was found in the classpath.
			return ConfigurationConverter.getProperties(configuration);
		} catch (Exception e) {
			logger.error(String.format("Error loading Properties file %s.", propsName), e);
			throw new RuntimeException(e);
		}
	}
	
}
