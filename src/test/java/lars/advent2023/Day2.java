package lars.advent2023;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

/*
You're launched high into the atmosphere! The apex of your trajectory just barely reaches the surface of a large island floating in the sky. You gently land in a fluffy pile of leaves. It's quite cold, but you don't see much snow. An Elf runs over to greet you.

The Elf explains that you've arrived at Snow Island and apologizes for the lack of snow. He'll be happy to explain the situation, but it's a bit of a walk, so you have some time. They don't get many visitors up here; would you like to play a game in the meantime?

As you walk, the Elf shows you a small bag and some cubes which are either red, green, or blue. Each time you play this game, he will hide a secret number of cubes of each color in the bag, and your goal is to figure out information about the number of cubes.

To get information, once a bag has been loaded with cubes, the Elf will reach into the bag, grab a handful of random cubes, show them to you, and then put them back in the bag. He'll do this a few times per game.

You play several games and record the information from each game (your puzzle input). Each game is listed with its ID number (like the 11 in Game 11: ...) followed by a semicolon-separated list of subsets of cubes that were revealed from the bag (like 3 red, 5 green, 4 blue).

For example, the record of a few games might look like this:

Game 1: 3 blue, 4 red; 1 red, 2 green, 6 blue; 2 green
Game 2: 1 blue, 2 green; 3 green, 4 blue, 1 red; 1 green, 1 blue
Game 3: 8 green, 6 blue, 20 red; 5 blue, 4 red, 13 green; 5 green, 1 red
Game 4: 1 green, 3 red, 6 blue; 3 green, 6 red; 3 green, 15 blue, 14 red
Game 5: 6 red, 1 blue, 3 green; 2 blue, 1 red, 2 green

In game 1, three sets of cubes are revealed from the bag (and then put back again). The first set is 3 blue cubes and 4 red cubes; the second set is 1 red cube, 2 green cubes, and 6 blue cubes; the third set is only 2 green cubes.

The Elf would first like to know which games would have been possible if the bag contained only 12 red cubes, 13 green cubes, and 14 blue cubes?

In the example above, games 1, 2, and 5 would have been possible if the bag had been loaded with that configuration. However, game 3 would have been impossible because at one point the Elf showed you 20 red cubes at once; similarly, game 4 would also have been impossible because the Elf showed you 15 blue cubes at once. If you add up the IDs of the games that would have been possible, you get 8.

 */

class Day2 {

  @Disabled("only run manually with my personal input")
  @Test
  void solvePart1() {
    int result =
        """
            input"""
            .lines()
            .map(l -> parseLine(l))
            .filter(g -> g.isPossible())
            .mapToInt(g -> g.getId())
            .sum();

    System.out.println(result);
  }

  // @Disabled("only run manually with my personal input")
  @Test
  void solvePart2() {
    int result =
        """
            input"""            .lines()
            .map(l -> parseLine(l))
            .mapToInt(g -> g.power())
            .sum();

    System.out.println(result);
  }

  @Test
  void testParseLine() {
    String input = "Game 1: 3 blue, 4 red; 1 red, 2 green, 6 blue; 2 green";
    Game game = parseLine(input);
    assertEquals(1, game.getId());
    assertEquals(6, game.getBlue());
    assertEquals(4, game.getRed());
    assertEquals(2, game.getGreen());
  }

  /**
   * Parses a line like Game 1: 3 blue, 4 red; 1 red, 2 green, 6 blue; 2 green into a Game object.
   * The numbers for the coulours tell us the maximum amount of cubes of that colour that were
   */
  private static Game parseLine(String input) {
    String[] parts = input.split(":");
    int id = Integer.parseInt(parts[0].split(" ")[1]);
    Game game = new Game(id);

    String[] sets = parts[1].split(";");
    for (String set : sets) {
      String[] cubes = set.split(",");
      for (String cube : cubes) {
        String[] colourAndCount = cube.trim().split(" ");
        int count = Integer.parseInt(colourAndCount[0]);
        String colour = colourAndCount[1];
        switch (colour) {
          case "red":
            game.addRed(count);
            break;
          case "green":
            game.addGreen(count);
            break;
          case "blue":
            game.addBlue(count);
            break;
          default:
            throw new IllegalArgumentException("Unknown colour: " + colour);
        }
      }
    }
    return game;
  }

  private static class Game {

    private final int id;
    private int red = 0;
    private int green = 0;
    private int blue = 0;

    public Game(int id) {
      this.id = id;
    }

    public boolean isPossible() {
      return red <= 12 && green <= 13 && blue <= 14;
    }

    /**
     * The power of a set of cubes is equal to the numbers of red, green, and blue cubes multiplied
     * together. The power of the minimum set of cubes in game 1 is 48. In games 2-5 it was 12,
     * 1560, 630, and 36, respectively. Adding up these five powers produces the sum 2286.
     */
    public int power() {
      return red * green * blue;
    }

    public void addBlue(int count) {
      if (count > blue) {
        blue = count;
      }
    }

    public void addGreen(int count) {
      if (count > green) {
        green = count;
      }
    }

    public void addRed(int count) {
      if (count > red) {
        red = count;
      }
    }

    public int getId() {
      return id;
    }

    public int getRed() {
      return red;
    }

    public int getGreen() {
      return green;
    }

    public int getBlue() {
      return blue;
    }
  }
}
