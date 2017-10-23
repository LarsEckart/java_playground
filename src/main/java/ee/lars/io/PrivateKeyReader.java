package ee.lars.io;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.spec.EncodedKeySpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;

public class PrivateKeyReader {

    /**
     * Reads a private key file which is in PKCS#8 format.
     * For example create a private key via
     * openssl genrsa -out private_key.pem 2048
     * and then convert it via
     * openssl pkcs8 -topk8 -inform PEM -outform DER -in private_key.pem -out private_key.der -nocrypt
     */
    public static PrivateKey get(String filename)
            throws IOException, NoSuchAlgorithmException, InvalidKeySpecException
    {
        byte[] keyBytes = Files.readAllBytes(Paths.get(filename));

        EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        final PrivateKey privateKey = keyFactory.generatePrivate(keySpec);
        return privateKey;
    }
}
