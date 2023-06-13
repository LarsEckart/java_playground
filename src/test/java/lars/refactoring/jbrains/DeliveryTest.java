package lars.refactoring.jbrains;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;

class DeliveryTest {

  @Test
  void redirecting_a_delivery() {
    Delivery delivery = new Delivery("123 Main Street", "Apt 4B", "Example City",
        "Example Province", "A1B 2C3",
        "John Doe", "555-1234", "Leave package at the front porch.",
        2.5, 1, false);

    Delivery redirectedDelivery = delivery.redirect("456 Main Street", "Apt 2A", "Example City",
        "Example Province", "A1B 2C3");

    assertAddressUpdatedInDeliveryBigObject(redirectedDelivery);
  }

  private static void assertAddressUpdatedInDeliveryBigObject(Delivery redirected) {
    assertEquals("456 Main Street", redirected.getAddressLine1());
    assertEquals("Apt 2A", redirected.getAddressLine2());
    assertEquals("Example City", redirected.getCity());
    assertEquals("Example Province", redirected.getProvince());
    assertEquals("A1B 2C3", redirected.getPostalCode());
  }
}
