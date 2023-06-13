package lars.refactoring.jbrains;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

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

    assertAddressUpdatedInDeliveryBigObject(redirectedDelivery,
        new Address("456 Main Street", "Apt 2A", "Example City", "Example Province", "A1B 2C3"));
  }

  private static void assertAddressUpdatedInDeliveryBigObject(Delivery redirected, Address second) {
    assertEquals(redirected.addressOf(), second);
  }

}
