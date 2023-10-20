package lars.refactoring.primitiveobsession;

class Stock {

  private String company;
  private int shares;
  private double shareValue;

  Stock() {
    company = "no name";
    shares = 0;
    shareValue = 0.0;
  }

  public Stock(String company, int shares, double pr) {
    this.company = company;
    this.shares = shares;
    if (pr < 0) {
      System.out.println("Share price can't be negative; " + company + " share price set to 0.\n");
      shareValue = 0;
    } else {
      shareValue = pr;
    }
  }

  void buy(long num, double price) {
    shares += num;

    if (price < 0) {
      System.out.println("The share price can't less than zero. " + "Transaction is aborted.\n");
    } else {
      shareValue = price;
    }
  }

  void sell(long num, double price) {
    if (num > shares) {
      System.out.println("You can't sell more than you have! " + "Transaction is aborted.\n");
    } else {
      shares -= num;
      if (price < 0) {
        System.out.println("Share price can't be less than 0" + "Transaction is aborted.\n");
      } else {
        shareValue = price;
      }
    }
  }

  void update(double price) {
    if (price < 0) {
      System.out.println("Share price can't be less than 0" + "Transaction is aborted.\n");
    } else {
      shareValue = price;
    }
  }
}
