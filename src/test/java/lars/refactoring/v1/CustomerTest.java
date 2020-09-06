package lars.refactoring.v1;

import org.approvaltests.Approvals;
import org.junit.jupiter.api.Test;

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

    Approvals.verify(customer.statement());
  }
}
