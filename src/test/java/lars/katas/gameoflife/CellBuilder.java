package lars.katas.gameoflife;

class CellBuilder {

    private int column;
    private int row;

    public static CellBuilder aCell() {
        return new CellBuilder();
    }

    public CellBuilder atColumn(int column) {
        this.column = column;
        return this;
    }

    public Cell atRow(int row) {
        this.row = row;
        return new Cell(this.column, this.row);
    }
}
