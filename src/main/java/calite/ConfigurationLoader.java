package calite;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class ConfigurationLoader {
	private static final Logger logger = LoggerFactory.getLogger(ConfigurationLoader.class);
	private static final String configFile = "calite.properties";
	private static Configuration config;


	static {
		try {
			config = new PropertiesConfiguration(configFile);
		} catch (ConfigurationException e) {
			logger.warn("{}, when try to load from {}", e.getMessage(), configFile);
			config = new PropertiesConfiguration();
		}
	}


	public static Configuration load() {
		return config;
	}


	private ConfigurationLoader() {}
}
