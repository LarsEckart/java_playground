package lars.json;

public class Card {

    public final char rank;
    public final Suit suit;

    public Card(char rank, Suit suit) {
        this.rank = rank;
        this.suit = suit;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Card card = (Card) o;

        if (this.rank != card.rank) {
            return false;
        }
        return this.suit == card.suit;
    }

    @Override
    public int hashCode() {
        int result = (int) this.rank;
        result = 31 * result + (this.suit != null ? this.suit.hashCode() : 0);
        return result;
    }
}
