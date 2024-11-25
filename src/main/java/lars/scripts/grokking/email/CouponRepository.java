package lars.scripts.grokking.email;

import java.util.List;

interface CouponRepository {

  List<Coupon> fetchCouponsFromDB();
}
