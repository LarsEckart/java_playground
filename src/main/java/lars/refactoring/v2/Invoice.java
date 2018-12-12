package lars.refactoring.v2;

import java.util.List;

class Invoice {

    private String customer;
    private List<Performance> performances;

    Invoice(String customer, List<Performance> performances) {
        this.customer = customer;
        this.performances = performances;
    }

    String getCustomer() {
        return customer;
    }

    List<Performance> getPerformances() {
        return performances;
    }
}
