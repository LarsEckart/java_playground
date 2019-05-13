package lars.refactoring.tpp;

public class Statement {

    public void print(Account account) {
        reportLine("Debits", account.debits);
        reportLine("Credits", account.credits);
        reportLine("Fees", account.fees);
        System.out.format("              ----\n");
        reportLine("Balance", account.balance);
    }

    private void reportLine(String label, float amount) {
        printLine(label + ":", formatAmount(amount));
    }

    private void printLine(String label, String amount) {
        //8 wide, left adjusted
        System.out.format("%-8s%s\n", label, amount);
    }

    private String formatAmount(float value) {
        // 10 wide, 2 after comma
        String result = String.format("%10.2f", Math.abs(value));
        if (value < 0) {
            result += "-";
        }
        return result;
    }

    static class Account {

        float debits;
        float credits;
        float fees;
        float balance;

        public Account(float debits, float credits, float fees, float balance) {
            this.debits = debits;
            this.credits = credits;
            this.fees = fees;
            this.balance = balance;
        }
    }
}
