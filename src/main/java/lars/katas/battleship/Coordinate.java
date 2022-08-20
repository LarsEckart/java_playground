package lars.katas.battleship;

import java.util.Objects;

final class Coordinate {

  private final String row;
  private final String column;

  Coordinate(int value) {
    this.row = String.valueOf(value);
    this.column = null;
  }

  Coordinate(String value) {
    String[] split = value.split("");
    if (split.length != 2) {
      throw new IllegalArgumentException("not a valid coordinate, e.g. 'A1'");
    }
    this.row = split[0];
    this.column = split[1];
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Coordinate that = (Coordinate) o;
    return Objects.equals(row, that.row) && Objects.equals(column, that.column);
  }

  @Override
  public int hashCode() {
    return Objects.hash(row, column);
  }

  @Override
  public String toString() {
    return "Coordinate{" + row + "," + column + '}';
  }
}
