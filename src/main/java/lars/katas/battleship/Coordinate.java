package lars.katas.battleship;

import java.util.Objects;

class Coordinate {

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
    return (o instanceof Coordinate c) && Objects.equals(row, c.row)
        && Objects.equals(column, c.column);
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
