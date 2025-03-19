package lars.scripts.grokking.emailv2;

import java.util.ArrayList;
import java.util.List;
import lars.scripts.grokking.email.Coupon;

class Functional {

  public static List<String> findByRank(List<Coupon> allCoupons, String rank) {
    List<String> list = new ArrayList<>();
    for (var coupon : allCoupons) {
      if (coupon.rank().equals(rank)) {
        list.add(coupon.code());
      }
    }
    return list;
  }
}
