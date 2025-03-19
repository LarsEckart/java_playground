package lars.scripts.grokking.affiliatev2.affiliate;

final class SendPayout implements PayoutDecision {

  private final int amount;

  public SendPayout(int amount) {
    this.amount = amount;
  }

  public int owed() {
    return amount;
  }
}
