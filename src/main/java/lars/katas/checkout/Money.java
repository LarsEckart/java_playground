package lars.katas.checkout;

import java.util.Objects;

public final class Money {

  public static final Money ZERO = new Money(0);

  private final int pence;

  private Money(int pence) {
    this.pence = pence;
  }

  public static Money fromPence(int pence) {
    return new Money(pence);
  }

  public Money add(Money other) {
    return new Money(this.pence + other.pence);
  }

  public Money subtract(Money other) {
    return new Money(this.pence - other.pence);
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == this) return true;
    if (obj == null || obj.getClass() != this.getClass()) return false;
    var that = (Money) obj;
    return this.pence == that.pence;
  }

  @Override
  public int hashCode() {
    return Objects.hash(pence);
  }

  @Override
  public String toString() {
    return "Money{" + "pence=" + pence + '}';
  }
}
