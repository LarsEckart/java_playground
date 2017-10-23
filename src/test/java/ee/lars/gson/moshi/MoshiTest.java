package ee.lars.gson.moshi;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.Types;
import ee.lars.json.BagOfPrimitives;
import ee.lars.json.BagWithNull;
import ee.lars.json.BagWithTransientField;
import ee.lars.json.Card;
import ee.lars.json.ImmutableBag;
import ee.lars.json.Suit;
import ee.lars.json.moshi.CardAdapter;
import java.io.EOFException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static ee.lars.json.Suit.HEARTS;
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

    @Test
    public void converts_json_to_bag_with_final_field() throws Exception {
        // given
        String json = "{\"name\":\"Oskar\"}";
        JsonAdapter<ImmutableBag> jsonAdapter = moshi.adapter(ImmutableBag.class);

        // when
        final ImmutableBag obj = jsonAdapter.fromJson(json);

        // then
        assertThat(obj.getName()).isEqualTo("Oskar");
    }

    @Test
    public void ignores_transient_fields() throws Exception {
        // given
        BagWithTransientField obj = new BagWithTransientField();
        JsonAdapter<BagWithTransientField> jsonAdapter = moshi.adapter(BagWithTransientField.class);

        // when
        String json = jsonAdapter.toJson(obj);

        // then
        assertThat(json).isEqualTo("{\"value1\":\"hello\"}");
    }

    @Test
    public void ignores_null_fields() throws Exception {
        // given
        BagWithNull obj = new BagWithNull();
        JsonAdapter<BagWithNull> jsonAdapter = moshi.adapter(BagWithNull.class);

        // when
        String json = jsonAdapter.toJson(obj);

        // then
        assertThat(json).isEqualTo("{\"value1\":\"hello\"}");
    }

    @Test
    public void throws_np_exception_when_parsing_null() throws Exception {
        // given
        String json = null;
        JsonAdapter<BagOfPrimitives> jsonAdapter = moshi.adapter(BagOfPrimitives.class);

        try {
            //when
            BagOfPrimitives obj = jsonAdapter.fromJson(json);

            //then
            Assert.fail();
        } catch (NullPointerException expected) {
        }
    }

    @Test
    public void throws_eof_exception_when_empty_string() throws Exception {
        // given
        String json = "";
        JsonAdapter<BagOfPrimitives> jsonAdapter = moshi.adapter(BagOfPrimitives.class);

        try {
            //when
            BagOfPrimitives obj = jsonAdapter.fromJson(json);

            //then
            Assert.fail();
        } catch (EOFException expected) {
        }
    }

    @Test
    public void parses_empty_json_string_to_default_primitive_or_null_for_non_primitives()
            throws Exception {
        // given
        String json = "{}";
        JsonAdapter<BagOfPrimitives> jsonAdapter = moshi.adapter(BagOfPrimitives.class);

        // when
        BagOfPrimitives obj = jsonAdapter.fromJson(json);

        // then
        assertThat(obj).isNotNull();
        assertThat(obj.getValue3()).isEqualTo(0);
        assertThat(obj.getValue4()).isNull();
    }

    @Test
    public void custom_type_adapter() throws Exception {
        // given
        moshi = new Moshi.Builder()
                .add(new CardAdapter())
                .build();
        Card sixOfHearts = new Card('6', HEARTS);
        JsonAdapter<Card> jsonAdapter = moshi.adapter(Card.class);

        // when
        final String json = jsonAdapter.toJson(sixOfHearts);

        // then
        assertThat(json).isEqualTo("\"6H\"");
    }

    @Test
    public void converts_generic_list_to_json_array() throws Exception {
        // given
        List<Card> list = new ArrayList<>();
        list.add(new Card('6', Suit.HEARTS));
        list.add(new Card('A', Suit.SPADES));

        Type type = Types.newParameterizedType(List.class, Card.class);
        JsonAdapter<List<Card>> jsonAdapter = this.moshi.adapter(type);

        // when
        final String json = jsonAdapter.toJson(list);

        // then
        assertThat(json).isEqualTo(
                "[{\"rank\":\"6\",\"suit\":\"HEARTS\"},{\"rank\":\"A\",\"suit\":\"SPADES\"}]");
    }

    @Test
    public void converts_json_array_to_generic_list() throws Exception {
        // given
        String json = "[{\"rank\":\"6\",\"suit\":\"HEARTS\"},{\"rank\":\"A\",\"suit\":\"SPADES\"}]";
        Type type = Types.newParameterizedType(List.class, Card.class);
        JsonAdapter<List<Card>> jsonAdapter = this.moshi.adapter(type);

        // when
        final List<Card> list = jsonAdapter.fromJson(json);

        // then
        assertThat(list).hasSize(2);
        assertThat(list).contains(new Card('6', Suit.HEARTS));
        assertThat(list).contains(new Card('A', Suit.SPADES));
    }
}
