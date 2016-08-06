package jcajce;

import java.security.InvalidKeyException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.junit.Test;


public class SimpleJCEPolicyTest extends BaseTestCase {
	private final byte[] data = { 0x00, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07 };
	private Cipher cipher;


	@Override
	public void setUp() throws Exception {
		super.setUp();
		cipher = Cipher.getInstance("Blowfish/ECB/NoPadding");
	}


	@Test
	public void testWeekNeed() throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        // create a 64 bit secret key from raw bytes
        SecretKey key64 = new SecretKeySpec(
            new byte[] { 0x00, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07 },
            "Blowfish"
        );

        cipher.init(Cipher.ENCRYPT_MODE, key64);
        cipher.doFinal(data);
	}


	@Test
    public void testStrongNeed() throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        // create a 192 bit secret key from raw bytes
        SecretKey key192 = new SecretKeySpec(
	        new byte[] { 0x00, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07,
	                     0x08, 0x09, 0x0a, 0x0b, 0x0c, 0x0d, 0x0e, 0x0f,
	                     0x10, 0x11, 0x12, 0x13, 0x14, 0x15, 0x16, 0x17 },
	        "Blowfish"
        );

        // now try encrypting with the larger key, need unlimited policy
        cipher.init(Cipher.ENCRYPT_MODE, key192);
        cipher.doFinal(data);
    }
}
