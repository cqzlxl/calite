package calite.openssl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.ArrayUtils;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import junit.framework.TestCase;


public class CommandExecutorTest extends TestCase {
	private static Logger logger = LoggerFactory.getLogger(CommandExecutorTest.class);


	@Test
	public void testAll() {
		List<Byte> result = new ArrayList<Byte>();
		boolean succeeded = CommandExecutor.execute(new String[]{"ls", "-l", "-a", "/"}, result);
		assertTrue(succeeded);
		logger.debug("{}", new String(ArrayUtils.toPrimitive(result.toArray(new Byte[0]))));

		result.clear();
		succeeded = CommandExecutor.execute(new String[]{"ls", "-ls", "-a", "/not-exists-item"}, result);
		assertFalse(succeeded);
	}
}
