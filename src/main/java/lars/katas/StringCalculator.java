package lars.katas;

public class StringCalculator {

  private final Transformer transformer;

  public StringCalculator(Transformer transformer) {
    this.transformer = transformer;
  }

  public int add(String numbers) {
    return transformer.transform(numbers);
  }
}
