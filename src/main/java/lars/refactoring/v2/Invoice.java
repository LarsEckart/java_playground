package lars.refactoring.v2;

import java.util.List;

class Invoice {

  public String customer;
  public List<Performance> performances;

  Invoice(String customer, List<Performance> performances) {
    this.customer = customer;
    this.performances = performances;
  }
}
