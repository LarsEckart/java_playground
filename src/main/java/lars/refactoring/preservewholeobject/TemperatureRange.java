package lars.refactoring.preservewholeobject;

class TemperatureRange {
    private final int low;
    private final int high;

    public TemperatureRange(int low, int high) {
        this.low = low;
        this.high = high;
    }

    public int getHigh() {
        return high;
    }

    public int getLow() {
        return low;
    }

    @Override
    public String toString() {
        return "[" + low + "," + high + ']';
    }
}
