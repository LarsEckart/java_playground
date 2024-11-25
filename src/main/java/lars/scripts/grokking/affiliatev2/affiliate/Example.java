package lars.scripts.grokking.affiliatev2.affiliate;

import java.util.List;

class Example {

  public void run(List<Affiliate> affiliates) {
    affiliatePayout(affiliates);
  }

  void affiliatePayout(List<Affiliate> affiliates) {
    for (Affiliate affiliate : affiliates) {
      PayoutDecision payoutDecision = figurePayout(affiliate);
      switch (payoutDecision) {
        case SendPayout p: sendPayout(affiliate.bankCode, p.owed()); break;
        case DontSendPayout p: {}
      }
    }
  }

  PayoutDecision figurePayout(Affiliate affiliate) {
    var owed = affiliate.sales * affiliate.commission;
    if (owed > 100) {
      return new SendPayout(owed);
    }
    return new DontSendPayout();
  }

  private void sendPayout(String bankCode, int owed) {

  }

  record Affiliate(String bankCode, int sales, int commission) {

  }
}
