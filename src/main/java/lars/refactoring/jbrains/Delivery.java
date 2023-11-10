package lars.refactoring.jbrains;

import java.util.Objects;

class Delivery {

  private String addressLine1;
  private String addressLine2;
  private String city;
  private String province;
  private String postalCode;
  private String recipientName;
  private String phoneNumber;
  private String deliveryInstructions;
  private double weight;
  private int packageCount;
  private boolean fragile;

  public Delivery(
      String addressLine1,
      String addressLine2,
      String city,
      String province,
      String postalCode,
      String recipientName,
      String phoneNumber,
      String deliveryInstructions,
      double weight,
      int packageCount,
      boolean fragile) {
    this.addressLine1 = addressLine1;
    this.addressLine2 = addressLine2;
    this.city = city;
    this.province = province;
    this.postalCode = postalCode;
    this.recipientName = recipientName;
    this.phoneNumber = phoneNumber;
    this.deliveryInstructions = deliveryInstructions;
    this.weight = weight;
    this.packageCount = packageCount;
    this.fragile = fragile;
  }

  public Address addressOf() {
    return new Address(addressLine1, addressLine2, city, province, postalCode);
  }

  public String getRecipientName() {
    return recipientName;
  }

  public String getPhoneNumber() {
    return phoneNumber;
  }

  public String getDeliveryInstructions() {
    return deliveryInstructions;
  }

  public double getWeight() {
    return weight;
  }

  public int getPackageCount() {
    return packageCount;
  }

  public boolean isFragile() {
    return fragile;
  }

  public Delivery redirect(
      String addressLine1, String addressLine2, String city, String province, String postalCode) {
    return new Delivery(
        addressLine1,
        addressLine2,
        city,
        province,
        postalCode,
        recipientName,
        phoneNumber,
        deliveryInstructions,
        weight,
        packageCount,
        fragile);
  }

  @Override
  public boolean equals(Object o) {
    return (o instanceof Delivery d)
        && Double.compare(d.weight, weight) == 0
        && packageCount == d.packageCount
        && fragile == d.fragile
        && Objects.equals(addressLine1, d.addressLine1)
        && Objects.equals(addressLine2, d.addressLine2)
        && Objects.equals(city, d.city)
        && Objects.equals(province, d.province)
        && Objects.equals(postalCode, d.postalCode)
        && Objects.equals(recipientName, d.recipientName)
        && Objects.equals(phoneNumber, d.phoneNumber)
        && Objects.equals(deliveryInstructions, d.deliveryInstructions);
  }

  @Override
  public int hashCode() {
    return Objects.hash(
        addressLine1,
        addressLine2,
        city,
        province,
        postalCode,
        recipientName,
        phoneNumber,
        deliveryInstructions,
        weight,
        packageCount,
        fragile);
  }
}
