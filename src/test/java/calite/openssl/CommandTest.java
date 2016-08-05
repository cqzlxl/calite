package calite.openssl;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import junit.framework.TestCase;


public class CommandTest extends TestCase {
	private static Logger logger = LoggerFactory.getLogger(CommandTest.class);


	@Test
	public void testAll() {
		Command cmd = new Command("openssl");
		logger.info("Program for now: {}", cmd);

		cmd.addArgument("ca");
		logger.info("Program for now: {}", cmd);

		cmd.addArgument("-config", "ca.conf");
		logger.info("Program for now: {}", cmd);

		cmd.addArgument("-gencrl", null);
		logger.info("Program for now: {}", cmd);

		cmd.addArgument("-out", "ca.crl.pem");
		logger.info("Program for now: {}", cmd);

		assertEquals("openssl ca -config ca.conf -gencrl -out ca.crl.pem", cmd.toString());
	}
}
