package ee.lars.money;

class Bank {

  public Money reduce(Expression source, String to) {
    if (source instanceof Money) {
      return source.reduce(to);
    }
    Sum sum = (Sum) source;
    return sum.reduce(to);
  }
}
