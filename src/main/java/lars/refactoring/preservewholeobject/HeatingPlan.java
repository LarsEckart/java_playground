package lars.refactoring.preservewholeobject;

class HeatingPlan {

    public HeatingPlan(TemperatureRange range) {
        this.range = range;
    }

    private TemperatureRange range;

    public boolean isWithinRange(int low, int high) {
        return (low >= range.getLow() && high <= range.getHigh());
    }

    @Override
    public String toString() {
        return "HeatingPlan: " +  range ;
    }
}
