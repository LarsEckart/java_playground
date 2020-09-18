package lars.katas.dicegame;

import java.util.Random;

class Die {

  private int value;
  private final Random r;

  public Die() {
    value = 1;
    r = new Random();
  }

  public int faceValue() {
    return value;
  }

  public void roll() {
    value = r.nextInt(6) + 1;
  }
}
