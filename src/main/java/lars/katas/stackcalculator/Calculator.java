package lars.katas.stackcalculator;

import java.math.BigInteger;
import java.util.Stack;

public class Calculator {
  final BigInteger emptyValue = new BigInteger("0");
  Stack<BigInteger> values = new Stack<BigInteger>();
  boolean topWasReplaced = false;

  public Calculator() {
    push();
  }

  public void push() {
    push(emptyValue);
  }

  public void push(BigInteger value) {
    values.push(value);
    topWasReplaced = false;
  }

  public BigInteger pop() {
    if (values.isEmpty()) return emptyValue;
    return values.pop();
  }

  public BigInteger top() {
    if (values.isEmpty()) return emptyValue;
    return values.lastElement();
  }

  public void replace(BigInteger value) {
    pop();
    values.push(value);
    topWasReplaced = true;
  }

  public void press(String value) {
    BigInteger top = top();
    StringBuffer buffer = new StringBuffer(topWasReplaced ? top.toString() : "");
    buffer.append(value);
    replace(new BigInteger(buffer.toString()));
  }

  public void add() {
    BigInteger value2 = top();
    pop();
    BigInteger value1 = top();
    pop();
    push(value1.add(value2));
  }

  public void multiply() {
    BigInteger value2 = top();
    pop();
    BigInteger value1 = top();
    pop();
    push(value1.multiply(value2));
  }
}
