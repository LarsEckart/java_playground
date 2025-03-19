package lars.scripts.grokking.email;

import java.util.ArrayList;
import java.util.List;

class CouponEmailProcess {

  private EmailSender emailSystem;
  private CouponRepository couponRepository;
  private SubscriberRepository subscriberRepository;

  public void sendIssue() {
    List<Coupon> coupons = couponRepository.fetchCouponsFromDB();
    var goodCoupons = selectCouponsByRank(coupons, "good");
    var bestCoupons = selectCouponsByRank(coupons, "best");
    var subscribers = subscriberRepository.fetchSubscribersFromDB();
    var emails = emailsForSubscribers(subscribers, goodCoupons, bestCoupons);
    for (var email : emails) {
      emailSystem.send(email);
    }
  }

  private List<Email> emailsForSubscribers(
      List<Subscriber> subscribers, List<String> goods, List<String> bests) {
    var emails = new ArrayList<Email>();
    for (Subscriber subscriber : subscribers) {
      var email = emailForSubscriber(subscriber, goods, bests);
      emails.add(email);
    }
    return emails;
  }

  private Email emailForSubscriber(Subscriber subscriber, List<String> goods, List<String> bests) {
    var rank = subCouponRank(subscriber);
    if (rank.equals("best")) {
      return new Email(
          "newsletter@coupondog.co",
          subscriber.email(),
          "Your best weekly coupons inside",
          "Here are the best coupons: " + String.join(", ", bests));
    } else // rank === "good"
    {
      return new Email(
          "newsletter@coupondog.co",
          subscriber.email(),
          "Your good weekly coupons inside",
          "Here are the good coupons: " + String.join(", ", goods));
    }
  }

  private String subCouponRank(Subscriber subscriber) {
    if (subscriber.recCount() >= 10) {
      return "best";
    } else {
      return "good";
    }
  }

  private List<String> selectCouponsByRank(List<Coupon> coupons, String rank) {
    var ret = new ArrayList<String>();
    for (var coupon : coupons) {
      if (coupon.rank().equals(rank)) {
        ret.add(coupon.code());
      }
    }
    return ret;
  }
}
