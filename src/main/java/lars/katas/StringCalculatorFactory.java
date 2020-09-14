package lars.katas;

class StringCalculatorFactory {

  public StringCalculator create() {
    return new StringCalculator(new CommaNewLineSplitter(new CommaSplitter()), new Transformer());
  }
}
