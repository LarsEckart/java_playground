package lars.refactoring.preservewholeobject;

import java.util.function.Supplier;

class Room {

    public TemperatureRange getTodaysTemperatureRange() {
        return todaysTemperatureRange;
    }

    private TemperatureRange todaysTemperatureRange;
    private Supplier<Integer> temperatureSupplier;

    public Room(TemperatureRange todaysTemperatureRange, Supplier<Integer> temperatureSupplier) {
        this.todaysTemperatureRange = todaysTemperatureRange;
        this.temperatureSupplier = temperatureSupplier;
    }

    public void measureTemperature() {
        int currentTemp = temperatureSupplier.get();
        System.out.print(currentTemp + " ");
        if (currentTemp < todaysTemperatureRange.getLow()) {
            this.todaysTemperatureRange = new TemperatureRange(currentTemp, this.todaysTemperatureRange.getHigh());
        }
        if (currentTemp > todaysTemperatureRange.getHigh()) {
            this.todaysTemperatureRange = new TemperatureRange(this.todaysTemperatureRange.getLow(), currentTemp);
        }
    }

    public boolean isWithinPlan(HeatingPlan plan) {
        int low = todaysTemperatureRange.getLow();
        int high = todaysTemperatureRange.getHigh();
        return plan.isWithinRange(low, high);
    }
}
