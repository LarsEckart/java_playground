package lars.quinngil;

public class FizzBuzz_01_NoGetterNoSetter {

  class FizzBuzzBag {

    private int input;
    private String result;

    public String getResult() {
      return result;
    }

    public void setResult(String value) {
      result = value;
    }

    public int getInput() {
      return input;
    }

    public void setInput(int value) {
      input = value;
    }
  }

  class FizzBuzz1 {

    public int Input;
    public String Result;
  }

  static class FizzBuzzUtils {

    public static void Calculate(FizzBuzz1 fb) {
      if (fb.Input % 3 == 0) {
        fb.Result = "Fizz";
      }
      if (fb.Input % 5 == 0) {
        if (fb.Result == null) {
          fb.Result = "Buzz";
        } else {
          fb.Result += "Buzz";
        }
      }
      if (fb.Result == null) {
        fb.Result = String.valueOf(fb.Input);
      }
    }
  }

  // rather like this

  class FizzBuzz {

    private final int input;

    public FizzBuzz(int input) {
      this.input = input;
    }

    public String result() {
      String result = null;
      if (input % 3 == 0) {
        result = "Fizz";
      }
      if (input % 5 == 0) {
        if (result == null) {
          result = "Buzz";
        } else {
          result += "Buzz";
        }
      }
      if (result == null) {
        result = String.valueOf(input);
      }
      return result;
    }
  }
}
