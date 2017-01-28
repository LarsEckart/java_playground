package ee.tublipoiss.explore;

import com.google.gson.Gson;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class GsonExplorer {

    @Test
    public void does_gson_require_empty_args_constructor() throws Exception {
        // given
        final String json = "{\"name\":\"Lars\"}";
        final Gson gson = new Gson();

        // when
        final AnyClass anyClass = gson.fromJson(json, AnyClass.class);

        // then
        assertThat(anyClass.getName()).isEqualTo("Lars");
    }

    class AnyClass {

        private final String name;

        public AnyClass(String name) {
            this.name = name;
        }

        public String getName() {
            return this.name;
        }
    }
}
