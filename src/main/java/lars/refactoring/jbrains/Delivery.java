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

  public Delivery(String addressLine1, String addressLine2, String city, String province,
      String postalCode, String recipientName, String phoneNumber, String deliveryInstructions,
      double weight, int packageCount, boolean fragile) {
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

  public String getAddressLine1() {
    return addressLine1;
  }

  public String getAddressLine2() {
    return addressLine2;
  }

  public String getCity() {
    return city;
  }

  public String getProvince() {
    return province;
  }

  public String getPostalCode() {
    return postalCode;
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
        addressLine1, addressLine2, city, province, postalCode, recipientName,
        phoneNumber, deliveryInstructions, weight, packageCount, fragile);
  }

  @Override
  public boolean equals(Object o) {
    return o instanceof Delivery that &&  Double.compare(that.weight, weight) == 0
        && packageCount == that.packageCount && fragile == that.fragile
        && Objects.equals(addressLine1, that.addressLine1) && Objects.equals(
        addressLine2, that.addressLine2) && Objects.equals(city, that.city)
        && Objects.equals(province, that.province) && Objects.equals(
        postalCode, that.postalCode) && Objects.equals(recipientName,
        that.recipientName) && Objects.equals(phoneNumber, that.phoneNumber)
        && Objects.equals(deliveryInstructions, that.deliveryInstructions);
  }

  @Override
  public int hashCode() {
    return Objects.hash(addressLine1, addressLine2, city, province, postalCode, recipientName,
        phoneNumber, deliveryInstructions, weight, packageCount, fragile);
  }
}
