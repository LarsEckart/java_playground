package lars.ooad;

class Guitar {

    private String serialNumber;
    private double price;
    private GuitarSpec guitarSpec;

    public Guitar(
            String serialNumber, double price,
            Builder builder, String model, Type type,
            Wood backWood, Wood topWood) {
        this.serialNumber = serialNumber;
        this.price = price;
        this.guitarSpec = new GuitarSpec(model, builder, type, backWood, topWood);
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public double getPrice() {
        return price;
    }

    public GuitarSpec getGuitarSpec() {
        return guitarSpec;
    }
}
