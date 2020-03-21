package lars.presentation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.junit.jupiter.api.Test;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.assertj.core.api.Assertions.assertThat;

public class Slide_01 {

  @Test
  void before() throws Exception {
    URL tartu = new URL("https://tartu.ee/");
    URLConnection connection = tartu.openConnection();
    Reader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), UTF_8));
  }

  @Test
  void local_variable_type_inference() throws Exception {
    var tartu = new URL("https://tartu.ee/");
    var connection = tartu.openConnection();
    var reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), UTF_8));
  }

  @Test
  void more_examples() {
    var name = "Lars";
    var solution = 42L;
    var evenNumbers = List.of(2, 4, 6, 8);
    var oddNumbers = new ArrayList<>();

    evenNumbers = new ArrayList<>();
    // oddNumbers = List.of(1, 3, 5, 7, 9);
  }

  @Test
  void still_programming_to_the_interface() {
    assertThat(getNames()).containsExactly("Lars");
  }

  private List<String> getNames() {
    var list = new ArrayList<String>();
    // ...
    list.add("Lars");
    // ...
    return list;
  }

  @Test
  void in_loops() {
    var numbers = List.of(1, 2, 3, 4, 5);

    for (var number : numbers) {
      System.out.println(number);
    }
  }

  @Test
  void in_try_declarations() {
    try (var inputStream = Files.newInputStream(Paths.get("passwords.txt"));
        var inputStreamReader = new InputStreamReader(inputStream, UTF_8);
        var bufferedReader = new BufferedReader(inputStreamReader)) {

      bufferedReader.lines().forEach(System.out::println);
    } catch (IOException e) {
    }
  }

  @Test
  void careful_when_updating() {
    ConcurrentMap<String, String> map = new ConcurrentHashMap<>();
    // map.mappingCount();
    map.put("key", "value");
    String value = map.get("key");
  }

  @Test
  void maybe_do_not_do_this() {
    var names = getAll();
  }

  static <T> Collection<T> getAll() {
    Collection<T> c = new ArrayList<>();
    return c;
  }
}
