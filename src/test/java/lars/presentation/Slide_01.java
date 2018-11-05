package lars.presentation;

import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import static java.nio.charset.StandardCharsets.UTF_8;

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
    void in_loops() {
        var numbers = List.of(1, 2, 3, 4, 5);

        for (var number : numbers) {
            System.out.println(number);
        }
    }

    @Test
    void in_try_declarations() {
        try (var file = new FileInputStream(new File("passwords.txt"))) {

            new BufferedReader(new InputStreamReader(file, UTF_8))
                    .lines()
                    .forEach(System.out::println);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void careful_when_updating() {
        ConcurrentMap<String, String> map = new ConcurrentHashMap<>();
        //map.mappingCount();
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
