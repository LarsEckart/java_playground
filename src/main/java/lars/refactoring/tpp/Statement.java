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
