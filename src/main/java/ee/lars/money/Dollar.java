package ee.lars.money;

class Dollar extends Money {

  public Dollar(int amount) {
    super(amount);
  }

  Dollar times(int multiplier) {
    return new Dollar(amount * multiplier);
  }
}
