package ee.lars;

import org.junit.Test;

import java.util.AbstractMap;

import static org.assertj.core.api.Assertions.assertThat;

public class JavaPair {

    @Test
    public void pair_class_in_pure_java() throws Exception {
        AbstractMap.SimpleEntry<String, String> pair = new AbstractMap.SimpleEntry<>("k", "v");

        assertThat(pair.getKey()).isEqualTo("k");
        assertThat(pair.getValue()).isEqualTo("v");
    }
}
