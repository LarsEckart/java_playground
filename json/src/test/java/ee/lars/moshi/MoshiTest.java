package ee.lars.moshi;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import ee.lars.json.BagOfPrimitives;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;


public class MoshiTest {

    private Moshi moshi;

    @Before
    public void initialize() throws Exception {
        this.moshi = new Moshi.Builder().build();
    }

    @Test
    public void converts_simple_string() throws Exception {
        // given
        JsonAdapter<String> jsonAdapter = moshi.adapter(String.class);

        // when
        final String json = jsonAdapter.toJson("hello world");

        // then
        assertThat(json).isEqualTo("\"hello world\"");
    }

    @Test
    public void converts_bag_of_primitives() throws Exception {
        // given
        BagOfPrimitives obj = new BagOfPrimitives();
        JsonAdapter<BagOfPrimitives> jsonAdapter = moshi.adapter(BagOfPrimitives.class);

        // when
        String json = jsonAdapter.toJson(obj);

        // then
        assertThat(json).isEqualTo("{\"value1\":1,\"value2\":\"abc\",\"value3\":0}");
    }

}
