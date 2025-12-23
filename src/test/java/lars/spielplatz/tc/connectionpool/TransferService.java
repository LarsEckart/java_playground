package lars.spielplatz.tc.connectionpool;

import java.sql.Connection;
import java.sql.SQLException;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Service for transferring money between accounts.
 *
 * <p>This service demonstrates the importance of connection pool sizing by performing transactions
 * with REPEATABLE_READ isolation level, which causes database-level serialization of concurrent
 * transfers to the same accounts.
 *
 * <p>The service includes retry logic to handle serialization failures that naturally occur when
 * concurrent transactions access the same rows.
 */
public class TransferService {

  private static final Logger LOGGER = LoggerFactory.getLogger(TransferService.class);
  private static final int MAX_RETRIES = 20;

  private final DataSource dataSource;
  private final AccountRepository accountRepository;

  public TransferService(DataSource dataSource, AccountRepository accountRepository) {
    this.dataSource = dataSource;
    this.accountRepository = accountRepository;
  }

  /**
   * Transfers money from one account to another with automatic retry on serialization failures.
   *
   * <p>The transfer is performed within a single transaction using REPEATABLE_READ isolation level.
   * This isolation level ensures that the balance check and updates are consistent, but it also
   * means that concurrent transfers to the same accounts will be serialized at the database level.
   * When serialization conflicts occur, the transaction is retried.
   *
   * @param sourceAccount the account number to transfer from
   * @param destinationAccount the account number to transfer to
   * @param amount the amount to transfer
   * @throws TransferException if the transfer fails after all retries
   */
  public void transfer(String sourceAccount, String destinationAccount, long amount) {
    int attempts = 0;
    while (attempts < MAX_RETRIES) {
      attempts++;
      try {
        executeTransfer(sourceAccount, destinationAccount, amount);
        return; // Success
      } catch (TransferException e) {
        if (isSerializationFailure(e) && attempts < MAX_RETRIES) {
          LOGGER.debug("Serialization failure, retrying (attempt {})", attempts);
          try {
            // Exponential backoff with jitter before retry
            Thread.sleep((long) (Math.pow(2, Math.min(attempts, 5)) * Math.random() * 5));
          } catch (InterruptedException ie) {
            Thread.currentThread().interrupt();
            throw e;
          }
        } else {
          throw e;
        }
      }
    }
  }

  private void executeTransfer(String sourceAccount, String destinationAccount, long amount) {
    try (Connection connection = dataSource.getConnection()) {
      connection.setAutoCommit(false);
      connection.setTransactionIsolation(Connection.TRANSACTION_REPEATABLE_READ);

      try {
        long sourceBalance = accountRepository.getBalance(connection, sourceAccount);
        if (sourceBalance >= amount) {
          accountRepository.addToBalance(connection, sourceAccount, -amount);
          accountRepository.addToBalance(connection, destinationAccount, amount);
        }
        connection.commit();
      } catch (SQLException e) {
        try {
          connection.rollback();
        } catch (SQLException rollbackEx) {
          LOGGER.warn("Rollback failed", rollbackEx);
        }
        throw new TransferException("Transfer failed", e);
      }
    } catch (SQLException e) {
      throw new TransferException("Could not acquire connection", e);
    }
  }

  private boolean isSerializationFailure(TransferException e) {
    // PostgreSQL serialization failure is SQLSTATE 40001
    if (e.getCause() instanceof SQLException sqlEx) {
      String sqlState = sqlEx.getSQLState();
      // 40001 = serialization_failure
      // 40P01 = deadlock_detected
      // 55P03 = lock_not_available
      return sqlState != null && (sqlState.startsWith("40") || "55P03".equals(sqlState));
    }
    return false;
  }

  /** Exception thrown when a transfer operation fails. */
  public static class TransferException extends RuntimeException {

    public TransferException(String message, Throwable cause) {
      super(message, cause);
    }
  }
}
