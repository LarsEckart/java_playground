package lars.ooad;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class Inventory {

    private List<Instrument> inventory;

    public Inventory() {
        inventory = new LinkedList<>();
    }

    public void addInstrument(String serialNumber, double price, InstrumentSpec spec) {
        Instrument instrument = null;
        if (spec instanceof GuitarSpec) {
            instrument = new Guitar(serialNumber, price, (GuitarSpec)spec);
        } else if (spec instanceof MandolinSpec) {
            instrument = new Mandolin(serialNumber, price, (MandolinSpec)spec);
        }
        inventory.add(instrument);
    }

    public Instrument get(String serialNumber) {
        for (Instrument instrument : inventory) {
            if (instrument.getSerialNumber().equals(serialNumber)) {
                return instrument;
            }
        }
        return null;
    }

    public List<Guitar> search(GuitarSpec searchSpec) {
        List<Guitar> matchingGuitars = new LinkedList<>();
        for (Instrument instrument : inventory) {
            Guitar guitar = (Guitar) instrument;
            if (guitar.getSpec().matches(searchSpec)) {
                matchingGuitars.add(guitar);
            }
        }
        return matchingGuitars;
    }

    public List search(MandolinSpec searchSpec) {
        List matchingMandolins = new LinkedList();
        for (Iterator i = inventory.iterator(); i.hasNext(); ) {
            Mandolin mandolin = (Mandolin)i.next();
            if (mandolin.getSpec().matches(searchSpec))
                matchingMandolins.add(mandolin);
        }
        return matchingMandolins;
    }
}


