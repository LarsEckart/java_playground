package lars.katas.battleship;

import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

public class Game {

  public static void main(String[] args) {
    Fleet fleet = new Fleet();
    Random random = new Random();
    int i = random.nextInt(17);
    Set<Coordinate> shipLocations = new HashSet<>();
    Coordinate e1 = new Coordinate(i);
    Coordinate e2 = new Coordinate(i + 1);
    Coordinate e3 = new Coordinate(i + 2);
    shipLocations.add(e1);
    shipLocations.add(e2);
    shipLocations.add(e3);
    fleet.add(new Ship(List.of(e1, e2, e3)));

    int nextInt = random.nextInt(17);
    while (shipLocations.contains(new Coordinate(nextInt))) {
      nextInt = random.nextInt(17);
    }
    fleet.add(new Ship(List.of(new Coordinate(nextInt))));

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
