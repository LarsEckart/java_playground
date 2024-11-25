package lars.scripts.grokking.emailv2;

import java.util.ArrayList;
import java.util.List;
import lars.scripts.grokking.email.Coupon;

class Coupons {

  private final List<Coupon> coupons;

  public Coupons(List<Coupon> coupons) {
    this.coupons = coupons;
  }

  public List<String> selectCouponsByRank(String rank) {
    List<String> list = new ArrayList<>();
    for (var coupon : coupons) {
      if (coupon.rank().equals(rank)) {
        list.add(coupon.code());
      }
    }
    return list;
  }
}
