package ee.lars.money;

class Dollar extends Money {

  Dollar(int amount) {
    super(amount);
  }

  Money times(int multiplier) {
    return new Dollar(amount * multiplier);
  }
}
