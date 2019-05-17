package lars.refactoring.tpp;

import lars.refactoring.tpp.Statement.Account;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

import static org.assertj.core.api.Assertions.assertThat;

class StatementTest {

    private ByteArrayOutputStream out;
    private Statement statement;

    @BeforeEach
    void setUp() {
        out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out, true, StandardCharsets.UTF_8));
        statement = new Statement();
    }

    @Test
    void print_with_all_positive() {
        Account account = new Account(1f, 10f, 100f, 1000f);

        statement.print(account);

        String actual = out.toString();
        assertThat(actual).isEqualTo(
                  "Debits:        1.00\n"
                + "Credits:      10.00\n"
                + "Fees:        100.00\n"
                + "               ----\n"
                + "Balance:    1000.00\n");
    }

    @Test
    void print_when_fee_negative() {
        Account account = new Account(1f, 1f, -1f, 1f);

        statement.print(account);

        assertThat(out.toString()).isEqualTo(
                  "Debits:        1.00\n"
                + "Credits:       1.00\n"
                + "Fees:          1.00-\n"
                + "               ----\n"
                + "Balance:       1.00\n");
    }

    @Test
    void print_when_balance_negative() {
        Account account = new Account(1f, 1f, 1f, -1f);

        statement.print(account);

        assertThat(out.toString()).isEqualTo(
                  "Debits:        1.00\n"
                + "Credits:       1.00\n"
                + "Fees:          1.00\n"
                + "               ----\n"
                + "Balance:       1.00-\n");
    }
}
