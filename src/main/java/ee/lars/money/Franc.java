package ee.lars.money;

class Franc extends Money {

  public Franc(int amount) {
    super(amount);
  }

  Money times(int multiplier) {
    return new Franc(amount * multiplier);
  }
}
