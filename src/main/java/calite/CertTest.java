package calite;
import java.io.FileOutputStream;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.cert.X509Certificate;
import java.util.Date;

import org.junit.Test;

import junit.framework.TestCase;
// import sun.security.tools.keytool.CertAndKeyGen; // Use this for Java 8 and above
import sun.security.x509.CertAndKeyGen;
import sun.security.x509.X500Name;


public class CertTest extends TestCase {
    private static final int keysize = 1024;
    private static final String commonName = "www.test.cn";
    private static final String organizationalUnit = "IT";
    private static final String organization = "test";
    private static final String city = "Beijing";
    private static final String state = "Beijing";
    private static final String country = "CN";
    private static final long validity = 365 * 10;
    private static final String alias = "root";
    private static final char[] keyPass = "888888".toCharArray();


    @Test
    @SuppressWarnings("restriction")
    public void testGenRootCert() throws Exception {
        KeyStore keyStore = KeyStore.getInstance("JKS");
        keyStore.load(null, null);

        CertAndKeyGen keypair = new CertAndKeyGen("RSA", "SHA1WithRSA", null);

        X500Name x500Name = new X500Name(commonName, organizationalUnit, organization, city, state, country);

        keypair.generate(keysize);
        PrivateKey privKey = keypair.getPrivateKey();

        X509Certificate[] chain = new X509Certificate[1];

        chain[0] = keypair.getSelfCertificate(x500Name, new Date(), validity * 24 * 60 * 60);

        keyStore.setKeyEntry(alias, privKey, keyPass, chain);
        keyStore.store(new FileOutputStream(".keystore"), keyPass);
    }
}