package ee.lars.json.gson;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import ee.lars.json.Card;
import ee.lars.json.Suit;
import java.io.IOException;

public class CardAdapter extends TypeAdapter<Card> {

    @Override
    public void write(JsonWriter writer, Card card) throws IOException {
        if (card == null) {
            writer.nullValue();
            return;
        }
        String json = card.rank + card.suit.name().substring(0, 1);
        writer.value(json);
    }

    @Override
    public Card read(JsonReader reader) throws IOException {
        if (reader.peek() == JsonToken.NULL) {
            reader.nextNull();
            return null;
        }
        String card = reader.nextString();

        if (card.length() != 2) {
            throw new IllegalArgumentException("Unknown card: " + card);
        }

        char rank = card.charAt(0);
        switch (card.charAt(1)) {
            case 'C':
                return new Card(rank, Suit.CLUBS);
            case 'D':
                return new Card(rank, Suit.DIAMONDS);
            case 'H':
                return new Card(rank, Suit.HEARTS);
            case 'S':
                return new Card(rank, Suit.SPADES);
            default:
                throw new IllegalArgumentException("unknown suit: " + card);
        }
    }
}