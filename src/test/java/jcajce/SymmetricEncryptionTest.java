package jcajce;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Hex;
import org.bouncycastle.util.Arrays;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class SymmetricEncryptionTest extends BaseTestCase {
	private static final Logger logger = LoggerFactory.getLogger(SymmetricEncryptionTest.class);


	@Test
    public void testAes() throws Exception {
        byte[] input = new byte[] {
	                      0x00, 0x11, 0x22, 0x33, 0x44, 0x55, 0x66, 0x77,
	                      (byte)0x88, (byte)0x99, (byte)0xaa, (byte)0xbb,
	                      (byte)0xcc, (byte)0xdd, (byte)0xee, (byte)0xff
                       };
        byte[] keyBytes = new byte[] {
		                      0x00, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07,
		                      0x08, 0x09, 0x0a, 0x0b, 0x0c, 0x0d, 0x0e, 0x0f,
		                      0x10, 0x11, 0x12, 0x13, 0x14, 0x15, 0x16, 0x17
                          };

        String hexInput = Hex.encodeHexString(input);
        String hexKeyBytes = Hex.encodeHexString(keyBytes);
        logger.debug("input: {}", hexInput);
        logger.debug("key: {}", hexKeyBytes);

        SecretKeySpec key = new SecretKeySpec(keyBytes, "AES");
        Cipher cipher = Cipher.getInstance("AES/ECB/NoPadding", "BC");

        // encryption pass
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] encypted = new byte[input.length];
        int enlen = 0;
        enlen += cipher.update(input, 0, input.length, encypted, 0);
        enlen += cipher.doFinal(encypted, enlen);
        logger.debug("Encrypted: {}({})", Hex.encodeHexString(encypted), enlen);

        // decryption pass
        cipher.init(Cipher.DECRYPT_MODE, key);
        byte[] decrypted = new byte[enlen];
        int delen = 0;
        delen += cipher.update(encypted, 0, enlen, decrypted, 0);
        delen += cipher.doFinal(decrypted, delen);
        String hexDecrypted = Hex.encodeHexString(decrypted);
        assertEquals(hexInput, Hex.encodeHexString(decrypted));
        logger.debug("Decrypted: {}({})", hexDecrypted, delen);
    }


	@Test
	public void testPKCS7() throws Exception {
		byte[] input = new byte[] {
				0x00, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07,
				0x08, 0x09, 0x0a, 0x0b, 0x0c, 0x0d, 0x0e, 0x0f,
				0x10, 0x11, 0x12, 0x13, 0x14, 0x15, 0x16, 0x17
	    };
		byte[] keyBytes = new byte[] {
				0x00, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07,
				0x08, 0x09, 0x0a, 0x0b, 0x0c, 0x0d, 0x0e, 0x0f,
				0x10, 0x11, 0x12, 0x13, 0x14, 0x15, 0x16, 0x17
	    };

        String hexInput = Hex.encodeHexString(input);
        String hexKeyBytes = Hex.encodeHexString(keyBytes);
        logger.debug("input: {}", hexInput);
        logger.debug("key: {}", hexKeyBytes);

		SecretKeySpec key = new SecretKeySpec(keyBytes, "AES");
		// need padding
		Cipher cipher = Cipher.getInstance("AES/ECB/PKCS7Padding", "BC");

		cipher.init(Cipher.ENCRYPT_MODE, key);
		int sizeGuess = cipher.getOutputSize(input.length);
        byte[] encypted = new byte[sizeGuess];

        int enlen = 0;
        enlen += cipher.update(input, 0, input.length, encypted, 0);
        enlen += cipher.doFinal(encypted, enlen);
        assertTrue(sizeGuess >= enlen);
        logger.debug("Encrypted: {}({})", Hex.encodeHexString(encypted), enlen);

		// decryption pass
        cipher.init(Cipher.DECRYPT_MODE, key);
        sizeGuess = cipher.getOutputSize(enlen);
        byte[] decrypted = new byte[sizeGuess];
        int delen = 0;
        delen += cipher.update(encypted, 0, enlen, decrypted, 0);
        delen += cipher.doFinal(decrypted, delen);
        assertTrue(sizeGuess >= delen);
        decrypted = Arrays.copyOfRange(decrypted, 0, delen);
        String hexDecrypted = Hex.encodeHexString(decrypted);
        assertEquals(hexInput, Hex.encodeHexString(decrypted));
        logger.debug("Decrypted: {}({})", hexDecrypted, delen);
	}
}