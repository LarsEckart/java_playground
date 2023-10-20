package lars.spielplatz.jackson;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

class SerializeToJson {

  @ParameterizedTest
  @MethodSource("createPerson")
  void name(Object person) throws JsonProcessingException {
    ObjectMapper objectMapper = new ObjectMapper();

    String json = objectMapper.writeValueAsString(person);

    ArrayList<String> objects = new ArrayList<>();
    objects.add(null);

    assertThat(json).isEqualTo("{\"name\":\"Lars\",\"age\":34}");
  }

  private static Stream<Object> createPerson() {
    return Stream.of(new PersonPublicFields("Lars", 34), new PersonPrivateFields("Lars", 34));
  }

  static class PersonPublicFields {
    public final String name;
    public final int age;

    public PersonPublicFields(String name, int age) {
      this.name = name;
      this.age = age;
    }
  }

  static class PersonPrivateFields {
    private final String name;
    private final int age;

    public PersonPrivateFields(String name, int age) {
      this.name = name;
      this.age = age;
    }

    public String getName() {
      return name;
    }

    public int getAge() {
      return age;
    }
  }
}
