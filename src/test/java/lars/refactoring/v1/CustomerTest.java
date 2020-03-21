package lars.refactoring.v1;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class CustomerTest {

  @Test
  void statement() {
    Customer customer = new Customer("Any");

    customer.addRental(new Rental(new Movie("Harry Potter", Movie.CHILDRENS), 3));
    customer.addRental(new Rental(new Movie("Harry Potter II", Movie.CHILDRENS), 4));
    customer.addRental(new Rental(new Movie("Die Hard", Movie.REGULAR), 2));
    customer.addRental(new Rental(new Movie("Die Hard II", Movie.REGULAR), 1));
    customer.addRental(new Rental(new Movie("Die Hard III", Movie.REGULAR), 3));
    customer.addRental(
        new Rental(new Movie("Fantastic BBeasts And Where To Find Them", Movie.NEW_RELEASE), 4));

    assertThat(customer.statement())
        .isEqualTo(
            "Rental Record for Any\n"
                + "\tHarry Potter\t1.5\n"
                + "\tHarry Potter II\t3.0\n"
                + "\tDie Hard\t2.0\n"
                + "\tDie Hard II\t2.0\n"
                + "\tDie Hard III\t3.5\n"
                + "\tFantastic BBeasts And Where To Find Them\t12.0\n"
                + "Amount owed is 24.0\n"
                + "Your earned 7 frequent renter points");
  }
}
