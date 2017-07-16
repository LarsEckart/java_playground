package ee.lars.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.Types;

import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.security.Signature;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Base64;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * TODO: take care of padding
 */
public class Main {

    public static void main(String[] args) throws Exception {

        System.out.println(new JwtExample().createJwt());
    }

    static class JwtExample {

        public String createJwt() throws Exception {

            String headerPart = createHeader();
            String payloadPart = createPayload();
            String signature = createSignature(headerPart, payloadPart);

            return String.join(".", headerPart, payloadPart, signature);
        }

        private String createHeader() {
            Map<String, Object> headerMap = new LinkedHashMap<>();
            headerMap.put("typ", "JWT");
            headerMap.put("alg", "RS256");
            final Moshi moshi = new Moshi.Builder().build();
            Type type = Types.newParameterizedType(Map.class, String.class, Object.class);
            final JsonAdapter<Map<String, Object>> jsonAdapter = moshi.adapter(type);
            final Base64.Encoder urlEncoder = Base64.getUrlEncoder();
            final String headerJson = jsonAdapter.toJson(headerMap);
            final byte[] headerEncoded = urlEncoder.encode(headerJson.getBytes(StandardCharsets.UTF_8));
            final String headerPart = new String(headerEncoded);
            return headerPart;
        }

        private String createPayload() {
            Map<String, Object> payload = new LinkedHashMap<>();
            payload.put("hello", "world");
            final Moshi moshi = new Moshi.Builder().build();
            Type type = Types.newParameterizedType(Map.class, String.class, Object.class);
            final JsonAdapter<Map<String, Object>> jsonAdapter = moshi.adapter(type);
            final Base64.Encoder urlEncoder = Base64.getUrlEncoder();
            final String payloadJson = jsonAdapter.toJson(payload);
            final byte[] payloadEncoded = urlEncoder.encode(payloadJson.getBytes(StandardCharsets.UTF_8));
            final String payloadPart = new String(payloadEncoded);
            return payloadPart;
        }

        private String createSignature(String headerPart, String payloadPart) throws Exception {

            final ClassLoader classLoader = getClass().getClassLoader();
            final String privateKeyFile = classLoader.getResource("private_key.der").getFile();

            RSAPrivateKey privateKey = (RSAPrivateKey) PrivateKeyReader.get(privateKeyFile);

            final String headerAndData = String.join(".", headerPart, payloadPart);
            final Signature signer = Signature.getInstance("SHA256withRSA");
            signer.initSign(privateKey);
            signer.update(headerAndData.getBytes());
            final byte[] signature = signer.sign();
            final Base64.Encoder urlEncoder = Base64.getUrlEncoder();
            final byte[] encodedSig = urlEncoder.encode(signature);
            final String signaturePart = new String(encodedSig, StandardCharsets.UTF_8);
            return signaturePart;
        }

        public void javaJwtExample() throws Exception {
            final ClassLoader classLoader = getClass().getClassLoader();
            final String publicKeyFile = classLoader.getResource("public_key.der").getFile();
            final String privateKeyFile = classLoader.getResource("private_key.der").getFile();

            RSAPublicKey publicKey = (RSAPublicKey) PublicKeyReader.get(publicKeyFile);
            RSAPrivateKey privateKey = (RSAPrivateKey) PrivateKeyReader.get(privateKeyFile);

            try {
                Algorithm signAlgo = Algorithm.RSA256(null, privateKey);
                String token = JWT.create()
                                  .withClaim("hello", "world")
                                  .sign(signAlgo);

                System.out.println(token);
                final String[] parts = token.split("\\.");
                final String header = parts[0];
                final String data = parts[1];
                System.out.println("header: " + new String((Base64.getUrlDecoder().decode(header))));
                System.out.println("data: " + new String(Base64.getUrlDecoder().decode(data)));

                Algorithm verifyAlgo = Algorithm.RSA256(publicKey, null);

                JWTVerifier verifier = JWT.require(verifyAlgo)
                                          .build();

                DecodedJWT jwt = verifier.verify(token);
            } catch (JWTCreationException exception) {
                System.out.println(exception.getMessage());
            }
        }
    }
}