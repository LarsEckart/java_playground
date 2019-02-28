package lars.katas.pos;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class SellOneItemTest {

    private Display display;
    private Sale sale;

    @BeforeEach
    void setUp() {
        display = new Display();
        sale = new Sale(display, Map.of(
                "12345", "7.95€",
                "23456", "12.50€"));
    }

    @Test
    void product_found() {
        sale.onBarcode("12345");

        assertThat(display.getText()).isEqualTo("7.95€");
    }

    @Test
    void another_product_found() {
        sale.onBarcode("23456");

        assertThat(display.getText()).isEqualTo("12.50€");
    }

    @Test
    void product_not_found() {
        sale.onBarcode("99999");

        assertThat(display.getText()).isEqualTo("Product not found for 99999");
    }

    @Test
    void empty_barcode() {
        Sale sale = new Sale(display, null);

        sale.onBarcode("");

        assertThat(display.getText()).isEqualTo("Scanning error: empty barcode");
    }

    private static class Display {

        private String text;

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }
    }

    private static class Sale {

        private Display display;
        private Map<String, String> pricesByBarcode;

        private Sale(Display display, Map<String, String> pricesByBarcode) {
            this.display = display;
            this.pricesByBarcode = pricesByBarcode;
        }

        public void onBarcode(String barcode) {
            // up the call stack?
            if ("".equals(barcode)) {
                display.setText("Scanning error: empty barcode");
                return;
            }

            if (pricesByBarcode.containsKey(barcode)) {
                display.setText(pricesByBarcode.get(barcode));
            } else {
                display.setText("Product not found for " + barcode);
            }
        }
    }
}
