package lars.scripts.grokking.emailv2;

import java.util.ArrayList;
import java.util.List;
import lars.scripts.grokking.email.Email;
import lars.scripts.grokking.email.EmailSender;

class CouponEmailProcess {

  private EmailSender emailSystem;
  private CouponRepository couponRepository;
  private SubscriberRepository subscriberRepository;

  public void sendIssue() {
    var coupons = couponRepository.fetchCouponsFromDB();
    var goodCoupons = coupons.selectCouponsByRank("good");
    var bestCoupons = coupons.selectCouponsByRank("best");
    var subscribers = subscriberRepository.fetchSubscribersFromDB();
    var emails = emailsForSubscribers(subscribers, goodCoupons, bestCoupons);
    for (var email : emails) {
      emailSystem.send(email);
    }
  }

  List<Email> emailsForSubscribers(
      List<Subscriber> subscribers, List<String> goods, List<String> bests) {
    var emails = new ArrayList<Email>();
    for (Subscriber subscriber : subscribers) {
      var email = emailForSubscriber(subscriber, goods, bests);
      emails.add(email);
    }
    return emails;
  }

  private Email emailForSubscriber(Subscriber subscriber, List<String> goods, List<String> bests) {
    if (subscriber.subCouponRank().equals("best")) {
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
}
