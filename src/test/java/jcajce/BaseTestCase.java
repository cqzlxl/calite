package jcajce;

import java.security.Security;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import junit.framework.TestCase;


public class BaseTestCase extends TestCase {
	@Override
	public void setUp() throws Exception {
		Security.addProvider(new BouncyCastleProvider());
	}
}
