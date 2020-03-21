package lars.refactoring.v2;

import java.text.NumberFormat;
import java.util.Locale;
import java.util.Map;

class Statement {

  String statement(Invoice invoice, Map<String, Play> plays) {
    int totalAmount = 0;
    int volumeCredits = 0;
    var result = String.format("Statement for %s\n", invoice.customer);
    NumberFormat formatter = NumberFormat.getCurrencyInstance(Locale.US);
    for (var perf : invoice.performances) {
      var play = plays.get(perf.playID);
      var thisAmount = 0;
      switch (play.type) {
        case "tragedy":
          thisAmount = 40000;
          if (perf.audience > 30) {
            thisAmount += 1000 * (perf.audience - 30);
          }
          break;
        case "comedy":
          thisAmount = 30000;
          if (perf.audience > 20) {
            thisAmount += 10000 + 500 * (perf.audience - 20);
          }
          thisAmount += 300 * perf.audience;
          break;
        default:
          throw new IllegalArgumentException(String.format("unknown type: %s", play.type));
      }
      // add volume credits
      volumeCredits += Math.max(perf.audience - 30, 0);
      // add extra credit for every ten comedy attendees
      if ("comedy" == play.type) {
        volumeCredits += Math.floor(perf.audience / 5);
      }
      // print line for this order
      result +=
          String.format(
              "  %s: %s (%d seats)\n",
              play.name, formatter.format(thisAmount / 100), perf.audience);
      totalAmount += thisAmount;
    }
    result += String.format("Amount owed is %s\n", formatter.format(totalAmount / 100));
    result += String.format("You earned %d credits", volumeCredits);
    return result;
  }
}
