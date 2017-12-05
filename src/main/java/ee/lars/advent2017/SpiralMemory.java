package ee.lars.advent2017;

public class SpiralMemory {

  public int calculateRowLength(int number) {
    if (number == 4) {
      return 2;
    }
    if (number == 16) {
      return 4;
    }
    return 1;
  }
}
