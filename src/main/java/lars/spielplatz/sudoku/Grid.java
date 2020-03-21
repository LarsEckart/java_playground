package lars.spielplatz.sudoku;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

/**
 * A Grid object holds the currently known values of the Sudoku puzzle. The grid consists of 9x9
 * cells that hold integer values from 0 to 9. A value of 0 indicates that the cell is empty.
 *
 * <p>Each of the 81 cells has a location which is used to identify a specific cell. There are 81
 * locations, labelled location 0 to location 80. Cell locations are ordered from left-to-right and
 * top-down. For example, location 0 refers to the top-leftmost cell. Location 8 refers to the
 * top-righttmost cell. Location 71 refers to the bottom-leftmost cell. Location 80 refers to the
 * bottom-rightmost cell.
 *
 * <p>The grid consists of 9 columns, labelled column 0 to column 8. The grid consists of 9 rows,
 * labelled row 0 to row 8.
 *
 * <p>The grid consists of 9 subgrids, labelled subgrid 0 to subgrid 8. Each subgrid contains a
 * subgrid of 3x3 cells. Subgrid 0 contains cells - 0, 1, 2, 9, 10, 11, 18, 19, 20. Subgrid 8
 * contains cells - 60, 61, 62, 69, 70, 71, 78, 79, 80
 */
public class Grid implements Cloneable {

  private Grid() {}

  // Array that contains values of all 81 cells in the grid.

  private int[] cells = new int[81];
  // A set of bit-vectors that represent the known values for each column.
  // Specifically, if column c contains the digits d1 and d2,
  //   colsSet[c] = 2^(d1-1)|2^(d2-1)
  // For example, if column 0 contains the values 1 and 4, colsSet[0] = 9.
  // The information in this variable is redundant with the information
  // in the cells variable. The purpose of this variable is to reduce
  // the cost of determining whether a particular digit can be set in
  // a particular cell.
  private int[] colsSet = new int[9];
  // This purpose and behavior of this variable is similar to colsSet.
  private int[] rowsSet = new int[9];
  // This purpose and behavior of this variable is similar to colsSet.
  private int[] subgridSet = new int[9];

  public static Grid create(int[][] numbers) {
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < numbers.length; i++)
      for (int j = 0; j < numbers.length; j++) {
        int n = numbers[i][j];
        sb.append(n == 0 ? "." : String.valueOf(n));
      }
    return create(new StringReader(sb.toString()));
  }

  /**
   * This method returns a grid of givens and empty cells ready to be solved. The cells containing
   * givens have values between 1 and 9. Empty cells have the value 0.
   *
   * <p>Characters are read one at a time from the input stream and placed into the grid in
   * left-to-right and top-down order. - The characters 0 or . indicates an empty cell. - The
   * characters 1 to 9 indicates a given. - The character # is used for comments; subsequent
   * characters are ignored until a newline is encountered. - All other characters are simply
   * ignored.
   *
   * @param rd Reader containing the givens
   * @return null if there are not enough characters in 'rd' to form a grid.
   */
  public static Grid create(Reader rd) {
    Grid grid = new Grid();
    try {
      // Read until all 81 cells are filled
      for (int loc = 0; loc < grid.cells.length; ) {
        // Read a character
        int ch = rd.read();

        // -1 is returned if the input stream has no more characters
        if (ch < 0) {
          // No more characters so return null
          return null;
        }
        if (ch == '#') {
          // Skip to end-of-line
          while (ch >= 0 && ch != '\n' && ch != '\r') {
            ch = rd.read();
          }
        } else if (ch >= '1' && ch <= '9') {
          // A given
          grid.set(loc, ch - '0');
          loc++;
        } else if (ch == '.' || ch == '0') {
          // Empty cell
          loc++;
        }
      }
      return grid;
    } catch (IOException ex) {
      return null;
    }
  }

  /*
   * Finds an empty cell.
   * @return the location of an empty cell or -1 if there are no empty cells.
   *         Values must be in the range [-1, 80].
   */
  public int findEmptyCell() {
    for (int i = 0; i < cells.length; i++) {
      if (cells[i] == 0) {
        return i;
      }
    }
    return -1;
  }

  /*
   * Sets a number in a cell. This method checks to see if
   *   1. the cell is empty
   *   2. the cell is allowed to contain the specified number. E.g. if
   *      the number is 5 but the row already has a 5, the cell will
   *      not be set and false is returned.
   * @param loc  the location of the target cell.
   *             Values must be in the range [0, 80].
   * @param num  the number to set in the cell.
   *             Values must be in the range [1, 9].
   * @return     true if the set was successful.
   */
  public boolean set(int loc, int num) {
    // Compute row and column
    int r = loc / 9;
    int c = loc % 9;
    int blockLoc = (r / 3) * 3 + c / 3;

    boolean canSet =
        cells[loc] == 0
            && (colsSet[c] & (1 << num)) == 0
            && (rowsSet[r] & (1 << num)) == 0
            && (subgridSet[blockLoc] & (1 << num)) == 0;
    if (!canSet) {
      return false;
    }

    cells[loc] = num;
    colsSet[c] |= (1 << num);
    rowsSet[r] |= (1 << num);
    subgridSet[blockLoc] |= (1 << num);
    return true;
  }

  /*
   * Removes the number in a cell.
   * @param loc  the location of the target cell.
   *             Values must be in the range [0, 80].
   */
  public void clear(int loc) {
    // Compute row and column
    int r = loc / 9;
    int c = loc % 9;
    int blockLoc = (r / 3) * 3 + c / 3;

    int num = cells[loc];
    cells[loc] = 0;
    colsSet[c] ^= (1 << num);
    rowsSet[r] ^= (1 << num);
    subgridSet[blockLoc] ^= (1 << num);
  }

  /**
   * Returns a copy of this grid. Any modifications to the returned grid will not affect this grid.
   *
   * @return a non-null deep copy of this grid.
   */
  @Override
  public Grid clone() {
    Grid grid = new Grid();
    grid.cells = cells.clone();
    grid.colsSet = colsSet.clone();
    grid.rowsSet = rowsSet.clone();
    grid.subgridSet = subgridSet.clone();
    return grid;
  }

  public int[] getCells() {
    return cells;
  }

  /**
   * Returns a string representing the current contents of the grid. Used for debugging purposes.
   *
   * @return a non-null string representing the current contents of the grid.
   */
  @Override
  public String toString() {
    StringBuilder buf = new StringBuilder();
    for (int r = 0; r < 9; r++) {
      if (r % 3 == 0) {
        buf.append("-------------------------\n");
      }
      for (int c = 0; c < 9; c++) {
        if (c % 3 == 0) {
          buf.append("| ");
        }
        int num = cells[r * 9 + c];
        if (num == 0) {
          buf.append(". ");
        } else {
          buf.append(num);
          buf.append(" ");
        }
      }
      buf.append("|\n");
    }
    buf.append("-------------------------");
    return buf.toString();
  }
}
