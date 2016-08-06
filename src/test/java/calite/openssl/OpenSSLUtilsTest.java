package calite.openssl;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import junit.framework.TestCase;


public class OpenSSLUtilsTest extends TestCase {
	private static Logger logger = LoggerFactory.getLogger(OpenSSLUtilsTest.class);


	@Test
	public void testgenerateKeyPair() {
		byte[] pair = OpenSSLUtils.generateKeyPair(8192);
		logger.debug("new key pair:\n{}", new String(pair));
	}
}
