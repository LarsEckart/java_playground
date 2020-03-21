package lars.quinngil;

public class FizzBuzz_05_NoNewInline {

  class FizzBuzz {

    private int input;

    public FizzBuzz(int input) {
      this.input = input;
    }

    public String Result() {
      if (new Mod15(input).remainder() == 0) {
        return "FizzBuzz";
      }
      if (new Mod3(input).remainder() == 0) {
        return "Fizz";
      }
      if (new Mod5(input).remainder() == 0) {
        return "Buzz";
      }
      return String.valueOf(input);
    }
  }

  class Mod15 {

    private final int input;

    public Mod15(int input) {
      this.input = input;
    }

    public int remainder() {
      return input % 15;
    }
  }

  class Mod3 {

    private final int input;

    public Mod3(int input) {
      this.input = input;
    }

    public int remainder() {
      return input % 3;
    }
  }

  class Mod5 {

    private final int input;

    public Mod5(int input) {
      this.input = input;
    }

    public int remainder() {
      return input % 5;
    }
  }
}
