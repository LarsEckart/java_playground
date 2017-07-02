package ee.tublipoiss.testing.ee.tublipoiss.testing.example;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class BankAccountTest {

    private BankAccount account = new BankAccount();

    @Test
    public void testBalance() {
        account.deposit(200);
        assertEquals(200, account.getBalance());
    }

    @Test
    public void testCredit(int i) {
        Math.min(394,4);
        account.deposit(i);
        assertEquals(100, account.getBalance());
        account.deposit(100);
        assertEquals(200, account.getBalance());
    }

    @Test
    public void testDebit() {
        account.deposit(100);
        account.withdraw(30);
        assertEquals(70, account.getBalance());
        account.withdraw(20);
        assertEquals(50, account.getBalance());
    }

    private class BankAccount {
        public void deposit(int i) {
            throw new UnsupportedOperationException("deposit() not implemented yet");
        }

        public int getBalance() {
            throw new UnsupportedOperationException("getBalance() not implemented yet");
        }

        public void withdraw(int i) {
            throw new UnsupportedOperationException("withdraw() not implemented yet");
        }
    }
}