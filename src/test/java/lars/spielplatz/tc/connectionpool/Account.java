package lars.spielplatz.tc.connectionpool;

/**
 * Represents a bank account with an account number and balance.
 *
 * <p>This is an immutable value object used to represent account data retrieved from the database.
 */
public record Account(String accountNumber, long balance) {}
