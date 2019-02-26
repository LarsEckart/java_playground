package lars.katas.pos;

import java.util.Optional;

public class PointOfSale {

    private final Display display;
    private final PriceRepository repo;

    public PointOfSale(Display display, PriceRepository repo) {
        this.display = display;
        this.repo = repo;
    }

    public void onBarcode(String barcode) {
        Optional<Long> price = repo.getFor(barcode);
        if (price.isPresent()) {
            display.show((price.get()/100.00) + "€");
        } else {
            display.show("No price for " + barcode);
        }
    }
}
