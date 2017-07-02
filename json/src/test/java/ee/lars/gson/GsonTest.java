package ee.lars.gson;

import com.google.gson.Gson;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class GsonTest {

    private Gson gson;

    @Before
    public void initialize() throws Exception {
        this.gson = new Gson();
    }

    @Test
    public void converts_primitive_int() throws Exception {
        // given

        // when
        final String json = gson.toJson(1);

        // then
        assertThat(json).isEqualTo("1");
    }

    @Test
    public void converts_simple_string() throws Exception {
        // given

        // when
        final String json = gson.toJson("hello world");

        // then
        assertThat(json).isEqualTo("\"hello world\"");
    }

    @Test
    public void converts_primitive_array() throws Exception {
        // given
        int[] values = {1, 2, 3};

        // when
        final String json = gson.toJson(values);

        // then
        assertThat(json).isEqualTo("[1,2,3]");
    }

    @Test
    public void converts_bag_of_primitives() throws Exception {
        // given
        BagOfPrimitives obj = new BagOfPrimitives();

        // when
        String json = gson.toJson(obj);

        // then
        assertThat(json).isEqualTo("{\"value1\":1,\"value2\":\"abc\"}");
    }

    @Test
    public void converts_bag_with_final_field() throws Exception {
        // given
        ImmutableBag obj = new ImmutableBag("Oskar");

        // when
        String json = gson.toJson(obj);

        // then
        assertThat(json).isEqualTo("{\"name\":\"Oskar\"}");
    }

    @Test
    public void converts_json_to_bag_with_final_field() throws Exception {
        // given
        String json = "{\"name\":\"Oskar\"}";

        // when
        final ImmutableBag obj = gson.fromJson(json, ImmutableBag.class);

        // then
        assertThat(obj.getName()).isEqualTo("Oskar");
    }

    @Test
    public void ignores_transient_fields() throws Exception {
        // given
        BagWithTransientField obj = new BagWithTransientField();

        // when
        String json = gson.toJson(obj);

        // then
        assertThat(json).isEqualTo("{\"value1\":\"hello\"}");
    }

    @Test
    public void ignores_null_fields() throws Exception {
        // given
        BagWithNull obj = new BagWithNull();

        // when
        String json = gson.toJson(obj);

        // then
        assertThat(json).isEqualTo("{\"value1\":\"hello\"}");
    }

    class BagOfPrimitives {

        private int value1 = 1;
        private String value2 = "abc";

        BagOfPrimitives() {
        }
    }

    class BagWithNull {

        private String value1 = "hello";
        private String value2 = null;

        BagWithNull() {
        }
    }

    class ImmutableBag {

        private final String name;

        public ImmutableBag(String name) {
            this.name = name;
        }

        public String getName() {
            return this.name;
        }
    }

    class BagWithTransientField {

        private String value1 = "hello";
        private transient String value2 = "world";

        public BagWithTransientField() {
        }
    }
}
