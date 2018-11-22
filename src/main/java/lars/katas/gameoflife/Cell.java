package lars.katas.gameoflife;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

class Cell {

    private final int column;
    private final int row;
    private boolean alive;
    private List<Cell> neighbours = new ArrayList<>(8);

    public Cell(int column, int row) {
        this.column = column;
        this.row = row;
    }

    public void evolve() {
        for (Cell c : neighbours) {

        }
    }

    public boolean isAlive() {
        return alive;
    }

    public void setAlive() {
        this.alive = true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Cell cell = (Cell) o;
        return column == cell.column &&
                row == cell.row;
    }

    @Override
    public int hashCode() {
        return Objects.hash(column, row);
    }

    @Override
    public String toString() {
        return "Cell{" +
                "column=" + column +
                ", row=" + row +
                '}';
    }
}
