package lars.spielplatz.tc.connectionpool;

import com.google.mu.safesql.SafeSql;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Repository for managing bank account operations using SafeSql for type-safe queries.
 *
 * <p>This class demonstrates using Google Mug's SafeSql library to build and execute SQL queries in
 * a type-safe manner, preventing SQL injection while maintaining readable code.
 */
public class AccountRepository {

  /**
   * Retrieves the current balance for an account using SELECT FOR UPDATE to acquire a row lock.
   *
   * @param connection the database connection to use
   * @param accountNumber the account number to query
   * @return the current balance
   * @throws SQLException if the account doesn't exist or a database error occurs
   */
  public long getBalance(Connection connection, String accountNumber) throws SQLException {
    return SafeSql.of(
            "SELECT balance FROM accounts WHERE account_number = {accountNumber} FOR UPDATE",
            accountNumber)
        .queryForOne(connection, row -> row.getLong("balance"))
        .orElseThrow(() -> new SQLException("Account not found: " + accountNumber));
  }

  /**
   * Adds an amount to the account balance.
   *
   * @param connection the database connection to use
   * @param accountNumber the account number to update
   * @param amount the amount to add (can be negative for withdrawals)
   * @throws SQLException if a database error occurs
   */
  public void addToBalance(Connection connection, String accountNumber, long amount)
      throws SQLException {
    int updated =
        SafeSql.of(
                "UPDATE accounts SET balance = balance + {amount} WHERE account_number = {accountNumber}",
                amount,
                accountNumber)
            .update(connection);

    if (updated == 0) {
      throw new SQLException("Account not found: " + accountNumber);
    }
  }
}
