package lars.refactoring.v2;

import java.text.NumberFormat;
import java.util.Locale;
import java.util.Map;

class Bill {

    String statement(Invoice invoice, Map<String, Play> plays) {
        var totalAmount = 0;
        var volumeCredits = 0;
        var result = String.format("Statement for %s\n", invoice.getCustomer());
        NumberFormat formatter = NumberFormat.getCurrencyInstance(Locale.US);
        for (var perf : invoice.getPerformances()) {
            var play = plays.get(perf.getPlayID());
            var thisAmount = 0;
            switch (play.getType()) {
                case "tragedy":
                    thisAmount = 40000;
                    if (perf.getAudience() > 30) {
                        thisAmount += 1000 * (perf.getAudience() - 30);
                    }
                    break;
                case "comedy":
                    thisAmount = 30000;
                    if (perf.getAudience() > 20) {
                        thisAmount += 10000 + 500 * (perf.getAudience() - 20);
                    }
                    thisAmount += 300 * perf.getAudience();
                    break;
                default:
                    throw new Error(String.format("unknown type: %s", play.getType()));
            }
            // add volume credits
            volumeCredits += Math.max(perf.getAudience() - 30, 0);
            // add extra credit for every ten comedy attendees
            if ("comedy" == play.getType()) {
                volumeCredits += Math.floor(perf.getAudience() / 5);
            }
            // print line for this order
            result += String.format("  %s: %s (%d seats)\n", play.getName(), formatter.format(thisAmount / 100), perf.getAudience());
            totalAmount += thisAmount;
        }
        result += String.format("Amount owed is %s\n", formatter.format(totalAmount / 100));
        result += String.format("You earned %d credits", volumeCredits);
        return result;
    }
}
