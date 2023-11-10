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
    return (o instanceof Card c) && rank == c.rank && suit == c.suit;
  }

  @Override
  public int hashCode() {
    int result = (int) this.rank;
    result = 31 * result + (this.suit != null ? this.suit.hashCode() : 0);
    return result;
  }
}
