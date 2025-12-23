package lars.spielplatz.tc.connectionpool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Repository for managing bank account operations.
 *
 * <p>This class provides methods to query and update account balances using the provided database
 * connection. All operations use the connection passed in, allowing the caller to manage
 * transactions.
 */
public class AccountRepository {

  /**
   * Retrieves the current balance for an account.
   *
   * @param connection the database connection to use
   * @param accountNumber the account number to query
   * @return the current balance
   * @throws SQLException if the account doesn't exist or a database error occurs
   */
  public long getBalance(Connection connection, String accountNumber) throws SQLException {
    String sql = "SELECT balance FROM accounts WHERE account_number = ? FOR UPDATE";
    try (PreparedStatement statement = connection.prepareStatement(sql)) {
      statement.setString(1, accountNumber);
      try (ResultSet resultSet = statement.executeQuery()) {
        if (resultSet.next()) {
          return resultSet.getLong("balance");
        }
        throw new SQLException("Account not found: " + accountNumber);
      }
    }
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
    String sql = "UPDATE accounts SET balance = balance + ? WHERE account_number = ?";
    try (PreparedStatement statement = connection.prepareStatement(sql)) {
      statement.setLong(1, amount);
      statement.setString(2, accountNumber);
      int updated = statement.executeUpdate();
      if (updated == 0) {
        throw new SQLException("Account not found: " + accountNumber);
      }
    }
  }
}
