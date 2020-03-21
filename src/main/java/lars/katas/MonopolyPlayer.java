package lars.katas;

import java.util.ArrayList;
import java.util.List;

class MonopolyPlayer {

  private int balance;
  private String playerName;
  private List<String> ownedProperties = new ArrayList<String>();
  private String location;

  public MonopolyPlayer(String playerName) {
    this.playerName = playerName;
  }

  public void setBalance(int newBalance) {
    this.balance = newBalance;
  }

  public int balance() {
    return this.balance;
  }

  public String location() {
    return location;
  }

  public String playerName() {
    return playerName;
  }

  public void addOwnedProperty(String squareName) {
    ownedProperties.add(squareName);
  }

  public boolean isOwned(String squareName) {
    return ownedProperties.contains(squareName);
  }

  public void landsOn(String squareName) {
    this.location = squareName;
    if ("GO".equals(location)) {
      this.balance += 200;
    }
    if ("LUXURY TAX".equals(location)) {
      this.balance -= 75;
    }
    if ("GO TO JAIL".equals(squareName)) {
      this.location = "JAIL";
    }
    if ("INCOME TAX".equals(squareName)) {
      if (this.balance < 2000) {
        this.balance = this.balance - (this.balance / 10);
      } else {
        this.balance -= 200;
      }
    }
    if ("GIFT OF NAME".equals(squareName)) {
      this.balance += this.playerName.toCharArray().length;
    }
    if ("UTILITY TAX".equals(squareName)) {
      long count =
          ownedProperties.stream()
              .filter(x -> "ELECTRIC COMPANY".equals(x) || "WATER WORKS".equals(x))
              .count();
      this.balance -= 10 * count;
    }
  }
}
