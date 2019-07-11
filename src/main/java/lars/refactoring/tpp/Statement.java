package lars.refactoring.tpp;

public class Statement {

    public void print(Account account) {
        System.out.format("Debits:  %10.2f\n", account.debits);
        System.out.format("Credits: %10.2f\n", account.credits);
        if (account.fees < 0) {
            System.out.format("Fees:    %10.2f-\n", -account.fees);
        } else {
            System.out.format("Fees:    %10.2f\n", account.fees);
        }
        System.out.format("               ----\n");
        if (account.balance < 0) {
            System.out.format("Balance: %10.2f-\n", -account.balance);
        } else {
            System.out.format("Balance: %10.2f\n", account.balance);
        }
        fees(account);
    }

    /**
     * Calculate the fees for this account.
     *
     * Each returned check costs $20
     * If the account is in overdraft for more than 3 days,
     * charge $10 for each day
     * If the average account balance is greater that $2,000
     * reduce the fees by 50%
     */
    private int fees(Account a) {
        int f = 0;
        if (a.returnedCheckCount > 0) {
            f += 20 * a.returnedCheckCount;
        }
        if (a.overdraftDays > 3) {
            f += 10 * a.overdraftDays;
        }
        if (a.averageBalance > 2_000) {
            f /= f;
        }
        return f;
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

        int returnedCheckCount;
        int overdraftDays;
        int averageBalance;
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
