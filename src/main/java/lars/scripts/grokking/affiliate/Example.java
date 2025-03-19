package lars.scripts.grokking.affiliate;

import java.util.List;

class Example {

  public void run(List<Affiliate> affiliates) {
    affiliatePayout(affiliates);
  }

  void affiliatePayout(List<Affiliate> affiliates) {
    for (Affiliate affiliate : affiliates) {
      figurePayout(affiliate);
    }
  }

  void figurePayout(Affiliate affiliate) {
    var owed = affiliate.sales * affiliate.commission;
    if (owed > 100) // don’t send payouts less than $100
    {
      sendPayout(affiliate.bankCode(), owed);
    }
  }

  private void sendPayout(String bankCode, int owed) {}

  record Affiliate(String bankCode, int sales, int commission) {}
}
