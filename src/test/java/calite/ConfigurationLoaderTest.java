package calite;

import org.apache.commons.configuration.Configuration;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import junit.framework.TestCase;


public class ConfigurationLoaderTest extends TestCase {
	private static Logger logger = LoggerFactory.getLogger(ConfigurationLoaderTest.class);
    private static Configuration config = ConfigurationLoader.load();;


	@Test
	public void testKeyHit() {
		String appName = config.getString("app.name");
		assertEquals("calite", appName);
		logger.debug("got app name from config: {}", appName);
	}


	@Test
	public void testKeyMiss() {
		String appSign = config.getString("app.sign");
		assertNull(appSign);

		appSign = config.getString("app.sign", "No such sign");
		assertEquals("No such sign", appSign);
	}
}
