package lars.spielplatz.java8;

import java.util.function.Supplier;

import static java.util.Objects.requireNonNull;

public final class Lazy<T> {

  private volatile T value;

  public T getOrCompute(Supplier<T> supplier) {
    final T result = value; // Read volatile just once...
    return result == null ? maybeCompute(supplier) : result;
  }

  private synchronized T maybeCompute(Supplier<T> supplier) {
    if (value == null) {
      value = requireNonNull(supplier.get());
    }
    return value;
  }

  static class Point {

    private final int x, y;
    private final Lazy<String> lazyToString;

    public Point(int x, int y) {
      this.x = x;
      this.y = y;
      lazyToString = new Lazy<>();
    }

    @Override
    public String toString() {
      return lazyToString.getOrCompute(() -> "(" + x + ", " + y + ")");
    }
  }
}
