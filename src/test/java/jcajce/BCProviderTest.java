package jcajce;

import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Provider;
import java.security.Security;
import java.util.TreeSet;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class BCProviderTest extends BaseTestCase {
	private static final Logger logger = LoggerFactory.getLogger(BCProviderTest.class);


	@Test
    public void testInstalled() {
		assertNotNull(Security.getProvider("BC"));
    }


	@Test
	public void testGetCipher() throws NoSuchAlgorithmException, NoSuchPaddingException, NoSuchProviderException {
		Cipher cipher1 = Cipher.getInstance("Blowfish/ECB/NoPadding");
		Cipher cipher2 = Cipher.getInstance("Blowfish/ECB/NoPadding", "BC");

		logger.debug("cipher1.provider={}, cipher2.provider={}\n", cipher1.getProvider(), cipher2.getProvider());
	}


	@Test
	public void testBCCapabilites() {
        Provider provider = Security.getProvider("BC");
        assertNotNull(provider);

        final String aliasPrefix = "Alg.Alias.";
        for (Object k: new TreeSet<Object>(provider.keySet())) {
            String entry = (String) k;
            // this indicates the entry actually refers to another entry
            if (entry.startsWith(aliasPrefix)) {
                entry = entry.substring(aliasPrefix.length());
            }

            String[] parts = entry.split("\\.", 2);
            assertEquals(2, parts.length);

            String factoryClass =  parts[0];
            String name = parts[1];

            logger.debug("{}: {}", factoryClass, name);
        }
	}
}
