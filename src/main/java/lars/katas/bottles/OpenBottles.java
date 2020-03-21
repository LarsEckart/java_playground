package lars.katas.bottles;

import java.util.stream.Collectors;
import java.util.stream.Stream;

public class OpenBottles extends Bottles {

  @Override
  public String song() {
    return verses(99, 0);
  }

  @Override
  public String verses(int start, int end) {
    return Stream.iterate(start, i -> i >= end, i -> i - 1)
        .map(this::verse)
        .collect(Collectors.joining("\n"));
  }

  @Override
  public String verse(int number) {
    BottleNumber bottleNumber = BottleNumber.createFor(number);

    return capitalize(bottleNumber + " of beer on the wall, ")
        + bottleNumber
        + " of beer.\n"
        + bottleNumber.action()
        + ", "
        + bottleNumber.successor()
        + " of beer on the wall.\n";
  }

  private String capitalize(String text) {
    return text.substring(0, 1).toUpperCase() + text.substring(1);
  }

  static class BottleNumber {

    private int number;

    BottleNumber(int number) {
      this.number = number;
    }

    static BottleNumber createFor(int number) {
      switch (number) {
        case 0:
          BottleNumber0 bottleNumber0 = new BottleNumber0(number);
          System.out.println(bottleNumber0.getClass().getName());
          return bottleNumber0;
        case 1:
          return new BottleNumber1(number);
        case 6:
          return new BottleNumber6(number);
        default:
          return new BottleNumber(number);
      }

      /*
      Class c;
      try {
          c = Class.forName("lars.katas.bottles.OpenBottles$BottleNumber" + number);
      } catch (ClassNotFoundException e) {
          try {
              c = Class.forName("lars.katas.bottles.OpenBottles$BottleNumber");
          } catch (ClassNotFoundException e1) {
              throw new RuntimeException("out of luck");
          }
      }
      Class[] type = { int.class };
      Constructor constructor = null;
      try {
          constructor = c.getDeclaredConstructor(type);
          return (BottleNumber) constructor.newInstance(number);
      } catch (NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
          throw new RuntimeException("out of luck");
      }
      */
    }

    protected BottleNumber successor() {
      return createFor(number - 1);
    }

    protected String container() {
      return "bottles";
    }

    protected String pronoun() {
      return "one";
    }

    protected String quantity() {
      return String.valueOf(number);
    }

    protected String action() {
      return "Take " + pronoun() + " down and pass it around";
    }

    @Override
    public String toString() {
      return quantity() + " " + container();
    }
  }

  static class BottleNumber0 extends BottleNumber {

    BottleNumber0(int number) {
      super(number);
    }

    @Override
    protected String quantity() {
      return "no more";
    }

    @Override
    protected BottleNumber successor() {
      return createFor(99);
    }

    @Override
    protected String action() {
      return "Go to the store and buy some more";
    }
  }

  static class BottleNumber1 extends BottleNumber {

    BottleNumber1(int number) {
      super(number);
    }

    @Override
    protected String pronoun() {
      return "it";
    }

    @Override
    protected String container() {
      return "bottle";
    }
  }

  static class BottleNumber6 extends BottleNumber {

    BottleNumber6(int number) {
      super(number);
    }

    @Override
    protected String container() {
      return "six-pack";
    }

    @Override
    protected String quantity() {
      return "1";
    }
  }
}
