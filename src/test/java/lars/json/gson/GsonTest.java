package lars.json.gson;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import lars.json.BagOfPrimitives;
import lars.json.BagWithNull;
import lars.json.BagWithTransientField;
import lars.json.Card;
import lars.json.ImmutableBag;
import lars.json.Suit;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static lars.json.Suit.HEARTS;
import static org.assertj.core.api.Assertions.assertThat;

class GsonTest {

  private Gson gson;

  @BeforeEach
  void initialize() {
    this.gson = new Gson();
  }

  @Test
  void converts_primitive_int() {
    // given

    // when
    final String json = gson.toJson(1);

    // then
    assertThat(json).isEqualTo("1");
  }

  @Test
  void converts_simple_string() {
    // given

    // when
    final String json = gson.toJson("hello world");

    // then
    assertThat(json).isEqualTo("\"hello world\"");
  }

  @Test
  void converts_primitive_array() {
    // given
    int[] values = {1, 2, 3};

    // when
    final String json = gson.toJson(values);

    // then
    assertThat(json).isEqualTo("[1,2,3]");
  }

  @Test
  void converts_bag_of_primitives() {
    // given
    BagOfPrimitives obj = new BagOfPrimitives();

    // when
    String json = gson.toJson(obj);

    // then
    assertThat(json).isEqualTo("{\"value1\":1,\"value2\":\"abc\",\"value3\":0}");
  }

  @Test
  void converts_bag_with_final_field() {
    // given
    ImmutableBag obj = new ImmutableBag("Oskar");

    // when
    String json = gson.toJson(obj);

    // then
    assertThat(json).isEqualTo("{\"name\":\"Oskar\"}");
  }

  @Test
  void converts_json_to_bag_with_final_field() {
    // given
    String json = "{\"name\":\"Oskar\"}";

    // when
    final ImmutableBag obj = gson.fromJson(json, ImmutableBag.class);

    // then
    assertThat(obj.getName()).isEqualTo("Oskar");
  }

  @Test
  void ignores_transient_fields() {
    // given
    BagWithTransientField obj = new BagWithTransientField();

    // when
    String json = gson.toJson(obj);

    // then
    assertThat(json).isEqualTo("{\"value1\":\"hello\"}");
  }

  @Test
  void ignores_null_fields() {
    // given
    BagWithNull obj = new BagWithNull();

    // when
    String json = gson.toJson(obj);

    // then
    assertThat(json).isEqualTo("{\"value1\":\"hello\"}");
  }

  @Test
  void parses_null_to_null() {
    // given
    String json = null;

    // when
    BagOfPrimitives obj = gson.fromJson(json, BagOfPrimitives.class);

    // then
    assertThat(obj).isNull();
  }

  @Test
  void parses_empty_string_to_null() {
    // given
    String json = "";

    // when
    BagOfPrimitives obj = gson.fromJson(json, BagOfPrimitives.class);

    // then
    assertThat(obj).isNull();
  }

  @Test
  void parses_empty_json_string_to_default_primitive_or_null_for_non_primitives() {
    // given
    String json = "{}";

    // when
    BagOfPrimitives obj = gson.fromJson(json, BagOfPrimitives.class);

    // then
    assertThat(obj).isNotNull();
    assertThat(obj.getValue3()).isEqualTo(0);
    assertThat(obj.getValue4()).isNull();
  }

  @Test
  void custom_type_adapter() {
    // given
    gson = new GsonBuilder().registerTypeAdapter(Card.class, new CardAdapter()).create();
    Card sixOfHearts = new Card('6', HEARTS);

    // when
    final String json = gson.toJson(sixOfHearts);

    // then
    assertThat(json).isEqualTo("\"6H\"");
  }

  @Test
  void converts_generic_list_to_json_array() {
    // given
    List<Card> list = new ArrayList<>();
    list.add(new Card('6', Suit.HEARTS));
    list.add(new Card('A', Suit.SPADES));

    // when
    final String json = this.gson.toJson(list);

    // then
    assertThat(json)
        .isEqualTo("[{\"rank\":\"6\",\"suit\":\"HEARTS\"},{\"rank\":\"A\",\"suit\":\"SPADES\"}]");
  }

  @Test
  void converts_json_array_to_generic_list() {
    // given
    String json = "[{\"rank\":\"6\",\"suit\":\"HEARTS\"},{\"rank\":\"A\",\"suit\":\"SPADES\"}]";
    Type cardListType = new TypeToken<ArrayList<Card>>() {}.getType();

    // when
    final List<Card> list = this.gson.fromJson(json, cardListType);

    // then
    assertThat(list).hasSize(2);
    assertThat(list).contains(new Card('6', Suit.HEARTS));
    assertThat(list).contains(new Card('A', Suit.SPADES));
  }
}
