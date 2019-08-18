package lars.json.moshi;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.Types;
import lars.json.BagOfPrimitives;
import lars.json.BagWithNull;
import lars.json.BagWithTransientField;
import lars.json.Card;
import lars.json.ImmutableBag;
import lars.json.Suit;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.EOFException;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import static lars.json.Suit.HEARTS;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class MoshiTest {

    private Moshi moshi;

    @BeforeEach
    void initialize() {
        this.moshi = new Moshi.Builder().build();
    }

    @Test
    void converts_simple_string() {
        // given
        JsonAdapter<String> jsonAdapter = moshi.adapter(String.class);

        // when
        final String json = jsonAdapter.toJson("hello world");

        // then
        assertThat(json).isEqualTo("\"hello world\"");
    }

    @Test
    void converts_bag_of_primitives() {
        // given
        BagOfPrimitives obj = new BagOfPrimitives();
        JsonAdapter<BagOfPrimitives> jsonAdapter = moshi.adapter(BagOfPrimitives.class);

        // when
        String json = jsonAdapter.toJson(obj);

        // then
        assertThat(json).isEqualTo("{\"value1\":1,\"value2\":\"abc\",\"value3\":0}");
    }

    @Test
    void converts_json_to_bag_with_final_field() throws IOException {
        // given
        String json = "{\"name\":\"Oskar\"}";
        JsonAdapter<ImmutableBag> jsonAdapter = moshi.adapter(ImmutableBag.class);

        // when
        final ImmutableBag obj = jsonAdapter.fromJson(json);

        // then
        assertThat(obj.getName()).isEqualTo("Oskar");
    }

    @Test
    void ignores_transient_fields() {
        // given
        BagWithTransientField obj = new BagWithTransientField();
        JsonAdapter<BagWithTransientField> jsonAdapter = moshi.adapter(BagWithTransientField.class);

        // when
        String json = jsonAdapter.toJson(obj);

        // then
        assertThat(json).isEqualTo("{\"value1\":\"hello\"}");
    }

    @Test
    void ignores_null_fields() {
        // given
        BagWithNull obj = new BagWithNull();
        JsonAdapter<BagWithNull> jsonAdapter = moshi.adapter(BagWithNull.class);

        // when
        String json = jsonAdapter.toJson(obj);

        // then
        assertThat(json).isEqualTo("{\"value1\":\"hello\"}");
    }

    @Test
    void throws_np_exception_when_parsing_null() {
        // given
        String json = null;
        JsonAdapter<BagOfPrimitives> jsonAdapter = moshi.adapter(BagOfPrimitives.class);

        assertThrows(NullPointerException.class, () -> jsonAdapter.fromJson(json));
    }

    @Test
    void throws_eof_exception_when_empty_string() {
        // given
        String json = "";
        JsonAdapter<BagOfPrimitives> jsonAdapter = moshi.adapter(BagOfPrimitives.class);

        assertThrows(EOFException.class, () -> jsonAdapter.fromJson(json));
    }

    @Test
    void parses_empty_json_string_to_default_primitive_or_null_for_non_primitives() throws IOException {
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
    void custom_type_adapter() {
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
    void converts_generic_list_to_json_array() {
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
    void converts_json_array_to_generic_list() throws IOException {
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
