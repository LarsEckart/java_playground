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
      Scanner scanner = new Scanner(System.in);
      int nextGuess = scanner.nextInt();
      fleet.shoot(new Coordinate(nextGuess));
    }
  }
}
