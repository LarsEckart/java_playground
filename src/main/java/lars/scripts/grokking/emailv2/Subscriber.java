package lars.scripts.grokking.emailv2;

record Subscriber(String email, int recCount) {

  public String subCouponRank() {
    if (recCount() >= 10) {
      return "best";
    } else {
      return "good";
    }
  }

}
