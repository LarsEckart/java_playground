package lars.json;

public class BagOfPrimitives {

  private int value1 = 1;
  private String value2 = "abc";
  private int value3;
  private String value4;

  public BagOfPrimitives() {}

  public int getValue1() {
    return this.value1;
  }

  public void setValue1(int value1) {
    this.value1 = value1;
  }

  public String getValue2() {
    return this.value2;
  }

  public void setValue2(String value2) {
    this.value2 = value2;
  }

  public int getValue3() {
    return this.value3;
  }

  public void setValue3(int value3) {
    this.value3 = value3;
  }

  public String getValue4() {
    return value4;
  }

  public void setValue4(String value4) {
    this.value4 = value4;
  }
}
