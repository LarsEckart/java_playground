package lars.advent2023;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

class ScratchCard {
  private List<Integer> winningNumbers = new ArrayList<>();
  private List<Integer> myNumbers = new ArrayList<>();
  private int id;

  ScratchCard(int id, String cardString) {
    this.id = id;

    String cleanCardString = removeNoise(cardString);
    String[] numbersByPurpose = separateNumbersByPurpose(cleanCardString);

    this.winningNumbers =
        parseNumbers(numbersByPurpose[0]).stream().sorted().collect(Collectors.toList());
    this.myNumbers =
        parseNumbers(numbersByPurpose[1]).stream().sorted().collect(Collectors.toList());
  }

  int getCardId() {
    return this.id;
  }

  int getCardWorth() {
    return getMatchingCardCount();
  }

  private List<Integer> parseNumbers(String cardString) {
    return Arrays.stream(cardString.split(" "))
        .filter(Objects::nonNull)
        .filter(s -> !s.isBlank())
        .map(Integer::parseInt)
        .collect(Collectors.toList());
  }

  private String removeNoise(String cardString) {
    return cardString.split(":")[1];
  }

  private String[] separateNumbersByPurpose(String cardString) {
    return cardString.split("\\|");
  }

  private int getMatchingCardCount() {
    return (int) myNumbers.stream().filter(winningNumbers::contains).count();
  }
}

class ScoreCalculator {
  private int score = 0;

  void increaseScore() {
    this.score++;
  }

  int getScore() {
    return this.score;
  }
}

/* ask Copilot about performance issues */
class CardProcessor {
  private ScoreCalculator scoreCalculator = new ScoreCalculator();
  private List<ScratchCard> cards = new ArrayList<>();
  private List<ScratchCard> cardsToProcess = new ArrayList<>();

  CardProcessor(List<String> fileData) {
    IntStream.range(0, fileData.size())
        .forEach(idx -> cards.add(new ScratchCard(idx + 1, fileData.get(idx))));

    cardsToProcess.addAll(this.cards);

    processCards();
  }

  int getFinalScore() {
    return scoreCalculator.getScore();
  }

  private void processCards() {
    ScratchCard cardToProcess = cardsToProcess.remove(0);

    while (cardToProcess != null) {
      scoreCalculator.increaseScore();

      int worth = cardToProcess.getCardWorth();
      int id = cardToProcess.getCardId();

      for (int i = id; i < id + worth; i++) {
        cardsToProcess.add(cards.get(i));
      }

      cardToProcess = cardsToProcess.remove(0);
    }
  }

  public static void main(String[] args) {
    List<String> input = null;
    try {
      input = Files.readAllLines(Paths.get("TODO"));
    } catch (IOException e) {
      e.printStackTrace();
    }
    CardProcessor cardProcessor = new CardProcessor(input);

    System.out.println("asd cards total score: " + cardProcessor.getFinalScore());
  }
}
