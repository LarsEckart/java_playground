package lars.katas.battleship;

import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

class Game {

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
    fleet.add(new Ship("BigBoy", List.of(e1, e2, e3)));

    int nextInt = random.nextInt(17);
    while (shipLocations.contains(new Coordinate(nextInt))) {
      nextInt = random.nextInt(17);
    }
    Coordinate e11 = new Coordinate(nextInt);
    shipLocations.add(e11);
    fleet.add(new Ship("Swift", List.of(e11)));

    int counter = 0;
    System.out.println(shipLocations);

    while (fleet.anyAlive() && counter < 10) {
      int nextGuess = getUserInput("next guess?");
      counter++;
      Result result1 = fleet.shoot(new Coordinate(nextGuess));
      System.out.println(result1.message());
    }
    System.out.println(shipLocations);
    System.out.println("it took you " + counter + " guesses");
    System.out.println("you won");
  }

  public static int getUserInput(String prompt) {
    System.out.print(prompt + ": ");
    Scanner scanner = new Scanner(System.in);
    return scanner.nextInt();
  }

}
