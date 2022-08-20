package lars.katas.battleship;

import java.util.Objects;

public final class Result {

  private final String name;
  private final String outcome;

  public Result(String outcome) {
    this("any", outcome);
  }
  public Result(String name, String outcome) {
    this.name = name;
    this.outcome = outcome;
  }

  public String message() {
    if ("hit".equals(outcome())) {
      return name + " was hit.";
    }
    if ("sunk".equals(outcome())) {
      return name + " was sunk!";
    }
    return outcome();
  }

  public String outcome() {
    return outcome;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == this) {
      return true;
    }
    if (obj == null || obj.getClass() != this.getClass()) {
      return false;
    }
    var that = (Result) obj;
    return Objects.equals(this.outcome, that.outcome);
  }

  @Override
  public int hashCode() {
    return Objects.hash(outcome);
  }

  @Override
  public String toString() {
    return "Result[" +
        "message=" + outcome + ']';
  }


}
