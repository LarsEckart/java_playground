package lars.refactoring.exercise;

import java.util.Vector;

public class Customer {

  static final int BUSINESS = 1;
  static final int CONSUMER = BUSINESS + 1;
  static final int INDUSTRIAL = CONSUMER + 1;
  static final int LIFELINE = 1;
  static final int INTERRUPTABLE = LIFELINE + 1;
  static final int ONE_HOUR_NOTICE = INTERRUPTABLE + 1;
  static final int TERITORIAL = ONE_HOUR_NOTICE + 1;
  static final int UNDEFINED = TERITORIAL + 1;

  public int address;
  public int kwh;
  public int customerType;
  public int rate, industrialRate;
  public Vector sites = new Vector();
}
