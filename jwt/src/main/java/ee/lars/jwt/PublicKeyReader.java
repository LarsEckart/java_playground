package ee.lars.jwt;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;

public class PublicKeyReader {

    /**
     * Reads a public key file which is in PKCS#8 format.
     * For example create a private key via
     * openssl genrsa -out private_key.pem 2048
     * and then create a public key file via
     * openssl rsa -in private_key.pem -pubout -outform DER -out public_key.der
     */
    public static PublicKey get(String filename) throws Exception {
        byte[] keyBytes = Files.readAllBytes(Paths.get(filename));

        X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        final PublicKey publicKey = kf.generatePublic(spec);
        return publicKey;
    }
}
