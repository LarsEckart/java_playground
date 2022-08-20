package lars.katas.battleship;

import java.util.List;
import java.util.Scanner;

public class Game {

  public static void main(String[] args) {
    Fleet fleet = new Fleet();
    fleet.add(new Ship(List.of(new Coordinate(1))));
    fleet.add(new Ship(List.of(new Coordinate(5), new Coordinate(6))));
    fleet.add(new Ship(List.of(new Coordinate(9), new Coordinate(10), new Coordinate(11))));

    while (fleet.anyAlive()) {
      int nextGuess = getUserInput("next guess?");
      String result = fleet.shoot(new Coordinate(nextGuess));
      System.out.println(result);
    }
    System.out.println("you won");

  }

  public static int getUserInput(String prompt) {
    System.out.print(prompt + ": ");
    Scanner scanner = new Scanner(System.in);
    return scanner.nextInt();
  }

}
