package ee.lars.tictactoe;

class Grid {

  private char[][] fields;
  private final char emptyFieldIndicator;

  public Grid(int size, char emptyFieldIndicator) {
    this.fields = new char[size][size];
    this.emptyFieldIndicator = emptyFieldIndicator;
    for (int i = 1; i < size + 1; i++) {
      for (int j = 1; j < size + 1; j++) {
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

  public int size() {
    return fields.length;
  }
}
