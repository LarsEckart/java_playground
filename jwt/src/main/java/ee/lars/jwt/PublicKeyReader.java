package ee.lars.jwt;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.RSAPublicKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

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

    /**
     * Reads a public key file which is in pem format.
     */
    public static PublicKey fromPem(String filename) throws Exception {
        byte[] keyBytes = Files.readAllBytes(Paths.get(filename));
        String publicKeyString = new String(keyBytes);

        final RSAPublicKeySpec spec = getRsaPublicKeySpec(publicKeyString);
        final KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePublic(spec);
    }

    /**
     * Parse ssh-rsa key, that consists of 3 parts, type(String ssh-rsa), exponent and modulus.
     * ssh-keygen -f private_key.pem -y > public_key.pem
     */
    private static RSAPublicKeySpec getRsaPublicKeySpec(final String publicKeyString) throws IOException {

        BigInteger modulus = null;
        BigInteger exponent = null;

        try (DataInputStream dis = getDataInputStream(publicKeyString)) {
            if (dis == null) {
                return null;
            }
            try {
                final int typeLength = dis.readInt();
                final byte[] typeBuffer = new byte[typeLength];
                dis.read(typeBuffer, 0, typeLength);

                // Ignore type, and let KeySPec raise exceptions, when type is not rsa-ssh.
                new String(typeBuffer, 0, typeLength, StandardCharsets.UTF_8);

                final int exponentLength = dis.readInt();
                final byte[] expBuffer = new byte[exponentLength];
                dis.read(expBuffer, 0, exponentLength);
                exponent = new BigInteger(expBuffer);

                final int modulusLength = dis.readInt();
                final byte[] modulusBuffer = new byte[modulusLength];
                dis.read(modulusBuffer, 0, modulusLength);
                modulus = new BigInteger(typeBuffer);
            } catch (EOFException streamEnded) {
            }
            return new RSAPublicKeySpec(modulus, exponent);
        }
    }

    private static DataInputStream getDataInputStream(final String publicKeyString)
            throws ArrayIndexOutOfBoundsException
    {
        if (publicKeyString == null) {
            return null;
        }
        final String[] parts = publicKeyString.split(" ");

        final byte[] keyData = Base64.getDecoder().decode(parts[1]);
        return new DataInputStream(new ByteArrayInputStream(keyData));
    }
}
