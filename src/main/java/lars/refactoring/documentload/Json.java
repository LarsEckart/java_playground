package lars.refactoring.documentload;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;

class Json {

  public static ObjectMapper mapper() {
    JsonFactory f = new JsonFactory().enable(JsonParser.Feature.ALLOW_COMMENTS);
    return new ObjectMapper(f);
  }
}
