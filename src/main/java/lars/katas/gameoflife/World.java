package lars.katas.gameoflife;

class World {

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

    public boolean isDeadAt(Cell cell) {
        return true;
    }
}
