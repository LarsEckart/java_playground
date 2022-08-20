package lars.katas.battleship;

import java.util.Objects;

public final class Result {

  private final String name;
  private final String message;

  public Result(String message) {
    this("any", message);
  }
  public Result(String name, String message) {
    this.name = name;
    this.message = message;
  }

  public String message() {
    return message;
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
    return Objects.equals(this.message, that.message);
  }

  @Override
  public int hashCode() {
    return Objects.hash(message);
  }

  @Override
  public String toString() {
    return "Result[" +
        "message=" + message + ']';
  }


}
