package ee.lars.money;

class Dollar {

  int amount;

  public Dollar(int amount) {
    this.amount = amount;
  }

  Dollar times(int multiplier) {
    return new Dollar(amount * multiplier);
  }

  public boolean equals(Object o) {
    Dollar other = (Dollar) o;
    return amount == other.amount;
  }
}
