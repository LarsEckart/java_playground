package lars.ooad;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class Inventory {

    private List<Guitar> guitars;

    public Inventory() {
        this.guitars = new LinkedList<>();
    }

    public void addGuitar(
            String serialNumber, double price, Builder builder, String model,
            Type type, Wood backWood, Wood topWood) {
        Guitar guitar = new Guitar(serialNumber, price, builder, model, type, backWood, topWood);
        guitars.add(guitar);
    }

    public Guitar getGuitar(String serialNumber) {
        for (Iterator i = guitars.iterator(); i.hasNext(); ) {
            Guitar guitar = (Guitar) i.next();
            if (guitar.getSerialNumber().equals(serialNumber)) {
                return guitar;
            }
        }
        return null;
    }

    public List<Guitar> search(GuitarSpec searchSpec) {
        List<Guitar> matchingGuitars = new LinkedList<>();
        for (Guitar guitar : guitars) {
            GuitarSpec guitarSpec = guitar.getGuitarSpec();
            // Ignore serial number since that's unique
            // Ignore price since that's unique
            if (searchSpec.getBuilder() != guitarSpec.getBuilder()) {
                continue;
            }
            String model = searchSpec.getModel().toLowerCase();
            if ((model != null) && (!model.equals("")) &&
                    (!model.equals(guitarSpec.getModel().toLowerCase()))) {
                continue;
            }
            if (searchSpec.getType() != guitarSpec.getType()) {
                continue;
            }
            if (searchSpec.getBackWood() != guitarSpec.getBackWood()) {
                continue;
            }
            if (searchSpec.getTopWood() != guitarSpec.getTopWood()) {
                continue;
            }
            matchingGuitars.add(guitar);
        }

        return matchingGuitars;
    }
}

