package lars.katas.gameoflife;

import java.util.ArrayList;
import java.util.List;

class World {

    private List<Cell> cells = new ArrayList<>();

    private World() {
    }

    public static World createNew() {
        return new World();
    }

    public int rows() {
        return 8;
    }

    public int columns() {
        return 8;
    }

    public boolean isAliveAt(Cell cell) {
        return cells.stream().filter(c -> c.equals(cell)).anyMatch(c -> c.isAlive());
    }

    public void lifeAt(Cell cell) {
        cell.setAlive();
        cells.add(cell);
    }

    public void evolve() {
        cells.forEach(Cell::evolve);
    }
}
