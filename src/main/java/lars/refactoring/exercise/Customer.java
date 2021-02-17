package lars.refactoring.exercise;

import java.util.Vector;

public class Customer {

  final static int BUSINESS = 1;
  final static int CONSUMER = BUSINESS + 1;
  final static int INDUSTRIAL = CONSUMER + 1;
  final static int LIFELINE = 1;
  final static int INTERRUPTABLE = LIFELINE + 1;
  final static int ONE_HOUR_NOTICE = INTERRUPTABLE + 1;
  final static int TERITORIAL = ONE_HOUR_NOTICE + 1;
  final static int UNDEFINED = TERITORIAL + 1;

  public int address;
  public int kwh;
  public int customerType;
  public int rate, industrialRate;
  public Vector sites = new Vector();
}
