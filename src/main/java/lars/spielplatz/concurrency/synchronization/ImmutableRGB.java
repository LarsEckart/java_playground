package lars.spielplatz.concurrency.synchronization;

public final class ImmutableRGB {

  // Values must be between 0 and 255.
  private final int red;
  private final int green;
  private final int blue;
  private final String name;

  private void check(int red, int green, int blue) {
    if (red < 0 || red > 255 || green < 0 || green > 255 || blue < 0 || blue > 255) {
      throw new IllegalArgumentException();
    }
  }

  public ImmutableRGB(int red, int green, int blue, String name) {
    check(red, green, blue);
    this.red = red;
    this.green = green;
    this.blue = blue;
    this.name = name;
  }

  public int getRGB() {
    return ((red << 16) | (green << 8) | blue);
  }

  public String getName() {
    return name;
  }

  public ImmutableRGB invert() {
    return new ImmutableRGB(255 - red, 255 - green, 255 - blue, "Inverse of " + name);
  }
}
