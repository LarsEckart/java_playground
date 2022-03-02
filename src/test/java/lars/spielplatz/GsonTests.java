package lars.spielplatz;

import org.junit.jupiter.api.Test;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import static org.assertj.core.api.Assertions.assertThat;

class GsonTests {

    @Test
    void gson_plain() {
        String json = """
                {\"name\":\"Lars\"}""";
        JsonElement jsonElement = JsonParser.parseString(json);

        JsonObject jsonObject = jsonElement.getAsJsonObject();

        assertThat(jsonObject.get("name").getAsString()).isEqualTo("Lars");
    }

    @Test
    void gson_object() {
        String json = """
                {\"name\":\"Lars\"}""";
        JsonElement jsonElement = JsonParser.parseString(json);

        Gson gson = new Gson();

        Person person = gson.fromJson(jsonElement.toString(), Person.class);

        assertThat(person.getName()).isEqualTo("Lars");
    }

    static class Person {

        private final String name;

        public Person(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }
}
