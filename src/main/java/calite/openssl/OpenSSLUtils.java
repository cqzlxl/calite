package calite.openssl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.ArrayUtils;

public class OpenSSLUtils {
	public static byte[] generateKeyPair(int nbits) {
		return callOpenSSL("genrsa", "-aes256", "-passout", "pass:888888", String.valueOf(nbits));
	}


	private static byte[] callOpenSSL(String subCommand, String...args) {
		String[] base = new String[] {"openssl", subCommand};
		String[] all = ArrayUtils.addAll(base, args);

		List<Byte> result = new ArrayList<>();
		CommandExecutor.execute(all, result);

		return ArrayUtils.toPrimitive(result.toArray(new Byte[0]));
	}
}
