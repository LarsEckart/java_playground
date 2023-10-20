package lars.spielplatz;

import static org.slf4j.LoggerFactory.getLogger;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import org.apache.commons.codec.binary.Hex;
import org.slf4j.Logger;

public class ObfuscationUtil {

  private static final Logger log = getLogger(ObfuscationUtil.class);

  /**
   * takes a string. runs it through cryptographic secure hash. then uses apache commons to encode
   * it to hex string.
   */
  public static String obfuscateData(String data) {
    try {
      MessageDigest digest = MessageDigest.getInstance("SHA-256");
      byte[] encodedHash = digest.digest(data.getBytes(StandardCharsets.UTF_8));
      return new String(Hex.encodeHex(encodedHash));
    } catch (Exception e) {
      log.error("error hashing data, outputting empty string instead");
      return "";
    }
  }
}
