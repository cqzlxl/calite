package calite.openssl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;


public class Command implements Serializable {
	private static final long serialVersionUID = 1L;

	private final String program;
	private final List<String> arguments = new ArrayList<>();


	public Command(String program) {
		this.program = program;
	}


	public void addArgument(String name, String value) {
		arguments.add(name);
		if (value != null) {
			arguments.add(value);
		}
	}

	public void addArgument(String arg) {
		arguments.add(arg);
	}


	public String getProgram() {
		return program;
	}

	public List<String> getArguments() {
		List<String> copy = new ArrayList<>();
		copy.addAll(arguments);
		return copy;
	}


	@Override
	public String toString() {
		List<String> parts = new ArrayList<>();
		parts.add(program);

		for (String p: arguments) {
			parts.add(p);
		}

		return StringUtils.join(parts, " ");
	}
}
