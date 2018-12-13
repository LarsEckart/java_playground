package lars.refactoring.v2;

import java.text.NumberFormat;
import java.util.Locale;
import java.util.Map;

class Bill {

    private Map<String, Play> plays;

    String statement(Invoice invoice, Map<String, Play> plays) {
        this.plays = plays;

        var result = String.format("Statement for %s\n", invoice.getCustomer());
        for (var perf : invoice.getPerformances()) {
            result += String.format("  %s: %s (%d seats)\n", playFor(perf).getName(), usd(amountFor(perf)), perf.getAudience());
        }

        result += String.format("Amount owed is %s\n", usd(calculateAmount(invoice)));
        result += String.format("You earned %d credits", calculateVolumeCredits(invoice));

        return result;
    }

    private int calculateAmount(Invoice invoice) {
        var result = 0;
        for (var perf : invoice.getPerformances()) {
            result += amountFor(perf);
        }
        return result;
    }

    private int calculateVolumeCredits(Invoice invoice) {
        var result = 0;
        for (var perf : invoice.getPerformances()) {
            result += addVolumneCredits(perf);
        }
        return result;
    }

    private String usd(int amount) {
        return NumberFormat.getCurrencyInstance(Locale.US).format(amount / 100);
    }

    private int addVolumneCredits(Performance perf) {
        int result = 0;
        result += Math.max(perf.getAudience() - 30, 0);
        if ("comedy".equals(playFor(perf).getType())) {
            result += Math.floor(perf.getAudience() / 5d);
        }
        return result;
    }

    private Play playFor(Performance perf) {
        return this.plays.get(perf.getPlayID());
    }

    private int amountFor(Performance perf) {
        var result = 0;

        switch (playFor(perf).getType()) {
            case "tragedy":
                result = 40000;
                if (perf.getAudience() > 30) {
                    result += 1000 * (perf.getAudience() - 30);
                }
                break;
            case "comedy":
                result = 30000;
                if (perf.getAudience() > 20) {
                    result += 10000 + 500 * (perf.getAudience() - 20);
                }
                result += 300 * perf.getAudience();
                break;
            default:
                throw new Error(String.format("unknown type: %s", playFor(perf)));
        }
        return result;
    }
}
