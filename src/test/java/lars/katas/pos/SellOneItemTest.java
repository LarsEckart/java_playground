package lars.katas.pos;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SellOneItemTest {

  private Display display;
  private Sale sale;

  @BeforeEach
  void setUp() {
    display = new Display();
    sale =
        new Sale(
            display,
            new Catalogue(
                Map.of(
                    "12345", "7.95€",
                    "23456", "12.50€")));
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
    Sale sale = new Sale(display, new Catalogue(null));

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

    private void displayproductNotFound(String barcode) {
      setText("Product not found for " + barcode);
    }

    private void displayPrice(String priceAsText) {
      setText(priceAsText);
    }

    private void displayEmptyBarcodeMessage() {
      setText("Scanning error: empty barcode");
    }
  }

  private static class Sale {

    private final Display display;
    private final Catalogue catalogue;

    private Sale(Display display, Catalogue catalogue) {
      this.display = display;
      this.catalogue = catalogue;
    }

    public void onBarcode(String barcode) {
      // up the call stack?
      if ("".equals(barcode)) {
        display.displayEmptyBarcodeMessage();
        return;
      }

      String priceAsText = catalogue.findPrice(barcode);
      if (priceAsText == null) {
        display.displayproductNotFound(barcode);
      } else {
        display.displayPrice(priceAsText);
      }
    }
  }

  private static class Catalogue {

    private final Map<String, String> pricesByBarcode;

    public Catalogue(Map<String, String> pricesByBarcode) {
      this.pricesByBarcode = pricesByBarcode;
    }

    private String findPrice(String barcode) {
      return pricesByBarcode.get(barcode);
    }
  }
}
