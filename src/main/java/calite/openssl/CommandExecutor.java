package calite.openssl;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class CommandExecutor {
	private static Logger logger = LoggerFactory.getLogger(CommandExecutor.class);


	public static boolean execute(Command command, List<Byte> result) {
		logger.info("execute command: {}", command);

		List<String> cmdline = new ArrayList<>();
		cmdline.add(command.getProgram());
		cmdline.addAll(command.getArguments());

		int exitCode = 1;
		try {
			ProcessBuilder builder = new ProcessBuilder(cmdline);
			Process process = builder.start();
			InputStream stdout = process.getInputStream();
			InputStream stderr = process.getErrorStream();

			byte[] out = IOUtils.toByteArray(stdout);
			result.addAll(Arrays.asList(ArrayUtils.toObject(out)));

			List<String> err = IOUtils.readLines(stderr);
			if (err.size() > 0) {
				logger.debug(StringUtils.join(err, System.lineSeparator()));
			}

			exitCode = process.waitFor();
		} catch (IOException e) {
			logger.error("execute command, start", e);
		} catch (InterruptedException e) {
			logger.error("execute command, wait finish", e);
		}

		boolean succeeded = exitCode == 0;
		logger.info("execute command: {}, succeeded? {}", command, succeeded);
		return succeeded;
	}


	public static boolean execute(String[] command, List<Byte> result) {
		Command cmd = new Command(command[0]);
		for (int i = 1; i < command.length; ++i) {
			cmd.addArgument(command[i]);
		}

		return execute(cmd, result);
	}
}
