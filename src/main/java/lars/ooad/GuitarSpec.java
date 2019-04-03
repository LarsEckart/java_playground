package lars.ooad;

class GuitarSpec {

    private String model;
    private Builder builder;
    private Type type;
    private int numStrings;
    private Wood backWood;
    private Wood topWood;

    public GuitarSpec(Builder builder, String model, Type type, int numStrings, Wood backWood, Wood topWood) {
        this.model = model;
        this.builder = builder;
        this.type = type;
        this.numStrings = numStrings;
        this.backWood = backWood;
        this.topWood = topWood;
    }

    @Override
    public String toString() {
        return getBuilder() + " " + getModel() + " " +
                getType() + " guitar:\n     " +
                getBackWood() + " back and sides,\n     " +
                getTopWood() + " top.";
    }

    public String getModel() {
        return model;
    }

    public Builder getBuilder() {
        return builder;
    }

    public Type getType() {
        return type;
    }

    public Wood getBackWood() {
        return backWood;
    }

    public Wood getTopWood() {
        return topWood;
    }

    public int getNumStrings() {
        return numStrings;
    }

    public boolean matches(GuitarSpec otherSpec) {
        if (builder != otherSpec.builder) {
            return false;
        }
        if ((model != null) && (!model.equals("")) &&
                (!model.toLowerCase().equals(otherSpec.model.toLowerCase()))) {
            return false;
        }
        if (type != otherSpec.type) {
            return false;
        }
        if (numStrings != otherSpec.numStrings) {
            return false;
        }
        if (backWood != otherSpec.backWood) {
            return false;
        }
        if (topWood != otherSpec.topWood) {
            return false;
        }
        return true;
    }
}
