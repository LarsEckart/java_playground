package ee.lars.tictactoe;

class Grid {

  private char[][] fields;
  private final char emptyFieldIndicator;

  public Grid(int size, char emptyFieldIndicator) {
    this.fields = new char[size][size];
    this.emptyFieldIndicator = emptyFieldIndicator;
    for (int i = 1; i <= size; i++) {
      for (int j = 1; j <= size; j++) {
        this.markField(i, j, emptyFieldIndicator);
      }
    }
  }

  public boolean validPlacement(int placement) {
    return placement > fields.length || placement < 1;
  }

  public boolean fieldEmpty(int row, int column) {
    return this.fields[row - 1][column - 1] == emptyFieldIndicator;
  }

  public char getFieldMarking(int row, int column) {
    return this.fields[row - 1][column - 1];
  }

  public void markField(int row, int column, char sign) {
    this.fields[row - 1][column - 1] = sign;
  }

  public boolean anyRowCompleteFor(char playerSign) {
    for (int row = 1; row <= size(); row++) {
      if (this.getFieldMarking(row, 1) == playerSign
          && this.getFieldMarking(row, 2) == playerSign
          && this.getFieldMarking(row, 3) == playerSign) {
        return true;
      }
    }
    return false;
  }

  public int size() {
    return fields.length;
  }

  public boolean anyColumnCompleteFor(char playerSign) {
    for (int column = 1; column <= size(); column++) {
      if (this.getFieldMarking(1, column) == playerSign
          && this.getFieldMarking(2, column) == playerSign
          && this.getFieldMarking(3, column) == playerSign) {
        return true;
      }
    }
    return false;
  }

  public boolean anyDiagonalCompleteFor(char playerSign) {
    return (this.getFieldMarking(1, 1) == playerSign
        && this.getFieldMarking(2, 2) == playerSign
        && this.getFieldMarking(3, 3) == playerSign)
        || (this.getFieldMarking(3, 1) == playerSign
        && this.getFieldMarking(2, 2) == playerSign
        && this.getFieldMarking(1, 3) == playerSign);
  }
}
