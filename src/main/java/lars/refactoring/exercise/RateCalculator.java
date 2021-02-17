package lars.refactoring.exercise;

import java.util.Iterator;

public class RateCalculator {

  boolean isWinter() {
    return false;
  }

  int calculateSlidingScale(int kwh) {
    return kwh;
  }

  int calculateTerritory(int address) {
    return address;
  }

  double calculateRate(Customer c) {
    double rate = 0;
    switch (c.customerType) {
      case Customer.CONSUMER:
        switch (c.rate) {
          case Customer.LIFELINE:
            if (c.kwh <= 100) {
              rate = c.kwh * .03;
              break;
            } else if (c.kwh <= 200) {
              rate = 3 + (c.kwh - 100) * .05;
              break;
            }
            // Customer does not qualify for lifline,
            // fall through to territorial case.
          case Customer.TERITORIAL:
            switch (calculateTerritory(c.address)) {
              case 1:
                // spec says territory 1 and 2 use same rate.
              case 2:
                rate = c.kwh * (isWinter() ? .07 : .06);
                break;
              case 3:
                rate = c.kwh * .065;
                break;
            }
            break;
        }
        break; // consumer
      case Customer.BUSINESS:
      case Customer.INDUSTRIAL: {
        for (Iterator iter = c.sites.iterator(); iter.hasNext(); ) {
          Site s = (Site) iter.next();
          rate += calculateSlidingScale(s.kwh);
        }
        if (c.customerType == Customer.INDUSTRIAL) {
          switch (c.industrialRate) {
            case Customer.INTERRUPTABLE:
              rate *= .8;
              break;
            case Customer.ONE_HOUR_NOTICE:
              rate *= .9;
              break;
            default:
              rate *= .95;
              break;
          }
        }
      }
      // industrial rate
      break; // business + industrial
    } // customer.type
    return rate;
  }
}
