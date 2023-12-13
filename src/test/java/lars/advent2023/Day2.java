package lars.advent2023;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

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

  @Disabled("only run manually with my personal input")
  @Test
  void solvePart2() {
    int result =
        """
            input""".lines().map(l -> parseLine(l)).mapToInt(g -> g.power()).sum();

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
